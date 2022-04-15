<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ page import ="java.util.*" %>
<%@ page import ="dao.CategoryDao" %>
<%@ page import ="vo.Category"%>
<% 
	// MVC
	// 3) 리스트 View
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>categoryListView</title>
</head>
<body>
	<h1>Category List View</h1>
	<table border="1">
		<thead>
		<tbody>
			<%
				List<Category> list = (List<Category>)request.getAttribute("list");
				for(Category c : list) {
			%>
				<tr>
					<td><%=c.getCategoryNo()%></td>
					<td><%=c.getCategoryTitle()%></td>
				</tr>
			<%
				}
			%>
		</tbody>
	</table>
</body>
</html>