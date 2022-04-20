<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>insertCashBook</h1>
	<form action="<%=request.getContextPath()%>/InsertCashBookController" method="post">
		<table border="1">
			<tr>
				<td>날짜</td>
				<td>
					<input type="text" name="cashDate" 
							value="<%=(String)request.getAttribute("cashDate")%>" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td>kind</td>
				<td>
					<input type="radio" name="kind" value="수입">수입
					<input type="radio" name="kind" value="지출">지출
				</td>
			</tr>
			<tr>
				<td>cash</td>
				<td><input type="number" name="cash"></td>
			</tr>
			<tr>
				<td>memo</td>
				<td>
					<textarea rows="4" cols="50" name="memo"></textarea>
				</td>
			</tr>
		</table>
		<button type="submit">입력</button>
	</form>
</body>
</html>
