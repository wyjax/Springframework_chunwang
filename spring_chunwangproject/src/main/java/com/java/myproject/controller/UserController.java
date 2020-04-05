package com.java.myproject.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.java.myproject.dao.UserDAOImpl;
import com.java.myproject.service.UserService;
import com.java.myproject.util.SHA256;

@Controller
public class UserController {
	
	@Autowired
	private UserService usersvc;
	@Autowired
	private JavaMailSender mailSender;
	
	@RequestMapping("/JoinCheck")
	public void JoinCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userID = request.getParameter("userID");
		String userEmail = request.getParameter("userEmail");
		
		if(userEmail == null) {
			response.getWriter().write(usersvc.joinCheck(userID) + "");
		}
		else if(userID == null) {
			boolean email_result = usersvc.getExistEmailChecked(userEmail);
			if(email_result == false)
				response.getWriter().write("1");
			else
				response.getWriter().write("0");
		}
	}
	
	@RequestMapping("/userLogin")
	public String userLogin(Model model) {
		return "userLogin";
	}
	
	@RequestMapping("/loginAction")
	public String loginAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userID = (String) request.getParameter("userID");
		String userPassword = (String) request.getParameter("userPassword");
		
		if(userID == null || userPassword == null || userID.equals("") || userPassword.equals("")) {
			request.getSession().setAttribute("msgType", "2");
			request.getSession().setAttribute("msgContent", "아이디와 비밀번호를 입력해주세요.");
			return "redirect:userLogin";
		}
		
		int result = usersvc.login(userID, userPassword);
		
		if(result == 1) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("nickName", usersvc.getNickname(userID));
			request.getSession().setAttribute("msgType", "1");
			request.getSession().setAttribute("msgContent", "로그인 되었습니다.");
			return "redirect:userLogin";
		}
		else if(result == 0 || result == -1) {
			request.getSession().setAttribute("msgType", "2");
			request.getSession().setAttribute("msgContent", "아이디나 비밀번호가 틀립니다.");
			return "redirect:userLogin";
		}
		else {
			request.getSession().setAttribute("msgType", "3");
			request.getSession().setAttribute("msgContent", "데이터베이스 오류입니다. 관리자에게 문의하십시오.");
			return "redirect:userLogin";
		}
	}
	
	@RequestMapping("/userRegister")
	public String userRegister(HttpServletRequest request) throws Exception {
		String userID = request.getParameter("userID");
		String userPassword = request.getParameter("userPassword");
		String userPasswordCheck = request. getParameter("userPasswordCheck");
		String userName = request. getParameter("userName");
		String userEmail = request. getParameter("userEmail");

		String secretkey = "6LeaNGoUAAAAAPmAgP8Wr-QWafwcyIs-v4ROHqQE";
		String captcha = request.getParameter("g-recaptcha-response");
		
		URL url = new URL("https://www.google.com/recaptcha/api/siteverify?secret="+secretkey+"&response="+captcha+"&remoteip="+request.getRemoteAddr());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		String line, outputString = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		while ((line = reader.readLine()) != null) {
		    outputString += line;
		}
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(outputString);
		boolean verify = jsonObject.get("success").getAsBoolean();
		
		if(verify == false) {
			request.getSession().setAttribute("msgType", "2");
			request.getSession().setAttribute("msgContent", "자동가입방지를 체크해주세요.");
			return "redirect:userJoin";
		}
		
		if(userID == null || userPassword == null || userPasswordCheck == null || userName == null ||
				userEmail == null || userID.equals("") || userPassword.equals("") || userPasswordCheck.equals("") ||
				userName.equals("") || userEmail.equals("")) {
			request.getSession().setAttribute("msgType", "2");
			request.getSession().setAttribute("msgContent", "모든 정보를 입력해주세요.");
			return "redirect:userJoin";
		}
		if(!userPassword.equals(userPasswordCheck)) {
			request.getSession().setAttribute("msgType", "2");
			request.getSession().setAttribute("msgContent", "비밀번호가 서로 다릅니다.");
			return "redirect:userJoin";
		}
		
		String email_result = usersvc.isExistEmail(userEmail);
		
		if(email_result != null ) {
			boolean checked = usersvc.getExistEmailChecked(userEmail);
			
			if(checked == true) {
				request.getSession().setAttribute("msgType", "2");
				request.getSession().setAttribute("msgContent", "이미 인증에 사용한 이메일 입니다.");
				return "redirect:userJoin";
			}
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		String time = dateFormat.format(cal.getTime());
		
		int result = usersvc.join(userID, userPassword, userName, userEmail, time);
		if(result == 1) {
			request.getSession().setAttribute("userID", userID);
			request.getSession().setAttribute("nickName", usersvc.getNickname(userID));
			request.getSession().setAttribute("msgType", "1");
			request.getSession().setAttribute("msgContent", "회원가입이 완료되었습니다.");
			return "redirect:emailSend";
		}
		else if(result == -2) {
			request.getSession().setAttribute("msgType", "2");
			request.getSession().setAttribute("msgContent", "이미 존재하는 아이디입니다.");
			return "redirect:userJoin";
		}
		else {
			request.getSession().setAttribute("msgType", "3");
			request.getSession().setAttribute("msgContent", "데이터베이스 오류입니다. 관리자에게 문의하십시오.");
			return "redirect:index";
		}
	}
	
	@RequestMapping("/userDelete")
	public String userDeleteAction(HttpServletRequest request) throws Exception {
		String userID = (String)request.getSession().getAttribute("userID");
		boolean result = usersvc.deleteUser(userID);
		request.getSession().invalidate();
		
		return "redirect:index";
	}
	
	@RequestMapping("/changePassword")
	public String changePassword(HttpServletRequest request) throws Exception {
		String userID = (String)request.getSession().getAttribute("userID");
		String pw1 = request.getParameter("passwd");
		String pw2 = request.getParameter("passwdcheck");
		
		if(pw1 == null || pw2 == null || !pw1.equals(pw2)) {
			request.getSession().setAttribute("msgType", "1");
			request.getSession().setAttribute("msgContent", "d");
			return "redirect:myPage";
		}
		boolean result = usersvc.changePw(userID, pw1);
		
		if(result == false) // 실패
			return "redirect:myPage";
		else // 성공
			return "redirect:myPage";
	}

	@RequestMapping("/userLogout")
	public String userLogout(Model model) {
		return "userLogout";
	}
	
	@RequestMapping("/userJoin")
	public String userJoin(HttpServletRequest request, Model model) throws Exception {
		return "userJoin";
	}
	
	@RequestMapping("/myPage")
	public String myPage(Model model) {
		return "myPage";
	}
	
	@RequestMapping("/NicknameServlet")
	public void NicknameServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String nickName = request.getParameter("nickName");
		String userID = (String) request.getSession().getAttribute("userID");
		String result1 = usersvc.isExistNickname(nickName);

		if(nickName == null) {
			response.getWriter().write("2");
			return;
		}
		else if(result1 != null) {
			response.getWriter().write("3");
			return;
		}
		
		int result2 = usersvc.changeNickName(userID, nickName);
		
		if(result2 == 1) {
			request.getSession().setAttribute("nickName", nickName);
			response.getWriter().write("1");
			return;
		}
		else {
			response.getWriter().write("4");
			return;
		}
	}
	
	@RequestMapping("/idShowPage")
	public String idShowPage(Model model) {
		return "idShowPage";
	}

	@RequestMapping("/idFind")
	public String idFind(Model model) {
		return "idFind";
	}
	
	@RequestMapping("/idFindAction")
	public String idFindAction(HttpServletRequest request) throws Exception {
		String userName = request.getParameter("userName");
		String userEmail = request.getParameter("userEmail");
		
		if(userName == null || userEmail == null || userName.equals("") || userEmail.equals("")) {
			return "redirect:idFind";
		}
		
		String tmpid = new UserDAOImpl().getIdFind(userName, userEmail);
		
		if(tmpid == null) {
			return "redirect:idFind";
		}
		else {
			request.getSession().setAttribute("tmpid", tmpid.toLowerCase());
			return "redirect:idShowPage";
		}
	}
	
	@RequestMapping("/pwFind")
	public String pwFind(Model model) {
		return "pwFind";
	}
	
	@RequestMapping("/pwFindAction")
	public String pwFindAction(HttpServletRequest request) throws Exception {
		String userID = request.getParameter("uid").toLowerCase();
		String userEmail = request.getParameter("userEmail").toLowerCase();
		
		if(userID == null || userEmail == null || userID.equals("") || userEmail.equals(""))
			return "redirect:pwFind";

		String tmp_email = usersvc.getUserEmail(userID).toLowerCase();
		if(tmp_email == null || !tmp_email.equals(userEmail))
			return "redirect:pwFind";
		
		char[] charaters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
		StringBuffer sb = new StringBuffer();
		Random rn = new Random();
		for( int i = 0 ; i < 15 ; i++ ){
	    	sb.append( charaters[ rn.nextInt( charaters.length ) ] );
		}
		String tmpPassword = sb.toString();
		
	    String setfrom = "umjugnki@gmail.com";       
	    String title   = "천왕 광장 임시 비밀번호입니다.";
	    String content = "임시 비밀번호 : " + tmpPassword;
	    
	    try {
	      MimeMessage message = mailSender.createMimeMessage();
	      MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
	      messageHelper.setFrom(setfrom);  // 보내는사람 생략하거나 하면 정상작동을 안함
	      messageHelper.setTo(userEmail);  // 받는사람 이메일
	      messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
	      messageHelper.setText(content);  // 메일 내용
	      mailSender.send(message);
	    } catch(Exception e){
	    }
	    
	    usersvc.changePw(userID, tmpPassword);
	    return "redirect:index";
	}
	
	@RequestMapping("/emailSendConfirm")
	public String emailSendConfirm() {
		return "emailSendConfirm";
	}
	
	@RequestMapping("/emailSend")
	public String emailSend(HttpServletRequest request) throws Exception {
		String userID = (String) request.getSession().getAttribute("userID");
		
	    String setfrom = "umjugnki@gmail.com";       
	    String tomail  = usersvc.getUserEmail(userID);     // 받는 사람 이메일
	    String title   = "천왕 광장 회원가입을 위한 이메일 인증 메일입니다.";
	    String content = "다음 링크에 접속하여 이메일 인증을 진행하세요." +
	    		"<a href='http://localhost:8080/myproject/emailCheck?code=" + new SHA256().getSHA256(tomail) + "'>  이메일 인증하기</a>";

	    try {
	      MimeMessage message = mailSender.createMimeMessage();
	      MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
	 
	      messageHelper.setFrom(setfrom);  // 보내는사람 생략하거나 하면 정상작동을 안함
	      messageHelper.setTo(tomail);     // 받는사람 이메일
	      messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
	      messageHelper.setText(content);  // 메일 내용
	     
	      mailSender.send(message);
	    } catch(Exception e){
	    }
	    
	    return "redirect:emailSendConfirm";
	}
	
	@RequestMapping("/emailCheck")
	public String emailCheck(HttpServletRequest request) throws Exception {
		String userID = (String) request.getSession().getAttribute("userID");
		
		if(userID == null)
			return "redirect:index";
		
		String code = request.getParameter("code");
		
		if(code == null)
			return "redirect:emailSendConfirm";
		
		String userEmail = usersvc.getUserEmail(userID);
		boolean isRight = (new SHA256().getSHA256(userEmail).equals(code)) ? true : false;
		
		if(isRight == true)
			return "redirect:index";
		else
			return "redirect.index";
	}
}