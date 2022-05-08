<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>login page</title>
</head>
<body>
<%
	// error 처리
	String msg = "";
		if(request.getParameter("msg") != null){
		if(request.getParameter("msg").equals("failLogin")) {
			msg ="Please check your id(pw)";
		}
	}
%>
	<h1>로그인</h1>
	<h2><%=msg%></h2>
	<form action="<%=request.getContextPath()%>/LoginController" method="post">
		<table border="1">
			<tr>
				<td>ID</td>
				<td><input type="text" name="memberId"></td>
			</tr>
			<tr>
				<td>password</td>
				<td><input type="password" name="memberPw"></td>
			</tr>
		</table>
		<button type="submit">로그인</button>
		<a  href="<%=request.getContextPath()%>/InsertMemberController" type="button">회원가입</a>
	</form>
</body>
</html>
