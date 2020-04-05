<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.io.*" %>
<%@ page import="java.lang.String" %>
<%@ page import="com.java.myproject.dao.*" %>
<%@ page import="com.java.myproject.dto.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>천왕 광장</title>
	<meta http-equiv="Content-Type" content="text.html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fot=no">
	<!-- 부트스트랩 css 추가 -->
	<link rel="stylesheet" href="resources/css/bootstrap.min.css">
	<link rel="stylesheet" href="resources/css/custom.css">
</head>
<body>
<script language="javascript">
	function button_click(s) {
		document.getElementById("cmntid").value = s;
	}
</script>
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
	boolean emailChecked = new UserDAOImpl().getUserEmailChecked(userID);
	
	if(emailChecked == false) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = 'eamilSendConfirm'");
		script.println("</script>");
		script.close();
		return;
	}
	
	String boardType = null;
	int idx = 0;
	
	if(request.getParameter("boardType") != null) {
		boardType = (String)request.getParameter("boardType");
	}
	if(request.getParameter("idx") != null) {
		idx = Integer.parseInt(request.getParameter("idx"));
	}
	
	if(boardType == null || idx == 0) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = 'index'");
		script.println("</script>");
		script.close();
		return;
	}
	
	new ContentDAOImpl().SearchUp(boardType, idx);
	ContentDTO cntdto = new ContentDAOImpl().getContent(boardType, idx);
%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand mr-5" href="index" style="color:#aaaaaa;">천왕 광장</a>
		<button class="navbar-toggler " type="button" data-toggle="collapse" data-target="#navbar">
		</button>
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
<%
	if(boardType.equals("freeboard")) {
%>
					<a class="nav-link active" style="font-weight: bold; border-bottom: solid 2px #343A40;" href="freeboard">자유광장</a>
<%
	} else {
%>
					<a class="nav-link" href="freeboard">자유광장</a>
<%
	}
%>
				</li>
				<li class="nav-item">
<%
	if(boardType.equals("anonboard")) {
%>
					<a class="nav-link active" style="font-weight: bold; border-bottom: solid 2px #343A40;" href="anonboard">익명광장</a>
<%
	} else {
%>
					<a class="nav-link" href="anonboard">익명광장</a>
<%
	}
%>
				</li>
				<li class="nav-item">
<%
	if(boardType.equals("nanumboard")) {
%>
					<a class="nav-link active" style="font-weight: bold; border-bottom: solid 2px #343A40;" href="nanumboard">나눔광장</a>
<%
	} else {
%>
					<a class="nav-link" href="nanumboard">나눔광장</a>
<%
	}
%>
				</li>
				<li class="nav-item">
<%
	if(boardType.equals("promoteboard")) {
%>
					<a class="nav-link active" style="font-weight: bold; border-bottom: solid 2px #343A40;" href="promoteboard">홍보광장</a>
<%
	} else {
%>				
					<a class="nav-link" href="promoteboard">홍보광장</a>
<%
	}
%>
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
					<a class="nav-link" style="color: black"><small>&lt; <%=nick%> &gt;님 반갑습니다.</small></a>
				</li>
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
	<section class="container">
		<div class="card bg-light mt-3">
			<div class="card-header bg-light ">
				<div class="row">
					<div class="col-md-3 text-left"><a style="font-weight: bold;">제목</a>&nbsp;&nbsp; <%=cntdto.getTopicName()%></div> 
				</div>
				<hr>
				<div class="row">
<%
	String nickName = new UserDAOImpl().getNickname(cntdto.getUserID());
	if(nickName == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('데이터베이스 오류입니다.')");
		script.println("location.href = 'index';");
		script.println("</script>");
		script.close();
		return;
	}
%>
<%
	// 만약 boardType이 익명게시판이라면 닉네임을 익명처리
	if(boardType.equals("anonboard")) {
%>
					<div class="col-md-3 text-left"><a style="font-weight: bold;">작성자</a>&nbsp;&nbsp; 익명</div>
<%
	} else {
%>
					<div class="col-md-3 text-left"><a style="font-weight: bold;">작성자</a>&nbsp;&nbsp; <%=nickName%></div>
<%
	}
%>
					<div class="col-md-3 text-center"><a style="font-weight: bold;">작성시간</a>&nbsp;&nbsp; <%=cntdto.getTime()%></div>
					<div class="col-md-3 text-right"><a style="font-weight: bold;">조회수</a>&nbsp;&nbsp; <%=cntdto.getSearchCount()%></div> 
				</div>
			</div>
			<div class="card-body">
				<h5 class="card-title mb-6">
					<%=cntdto.getContentName()%>
				</h5>
				<div class="text-center mt-5 mb-3">
					<a class="bg-secondary p-2" style="color:#ffffff; border-radius: 10px">추천 : <%=cntdto.getLikeCount()%></a>
				</div>
				<div class="row">
					<div class="col text-right text-center">
						<form method="post" action="./likeUp?boardType=<%=boardType%>&writeID=<%=cntdto.getwriteID()%>" style="display: inline;">
							<button type="submit" class="btn btn-primary mr-4" onclick="return confirm('추천하시겠습니까?')">추 천</button>
						</form>
<%
	if(userID.equals(cntdto.getUserID())) {
%>
						<form method="post" action="./contentDelete?boardType=<%=boardType%>&writeID=<%=cntdto.getwriteID()%>" style="display: inline;">
							<button type="submit" onclick="return confirm('게시글을 삭제하시겠습니까?')" class="btn btn-danger">삭 제</button>
						</form>
<%
	}
%>
					</div>
				</div>
			</div>
		</div>
		<div class="card bg-light mt-3">
			<div class="card-header" style="font-weight: bold;">댓글 달기</div>
			<div class="card-body">
				<form method="post" action="commentRegister?boardType=<%=boardType%>&idx=<%=idx%>&preComment=0">
					<div class="container">
						<textarea  name="commentContent" class="form-control" maxlength="2048" style="height: 120px; width: 100%;" placeholder="댓글내용을 입력하세요."></textarea>
					</div>
					<div class="form-group text-right mt-3 mr-3 mb-0">
						<button class="btn btn-success" type="submit">등 록</button>
					</div>
				</form>
			</div>
		</div>
		<div class="card bg-light mt-3">
			<div class="card-header" style="font-weight: bold;">댓글 목록</div>
<%
	ArrayList<CommentDTO> cmtList = new ArrayList<CommentDTO>();
	cmtList = new CommentDAOImpl().getList(boardType, idx);
	String cmtid = null;
	
	if(cmtList != null) {
		for(int i = 0; i < cmtList.size(); i++) {
	CommentDTO cmt = cmtList.get(i);
	
	if(cmt.getWriteID() == idx && cmt.getPreComment() == 0) {
%>
			<div class="card-body pt-1 pb-1">
<%
	if(i > 0) {
%>
				<div class="row" style="border-top: 1px solid #aaaaaa;">
<%
	} else {
%>
				<div class="row">
<%
	}
%>
					<div class="col-md-12">
<%
	// 댓글을 처리하는 코드
		nickName = new UserDAOImpl().getNickname(cmt.getUserID());
		if(nickName == null) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('데이터베이스 오류입니다.')");
			script.println("location.href = 'index';");
			script.println("</script>");
			script.close();
			return;
		}
		if(boardType.equals("anonboard")) {
%>
						<a style="font-weight: bold;">익명</a>
<%
	if(cntdto.getUserID().equals(cmt.getUserID()))
%>
						<small style="font-weight: bold; color: #0A6EFF;">(글쓴이)</small>&nbsp;&nbsp;&nbsp;
<%
	} else {
%>
						<a style="font-weight: bold;"><%=nickName%></a>&nbsp;&nbsp;&nbsp;
<%
	}
%>
						<small>- <%=cmt.getCommentTime()%></small>&nbsp;
						<a href="#registerModal" data-toggle="modal" onclick="button_click('<%= cmt.getCommentID() %>');"><small>댓글 달기</small></a>
<%
	if(userID.equals(cmt.getUserID())) {
%>
						<small><a onclick="return confirm('댓글을 삭제하시겠습니까?')" href="./commentDelete?boardType=<%=cmt.getboardType()%>&commentID=<%=cmt.getCommentID()%>&writeID=<%=idx%>" style="color: red;">삭제</a></small>
<%
	}
%>
					</div>
					<div class="col">
						<small><%=cmt.getCommentContent()%></small>
					</div>
				</div>
<%
	// 대댓글을 처리하는 코드
		for(int j = i + 1; j < cmtList.size(); j++) {
			CommentDTO cmt2 = cmtList.get(j);
			if(cmt.getCommentID() == cmt2.getPreComment()) {
%>
						<div class="row">
							<div class="col-md-12 pl-10">
<%
	if(boardType.equals("anonboard")) {
%>
								<a style="font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└&nbsp;익명</a>
<%
	if(cntdto.getUserID().equals(cmt2.getUserID()))
%>
								<small style="font-weight: bold; color: #0A6EFF;">(글쓴이)</small>
<%
	} else {
%>
								<a style="font-weight: bold;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;└&nbsp;<%=new UserDAOImpl().getNickname(cmt2.getUserID())%></a>
<%
						}
%>
								<small>&nbsp;&nbsp;&nbsp;&nbsp;- <%= cmt2.getCommentTime() %></small>
								<small><a onclick="return confirm('댓글을 삭제하시겠습니까?')" 
								href="./commentDelete?boardType=<%= cmt2.getboardType() %>&commentID=<%= cmt2.getCommentID() %>&writeID=<%= idx %>" 
								style="color: red;">삭제</a></small>
							</div> 	
							<div class="col pl-4 pl-10">
								<small>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%= cmt2.getCommentContent() %></small>
							</div>
						</div>
<%
					}
				}
%>
			</div>
<%
				cmtList.remove(i);
				i--;
			}
		}
	}
%> 	
		</div>
		<div class="modal" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="modal">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">
						<form method="post" action="commentRegister?boardType=<%=boardType%>&idx=<%=idx%>">
							<input type="hidden" id="cmntid" name="preComment" value="">
							<div class="container">
								<textarea  name="commentContent" class="form-control" maxlength="2048" 
								style="height: 120px; width: 100%;" placeholder="댓글내용을 입력하세요."></textarea>
							</div>
							<div class="form-group text-right mt-3 mr-3 mb-0">
								<button class="btn btn-success" type="submit">등 록</button>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
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
	<script src="resources/js/jquery.min.js" /></script>
	<script src="resources/js/popper.min.js" /></script>
	<script src="resources/js/bootstrap.min.js" /></script>
</body>
</html>