<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.io.*" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.java.myproject.dao.UserDAOImpl" %>
<%@ page import="com.java.myproject.dto.UserDTO" %>
<%@ page import="com.java.myproject.dto.CommentDTO"%>
<%@ page import="com.java.myproject.dao.CommentDAOImpl"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>천왕 광장</title>
	<meta http-equiv="Content-Type" content="text.html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fot=no">
	<link rel="stylesheet" href="resources/css/bootstrap.min.css">
	<link rel="stylesheet" href="resources/css/custom.css">
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="resources/js/bootstrap.js"></script>
	<Script type="text/javascript">
		function nickChange() {
			var nickName = $('#nickName').val();
			
			$.ajax({
				type: 'POST',
				url: './NicknameServlet',
				data: {nickName: nickName},
				success: function(result) {
					if(result == 1) {
						$('#checkbtn').attr('class', 'btn btn-primary');
						$('#msgType').html('성공');
						$('#msgContent').html('닉네임 변경에 성공했습니다.');
						$('#nicklabel').html(nickName);
					}
					else {
						$('#checkbtn').attr('class', 'btn btn-danger');
						$('#msgType').html('실패');
						
						if(result == 2)
							$('#msgContent').html('공백을 입력할 수 없습니다.');
						else if(result == 3)
							$('#msgContent').html('이미 사용중인 닉네임입니다.');
						else if(result == 4)
							$('#msgContent').html('데이터베이스 오류입니다.');
					}
					$('#nickName').val('');
					$('#messageModal').modal('show');
				}
			})
		}
		
		function passwordCheckFuntion() {
			var userPassword = $('#userPassword').val();
			var userPasswordCheck = $('#userPasswordCheck').val();
			
			if(userPassword.length < 6) {
				$('#userPassword').attr('style', 'background-color: #FFCECE;');
				$('#userPasswordCheck').attr('style', 'background-color: #FFCECE;');
				$('#pwtxt').attr('style', 'color: red;');
				$('#pwtxt').html('6자리 이상으로 만들어주세요.');
			}
			else if(userPassword != userPasswordCheck) {
				$('#userPassword').attr('style', 'background-color: #FFCECE;');
				$('#userPasswordCheck').attr('style', 'background-color: #FFCECE;');
				$('#pwtxt').attr('style', 'color: red;');
				$('#pwtxt').html('비밀번호가 서로 다릅니다.');
			}
			else {
				$('#userPassword').attr('style', 'background-color: #B0F6AC;');
				$('#userPasswordCheck').attr('style', 'background-color: #B0F6AC;');
				$('#pwtxt').attr('style', 'color: limegreen;');
				$('#pwtxt').html('사용가능한 비밀번호 입니다.');
			}
		}
	</Script>
</head>
<body>
<%
	String userID = null;
	if(session.getAttribute("userID") != null) {
		userID = (String)session.getAttribute("userID");
	}
	String nick = null;
	if(session.getAttribute("nickName") != null) {
		nick = (String)session.getAttribute("nickName");
	}
	
	if(userID == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 해주세요 !');");
		script.println("location.href = 'userLogin';");
		script.println("</script>");
		script.close();
		return;
	}
	
	String userEmail = null;
	String nickName = null;
	userEmail = new UserDAOImpl().getUserEmail(userID);
	nickName = new UserDAOImpl().getNickname(userID);
%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand mr-5" href="index" style="color:#aaaaaa;">천왕 광장</a>
		<button class="navbar-toggler " type="button" data-toggle="collapse" data-target="#navbar">
		</button>
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
					<a class="nav-link" style="color: black"><small>&lt; <%= nick %> &gt;님 반갑습니다.</small></a>
				</li>
				<li class="nav-item">
					<a class="nav-link active" style="font-weight: bold; border-bottom: solid 2px #343A40;" href="./myPage">마이페이지</a>
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
		<h2 class="bg-dark p-3 mb-0" style="color: #FFFFFF;">
			마이페이지
		</h2>
		<div class="bg-light p-3">
			<h4 class="mt-4">닉네임</h4><a id="nicklabel">&nbsp;&nbsp;<%= nickName %></a>
			<input class="form-control mb-2 mt-2" type="text" id="nickName" autocomplete="off" class="mt-2 mb-2" placeholder="변경할 닉네임을 입력해주세요." maxlength="20">
			<button class="btn btn-primary" onclick="nickChange();">닉네임 변경</button>	
			<h4 class="mt-4">아이디</h4><a>&nbsp;&nbsp;<%= userID %></a>
			<h4 class="mt-4">이메일</h4><a>&nbsp;&nbsp;<%= userEmail %></a>
			<h4 class="mt-4">비번번호 변경<small class="ml-3" id="pwtxt"></small></h4>
			<form class="mb-5" method="post" action="changePassword">
				<input id="userPassword" onkeyup="passwordCheckFuntion();" type="password" name="passwd" class="form-control mb-2" placeholder="변경할 비밀번호 입력" maxlength="64">
				<input id="userPasswordCheck" onkeyup="passwordCheckFuntion();" type="password" name="passwdcheck" class="form-control mb-2" placeholder="변경할 비밀번호 입력 확인" maxlength="64">	
				<button class="btn btn-primary" onclick="return confirm('비밀번호를 변경하시겠습니까?')" type="submit">비밀번호 변경</button>	
			</form>
			<form class="text-center" method="post" action="userDelete">
				<button class="btn btn-danger" onclick="return confirm('추천하시겠습니까?')">회원탈퇴</button>
			</form>
		</div>
	</section>
	<div class="modal fade" id="messageModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="vertical-alignment-helper">
			<div class="modal-dialog vertical-align-center">
				<div id="m1" class="modal-content">
					<div class="modal-header panel-heading">
						<h4 id="msgType" class="modal-title">
						</h4>
						<button type="button" class="close" data-dismiss="modal">
							<span aria-hidden="true">&times;</span>
							<span class="sr-only">Close</span>
						</button>
					</div>
					<div id="msgContent" class="modal-body">
					</div>
					<div class="modal-footer">
						<button id="checkbtn" class="btn btn-primary" data-dismiss="modal">확인</button>
					</div>
				</div>
			</div>
		</div>
	</div>
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