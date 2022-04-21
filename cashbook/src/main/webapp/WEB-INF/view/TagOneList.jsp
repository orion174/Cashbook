<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insert title here</title>
</head>
<body>
<%
	// request
	List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
%>
	<h1>Tag One (상세보기)</h1>
	<form method="get" action="<%=request.getContextPath()%>/TagController">
			<div>
				<button type="submit">back</button>
			</div>
	</form>
			<table border="1">
				<tr>
					<th>tag</th>
					<th>cashDate</th>
					<th>kind</th>
					<th>cash</th>
					<th>memo</th>
				</tr>
				<%
					for(Map<String, Object> map : list) {
				%>
						<tr>
							<td><%=map.get("tag")%></td>
							<td><%=map.get("cashDate")%></td>
							<td><%=map.get("kind")%></td>
							<td><%=map.get("cash")%></td>
							<td><%=map.get("memo")%></td>
						</tr>
				<%			
					}
				%>
			</table> 
</body>
</html>