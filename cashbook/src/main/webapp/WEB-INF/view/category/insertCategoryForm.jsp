<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 	// MVC
	// 3) 입력Form View
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insertCategoryForm</title>
</head>
<body>
	<h1>카테고리 등록</h1>
	<form method="post" action="<%=request.getContextPath()%>/category/InsertCategoryController">
		<table>
			<tr>
				<th>카테고리</th>
				<td>
					<input type="text" name="categoryTitle">
				</td>
			</tr>
		</table>
		<button type="submit">등록하기</button>
	</form>
</body>
</html>
