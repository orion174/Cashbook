<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CashBookListByMonth</title>
</head>
<body>
	<%
		List<Map<String, Object>> list = (List<Map<String, Object>>)request.getAttribute("list");
		int y = (Integer)request.getAttribute("y");
		int m = (Integer)request.getAttribute("m");
		
		int startBlank = (Integer)request.getAttribute("startBlank");
		int endDay = (Integer)request.getAttribute("endDay");
		int endBlank = (Integer)request.getAttribute("endBlank");
		int totalTd = (Integer)request.getAttribute("totalTd");
		
		System.out.println(list.size() +" <- list.size() CaahBookListByMonth.jsp");
		System.out.println(y +" <- y CaahBookListByMonth.jsp");
		System.out.println(m +" <- m CaahBookListByMonth.jsp");
		
		System.out.println(startBlank +" <- startBlank CaahBookListByMonth.jsp");
		System.out.println(endDay +" <- endDay CaahBookListByMonth.jsp");
		System.out.println(endBlank +" <- endBlank CaahBookListByMonth.jsp");
		System.out.println(totalTd +" <- totalTd CaahBookListByMonth.jsp");
	%>
	<h2><%=y%>년 <%=m%>월</h2>
	<div>
		<a href="<%=request.getContextPath()%>/CashBookListByMonthController?y=<%=y%>&m=<%=m-1%>">이전달</a>
		<a href="<%=request.getContextPath()%>/CashBookListByMonthController?y=<%=y%>&m=<%=m+1%>">다음달</a>
	</div>
	<!-- 
		1) 이번날 1일의 요일 firstDayYoil -> startBlank -> 일 0, 월 1, 화 2, ... 토 6
		2) 이번달 마지막날짜 endDay
		3) endBlank -> totalBlank
		4) td의갯수 1 ~ totalBlank
				+		
		5) 가계부 list
		6) 오늘 날짜
	-->
	<table border="1">
		<tr>
			<%
				for(int i=1; i<=totalTd; i+=1) {
					if((i-startBlank) > 0 && (i-startBlank) <= endDay) {
			%>
						<td><%=i-startBlank%></td>
			<%			
					} else {
			%>
						<td>&nbsp;</td>
			<%			
					}
					if(i<totalTd && i%7==0) {
			%>
						</tr><tr><!-- 새로운 행을 추가시키기 위해 -->
			<%			
					}
				}
			%>
		</tr>
	</table>
</body>
</html>
