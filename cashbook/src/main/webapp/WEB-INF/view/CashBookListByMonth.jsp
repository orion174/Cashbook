<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>CashBookListByMonth</title>
   <!-- Latest compiled and minified CSS -->
   <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
   <!-- jQuery library -->
   <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.0/dist/jquery.slim.min.js"></script>
   <!-- Popper JS -->
   <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
   <!-- Latest compiled JavaScript -->
   <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="container">
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
   <table class="table table-bordered table-striped">
      <thead>
         <tr>
            <th>일</th>
            <th>월</th>
            <th>화</th>
            <th>수</th>
            <th>목</th>
            <th>금</th>
            <th>토</th>
         </tr>
      </thead>
      <tbody>
         <tr>
            <%
               for(int i=1; i<=totalTd; i+=1) {
                  if((i-startBlank) > 0 && (i-startBlank) <= endDay) {
                     String c = "";
                     if(i%7==0) {
                        c = "text-primary";
                     } else if(i%7==1) {
                        c = "text-danger";
                     }
            %>
                     <td class="<%=c%>">
                        <%=i-startBlank%>
                        <a href="<%=request.getContextPath()%>/InsertCashBookController?y=<%=y%>&m=<%=m%>&d=<%=i-startBlank%>" class="btn btn-light">입력</a>
                        <div>
                           <%
                              // 해당 날짜의 cashbook 목록 출력
                              for(Map map : list) {
                                 if((Integer)map.get("day") == (i-startBlank)) {
                           %>
                                    <div>
                                    <a href="<%=request.getContextPath()%>/CashBookOneController?cashbookNo=<%=map.get("cashbookNo")%>">
                                       [<%=map.get("kind")%>] 
                                       <%=map.get("cash")%>원
                                       <%=map.get("memo")%>...
                                    </a>
                                    </div>
                           <%         
                                 }
                              }
                           %>
                        </div>
                     </td>
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
      </tbody>
   </table>
</body>
</html>