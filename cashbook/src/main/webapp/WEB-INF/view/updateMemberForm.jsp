<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<%@ page import ="vo.Member"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>update member form</title>
</head>
<body>
<%
	// receive controller
	Member member = (Member)request.getAttribute("member");
	// error 메세지
	String msg = "";
	if(request.getParameter("msg") != null){
		if(request.getParameter("msg").equals("wrongPw")) {
			msg = "Existing password does not match";
		}
		else if(request.getParameter("msg").equals("notMatch")) {
			msg = "Please check your password again";
		}
	}
	
	// 비밀번호 변경 여부 check
	String newPw="close"; // defalut = close(변경x) / open -> 변경
	
%>
	<h1>회원정보 수정</h1>
	<!-- 오류 메세지 출력 -->
	<h2><%=msg%></h2>
	<form method="post" action="<%=request.getContextPath()%>/UpdateMemberController">
		<div>
			<table border="1">
				<tr>
					<td>ID</td>
					<td>
						<input type="text" name="memberId" readonly="readonly" value="<%=member.getMemberId() %>">
					</td>
				</tr>
				<tr>
					<td>현재 비밀번호 입력</td>
					<td>
						<input type="password" name="memberPw" required>
						<a href="<%=request.getContextPath()%>/UpdateMemberController?newPw=open" type ="button">비밀번호 변경</a>
					</td>
				</tr>
				<%
					// defalut = close(변경x) / open -> 변경
					if(request.getParameter("newPw") != null && request.getParameter("newPw").equals("open")) {
						newPw = "open"; 
				%>
				<!-- 새로운 비밀번호 입력 여부 -->
					<tr>
						<td>새 비밀번호 입력</td>
						<td>
							<input type = "password" name ="newMemberPw1" value="" required>
							<a  href="<%=request.getContextPath()%>/UpdateMemberController" type ="button">취소</a>
						</td>
					</tr>
					<tr>
						<td>새 비밀번호 확인</td>
						<td>
							<input type = "password" name ="newMemberPw2"  value="" required>
						</td>
					</tr>
				<%
					}		
				%>
				<tr>
					<td>이름 수정</td>
					<td>
						<input type="text" name="name" value="<%=member.getName()%>" required>
					</td>
				</tr>
				<tr>
					<td>성별 수정</td>
					<td>
						<select name = "gender">
							<option value="<%=member.getGender()%>"><%=member.getGender()%></option>
							<option value="남">남</option>
							<option value="여">여</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>나이 수정</td>
					<td>
						<input type = "number" name="age" value="<%=member.getAge() %>" required>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<!-- 비밀번호 변경여부 체크 -->
						<input type="hidden" name="newPw" value="<%=newPw%>">
						<button type="submit">회원 정보 수정</button>
					</td>
				</tr>
			</table>
		</div>
	</form>
		<a  href="<%=request.getContextPath()%>/SelectMemberOneController" type ="button">이전</a>
		<a  href="<%=request.getContextPath()%>/CashBookListByMonthController" type ="button">cashbook</a>
</body>
</html> 