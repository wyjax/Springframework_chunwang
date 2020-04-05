package com.java.myproject.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.myproject.dao.CommentDAOImpl;
import com.java.myproject.dto.CommentDTO;
import com.java.myproject.service.CommentService;
import com.java.myproject.util.TimeCome;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService cmtsvc;
	
	@RequestMapping("/commentRegister")
	public String commentRegister(HttpServletRequest request) throws Exception {
		String userID = (String) request.getSession().getAttribute("userID");
		String boardType = request.getParameter("boardType");
		String commentContent = request.getParameter("commentContent");
		int writeID = Integer.parseInt(request.getParameter("idx"));
		int preComment = Integer.parseInt(request.getParameter("preComment"));
		
		if(commentContent == null) { // 댓글 내용 없음
			return "redirect:contentView?boardType=" + boardType + "&idx=" + writeID;
		}
		if(boardType == null || writeID == -1) {
			return "redirect:index";
		}
		String time = new TimeCome().getTime();
		
		int result = new CommentDAOImpl().commentWrite(new CommentDTO(0, writeID, preComment, userID, commentContent, time, boardType));
		
		if(result == -1) {
			return "redirect:contentView?boardType=" + boardType + "&idx=" + writeID;
		} 
	 	else {
	 		return "redirect:contentView?boardType=" + boardType + "&idx=" + writeID;
		}
	}
	
	@RequestMapping("/commentDelete")
	public String commentDelete(HttpServletRequest request) throws Exception {
		String userID = (String) request.getSession().getAttribute("userID");
		String boardType = request.getParameter("boardType");
		int commentID = Integer.parseInt(request.getParameter("commentID"));
		int writeID = Integer.parseInt(request.getParameter("writeID"));
		
		if(boardType == null || commentID == 0) {
			return "redirect:index";
		}
		
		boolean result = cmtsvc.Delete(boardType, commentID);
		
		if(result == false) { // 삭제 실패
			return "redirect:contentView?boardType=" + boardType + "&idx=" + writeID;
		} else { // 삭제 성공
			return "redirect:contentView?boardType=" + boardType + "&idx=" + writeID;
		}
	}
}
