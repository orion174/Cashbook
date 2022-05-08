<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import ="vo.Member"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>delete member form</title>
</head>
<body>
<%
	// request controller
	Member member = (Member)request.getAttribute("member");

	// error 메세지 값 받기 코드
	String msg = ""; 
	if(request.getParameter("msg") != null){
		if(request.getParameter("msg").equals("wrongPw")) {
			msg = "Existing passwords do not match.";
		}
	}
	
%>
	<h1>회원탈퇴</h1>
	<!-- 오류 메세지 출력 -->
	<h2><%=msg %></h2>
	<form method="post" action="<%=request.getContextPath()%>/DeleteMemberController">
		<div>
			<table border="1">
				<tr>
					<td>memberId</td>
					<td>
						<input type="text" name="memberId" readonly="readonly" value="<%=request.getAttribute("memberId")%>">
					</td>
				</tr>
				<tr>
					<td>회원 탈퇴를 원하시면, 비밀번호 입력를 입력하시오</td>
					<td>
						<input type="password" name="memberPw" required>
						<div>비밀번호 입력시, 회원 탈퇴됩니다.</div>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<button type ="submit">삭제</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
		<a  href="<%=request.getContextPath()%>/SelectMemberOneController" type ="button">이전</a>
		<a  href="<%=request.getContextPath()%>/CashBookListByMonthController" type ="button">cashbook</a>
</body>
</html> 