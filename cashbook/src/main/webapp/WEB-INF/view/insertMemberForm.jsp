<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import ="vo.Member"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>insert member form</title>
</head>
<body>
<%
	// error 메세지 값 받기 코드
	String msg = "";
	if(request.getParameter("msg") != null){
		if(request.getParameter("msg").equals("notMatch")){
			msg = "Please check your password again";
		}
	}
%>
	<h1>회원가입</h1>
	<!-- 오류메세지 출력 -->
	<h2><%=msg%></h2>
	<form method="post" action="<%=request.getContextPath()%>/InsertMemberController">
		<div>
			<table border="1">
				<tr>
					<td>ID 입력</td>
					<td>
						<input type="text" name="memberId">
					</td>
				</tr>
				<tr>
					<td>비밀번호 입력</td>
					<td>
						<input type = "password" name ="memberPw1" required>
					</td>
				</tr>
				<tr>
					<td>비밀번호 확인</td>
					<td>
						<input type = "password" name ="memberPw2" required>
					</td>
				</tr>
				<tr>
					<td>이름 입력</td>
					<td>
						<input type="text" name="name" required>
					</td>
				</tr>
				<tr>
					<td>성별 입력</td>
					<td>
 						<select name="gender">
							<option value="남">남</option>
							<option value="여">여</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>나이 입력</td>
					<td>
						<input type="number" name="age" required>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<button type ="submit">회원가입</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
		<a  href="<%=request.getContextPath()%>/LoginController" type="button">이전</a>
</body>
</html> 