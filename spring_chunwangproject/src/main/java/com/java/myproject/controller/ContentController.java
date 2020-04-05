package com.java.myproject.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.java.myproject.service.ContentService;

@Controller
public class ContentController {
	
	@Autowired
	private ContentService cntsvc;
	
	@RequestMapping("contentRegister")
	public String contentRegister (HttpServletRequest request) throws Exception {
		String userID = (String)request.getSession().getAttribute("userID");
		String topicName = request.getParameter("topicName");
		String contentName = request.getParameter("contentName");
		String boardDivide = (String)request.getSession().getAttribute("boardDivide");
		
		if(userID == null)
			return "redirect:userLogin";
		else if(topicName == null || contentName == null || boardDivide == null) {
			return "redirect:contentWrite";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");  
		Date dTime = new Date();
		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
		sdf.setTimeZone(tz);
		String time = sdf.format(dTime);
		
		int result = cntsvc.write(userID, topicName, contentName, time, boardDivide);
		
		if(result > 0)
			return "redirect:" + boardDivide;
		
		return "index";
	}
	
	@RequestMapping("/contentDelete")
	public String contentDelete (HttpServletRequest request) throws Exception {
		String boardType = (String)request.getParameter("boardType");
		int writeID = Integer.parseInt(request.getParameter("writeID"));
		
		if(boardType == null || writeID == 0)
			return "redirect:index";
		
		boolean result = cntsvc.Delete(boardType, writeID);
		
		return "redirect:" + boardType;
	}
	
	@RequestMapping("/freeboard")
	public String freeboard(Model model) {
		return "freeboard";
	}
	
	@RequestMapping("/anonboard")
	public String anonboard(Model model) {
		return "anonboard";
	}
	
	@RequestMapping("/nanumboard")
	public String nanumBoard(Model model) {
		return "nanumboard";
	}
	
	@RequestMapping("/promoteboard")
	public String promoteboard(Model model) {
		return "promoteboard";
	}
	
	@RequestMapping("/noticeboard")
	public String noticeboard(Model model) {
		return "noticeboard";
	}
	
	@RequestMapping("/contentView")
	public String contentView(Model model) {
		return "contentView";
	}
	
	@RequestMapping("/contentWrite")
	public String contentWrite(Model model) {
		return "contentWrite";
	}
	
	@RequestMapping("/index")
	public String index(Model model) {
		return "index";
	}
}
