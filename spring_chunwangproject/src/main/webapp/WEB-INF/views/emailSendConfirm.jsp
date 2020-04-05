<%@page import="com.java.myproject.dao.UserDAOImpl"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.java.myproject.dao.UserDAOImpl" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>천왕 광장</title>
	<!--  부트스트랩 CSS 추가하기 -->
	<link rel="stylesheet" href="resources/css/bootstrap.min.css">
	<link rel="stylesheet" href="resources/css/custom.css">
</head>
<body>
<%
	String userID = null;
	if(session.getAttribute("userID") != null) {
		userID = (String)session.getAttribute("userID");
	}
	if(userID == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인이 되어 있지 않습니다 !')");
		script.println("location.href = 'userLogin';");
		script.println("</script>");
		script.close();
		return;
	}
	boolean emailChecked = new UserDAOImpl().getUserEmailChecked(userID);
	
	if(emailChecked == true) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('이미 이메일 인증이 완료되어 있습니다.')");
		script.println("location.href = 'index';");
		script.println("</script>");
		script.close();
		return;
	}
%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand mr-5" href="index">천왕 광장</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
		</button>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<a class="nav-link" href="freeboard">자유광장</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="anonboard">익명광장</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="nanumboard">나눔광장</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="promoteboard">홍보광장</a>
				</li>
			</ul>
		</div>
				<div id="navbar" class="collapse navbar-collapse justify-content-end" style="display:inline; right:0px;">
			<ul class="navbar-nav">
<%
	if(userID == null) {
%>
				<li class="nav-item">
					<a class="nav-link" href="userLogin">로그인</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="userJoin">회원가입</a>
				</li>
<%
	} else {
%>
				<li class="nav-item">
					<a class="nav-link" href="./myPage">마이페이지</a>
				</li>
				<li class="nav-item">
					<a class="nav-link" href="userLogout">로그아웃</a>
				</li>
<%
	}
%>
			</ul>
		</div>
	</nav>
	<section class="container mt-3" style="max-width: 560px;"> <!-- 섹션은  HTML의 구성요소 container는 반응형으로 특정요소가 알아서 작아지도록함-->
		<div class="alert alert-warning mt-4" role="alert">
			이메일 주소 인증을 하셔야 이용가능합니다. 인증 메일을 받지 못하셨나요?
		</div>
		<a href="emailSend" class="btn btn-primary">인증 메일 다시 받기</a>
	</section>
	<footer class="bg-dark p-5 text-center mt-5" style="color: #FFFFFF;">
		Copyright &copy; 2018 엄정기 All Rights Reserved.
		<br><br>
		<a style="color: #bbbbbb;"> &lt; Contact &gt; email:
			<a style="color: #ffffff">umjugnki@Naver.com</a>&nbsp;&nbsp;
			<a style="color: #bbbbbb">instagram:</a> 
			<a style="color: #ffffff" href="http://www.instagram.com/spirit_umm" target="_blank">spirit_umm</a>
		</a>
	</footer>
	<!-- 스크립트 추가 -->
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/popper.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
</body>
</html>