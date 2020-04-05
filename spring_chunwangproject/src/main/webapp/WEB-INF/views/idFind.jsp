<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>천왕 광장</title>
	<meta http-equiv="Content-Type" content="text.html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fot=no">
	<link rel="stylesheet" href="resources/css/bootstrap.min.css">
	<link rel="stylesheet" href="resources/css/custom.css">
</head>
<body>
<%
	String userID = null;
	
	if(session.getAttribute("userID") != null) {
		userID = (String)session.getAttribute("userID");
	}
	if(userID != null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('이미 로그인 상태입니다 !');");
		script.println("location.href = 'index';");
		script.println("</script>");
		script.close(); 
		return;
	}
%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand mr-5" href="index" style="color:#aaaaaa;">천왕 광장</a>
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
					<a class="nav-link active bold" style="font-weight: bold; border-bottom: solid 2px #343A40;" href="userLogin">로그인</a>
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
	<section class="container" style="max-width: 560px;">
		<h2 class="bg-dark p-3 mb-3" style="color: #FFFFFF;">
			아이디 찾기
		</h2>
		<form method="post" action="idFindAction">
			<div class="form-group">
				<label>이름</label>
				<input type="text" name="userName" class="form-control">
			</div>
			<div class="form-group">
				<label>이메일</label>
				<input type="email" name="userEmail" class="form-control">
			</div>
			<div class="form-group">
				<a href="pwFind" style="text-decoration:none"><small>비밀번호 찾기</small></a>
			</div>
			<button type="submit" class="btn btn-primary">아이디 찾기</button>
		</form>
	</section>
	
	<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
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