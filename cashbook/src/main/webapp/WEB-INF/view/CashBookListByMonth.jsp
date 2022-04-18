<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CashBookListByMonth</title>
</head>
<body>
	<%
		int y = (Integer)request.getAttribute("y");
		int m = (Integer)request.getAttribute("m");
	%>
	<h2><%=y%>년 <%=m%>월 </h2>
	<table border="1">
		<tr>
			<th>day</th>
			<th>kind</th>
			<th>cash</th>
		</tr>
		<%	
			List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
			for(Map map : list) {
		%>
			<tr>
				<td><%=map.get("day")%></td>
				<td><%=map.get("kind")%></td>
				<td><%=map.get("cash")%></td>
			</tr>
		<%
			}
		%>
	</table>
	<div>
		<a href="<%=request.getContextPath()%>/CashBookListByMonthController?y=<%=y%>&m=<%=m-1%>">이전</a>
		<a href="<%=request.getContextPath()%>/CashBookListByMonthController?y=<%=y%>&m=<%=m+1%>">다음</a>
	</div>
</body>
</html>