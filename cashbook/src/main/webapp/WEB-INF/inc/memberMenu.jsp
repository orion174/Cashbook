<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member menu</title>
</head>
<body>
	<!-- 회원 정보 -->
	<h1>
		<%=session.getAttribute("sessionMemberId") %>님 반갑습니다.
	</h1>
		<a href="<%=request.getContextPath()%>/SelectMemberOneController" type="button">회원정보</a>
		<a href="<%=request.getContextPath()%>/LogoutController" type="button">로그아웃</a>
	
</body>
</html>