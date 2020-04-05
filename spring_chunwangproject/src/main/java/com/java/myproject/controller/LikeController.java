package com.java.myproject.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.myproject.service.LikeService;

@Controller
public class LikeController {
	
	@Autowired
	private LikeService likesvc;
	
	@RequestMapping("/likeUp")
	public String likeUp(HttpServletRequest request) throws Exception {
		String userID = (String) request.getSession().getAttribute("userID");
		int writeID = Integer.parseInt(request.getParameter("writeID"));
		String boardType = request.getParameter("boardType");
		
		if(boardType == null || writeID == 0) {
			return "redirect:index";
		}
		
		int result = likesvc.LikeUp(boardType, writeID, userID);
		
		if(result == -2)
			return "redirect:" + request.getHeader("Referer");
		else
			return "redirect:contentView?boardType=" + boardType +"&idx=" + writeID;
	}
}
