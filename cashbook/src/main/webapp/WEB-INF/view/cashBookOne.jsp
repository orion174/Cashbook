<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import ="vo.Cashbook" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>cashBookOne</title>
</head>
<body>
<%
	// CashBookOneController request
	Cashbook cashbook = (Cashbook)request.getAttribute("cashbook");
%>
	<h1>Cashbook 상세보기</h1>
	<table border="1">
		<tr>
			<td>cashDate</td>
			<td><%= cashbook.getCashDate() %></td>
		</tr>
		<tr>
			<td>kind</td>
			<td><%=cashbook.getKind()%></td>
		</tr>
		<tr>
			<td>cash</td>
			<td><%= cashbook.getCash() %></td>
		</tr>
		<tr>
			<td>memo</td>
			<td><%= cashbook.getMemo() %></td>
		</tr>
		<tr>
			<td>createDate</td>
			<td><%= cashbook.getCreateDate() %></td>
		</tr>
		<tr>
			<td>updateDate</td>
			<td><%= cashbook.getUpdateDate() %></td>
		</tr>
	</table>
	
	<a href="<%=request.getContextPath()%>/UpdateCashBookController?cashbookNo=<%=cashbook.getCashbookNo()%>" type="button">수정</a>
	<a href="<%=request.getContextPath()%>/DeleteCashBookController?cashbookNo=<%=cashbook.getCashbookNo()%>" type="button">삭제</a>
	<a href="<%=request.getContextPath()%>/CashBookListByMonthController" type="button">Cashbook List</a>
	
</body>
</html>