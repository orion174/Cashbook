<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import ="vo.Member"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>member one</title>
</head>
<body>
<%
	// receive controller
	Member member = (Member)request.getAttribute("member");
%>
<h1>회원정보 상세보기</h1>
	<table border="1">
	<tr>
		<td>ID</td>
		<td><%=member.getMemberId() %></td>
	</tr>
	<tr>
		<td>이름</td>
		<td><%=member.getName() %></td>
	</tr>
	<tr>
		<td>성별</td>
		<td><%=member.getGender() %></td>
	</tr>
	<tr>
		<td>나이</td>
		<td><%=member.getAge() %></td>
	</tr>
	<tr>
		<td>회원가입일</td>
		<td><%=member.getCreateDate() %></td>
	</tr>
	</table>
		<a  href="<%=request.getContextPath()%>/UpdateMemberController" type="button">정보수정</a>
		<a  href="<%=request.getContextPath()%>/DeleteMemberController" type="button">탈퇴</a>
		<a  href="<%=request.getContextPath()%>/CashBookListByMonthController" type="button">cashbook</a>
</body>
</html> 