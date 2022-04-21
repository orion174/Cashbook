<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import ="vo.Cashbook" %>
<%
	
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Update Cash Book Form</title>
</head>
<body>
<%
	// UpdateCashBookController request
	Cashbook cashbook = (Cashbook)request.getAttribute("cashbook");
%>
	<h1>Cash Book 수정</h1>
	<form method ="post" action ="<%=request.getContextPath()%>/UpdateCashBookController">
		<input type ="hidden" name="cashbookNo" value=<%=cashbook.getCashbookNo() %>>
		<table border="1">
			<tr>
				<td>cashDate</td>
				<td>
					<input type = "date"  value ="<%=cashbook.getCashDate()%>" name ="cashDate" >
				</td>
			</tr>
			<tr>
				<td>kind</td>
				<td>
					<input type = "radio" name="kind" value="<%=cashbook.getKind()%>" checked="checked"><%=cashbook.getKind()%>
					<input type = "radio" name="kind" value="수입">수입 
					<input type = "radio" name="kind" value="지출">지출
				</td>
			</tr>
			<tr>
				<td>cash</td>
				<td>
					<input type = "number" name ="cash" value="<%=cashbook.getCash() %>" >
				</td>
			</tr>
			<tr>
				<td>memo</td>
				<td>
					<textarea rows="5" cols="50" name ="memo"><%=cashbook.getMemo() %></textarea>
				</td>
			</tr>
			<tr>
				<td colspan ="2">
					<button type = "submit" class="btn btn-success">수정</button>
				</td>
			</tr>
		</table>
	</form>
	
	<a href="<%=request.getContextPath()%>/CashBookOneController?cashBookNo=<%=cashbook.getCashbookNo()%>" type ="button">수정취소</a>
	<a  href="<%=request.getContextPath()%>/CashBookListByMonthController" type ="button">리스트</a>
	
</body>
</html>