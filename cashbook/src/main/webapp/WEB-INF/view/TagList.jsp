<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	// request TagController 
	List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
%>
	<h1>Tag rank</h1>
		<form method="get" action="<%=request.getContextPath()%>/TagKindController">
			<div>
				[Kind rank]
				<input type="radio" name="kind" value="수입">수입
				<input type="radio" name="kind" value="지출">지출
				<button type="submit">Go</button>
			</div>
				
		</form>
		<form method="get" action="<%=request.getContextPath()%>/TagDateController">
			<div>
				[Date rank]
				<input type ="date" name = "beginDate" value ="<%=request.getAttribute("beginDate")%>"> ~
				<input type ="date" name = "endDate" value="<%=request.getAttribute("endDate")%>">
				<button type ="submit">검색</button>
			</div>
		</form>
	<table border="1">
		<tr>
			<th>rank</th>
			<th>tag</th>
			<th>count</th>
		</tr>
		<%
			for(Map<String, Object> map : list) {
		%>
				<tr>
					<td><%=map.get("rank")%></td>
					<td><a href="<%=request.getContextPath()%>/TagOneController?tag=<%=map.get("tag")%>"><%=map.get("tag")%></a></td>
					<td><%=map.get("cnt")%></td>
				</tr>
		<%			
			}
		%>
	</table>
</body>
</html>