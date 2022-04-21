package controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;

@WebServlet("/CashBookListByMonthController")
public class CashBookListByMonthController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1) 월별 가계부 리스트 요청 분석
		Calendar now = Calendar.getInstance(); // ex) 2022.04.19
		int y = now.get(Calendar.YEAR);
		int m = now.get(Calendar.MONTH) + 1; // 0 - 1월, 1 - 2월, ... 11 - 12월
		
		if(request.getParameter("y") != null) {
			y = Integer.parseInt(request.getParameter("y"));
		}
		if(request.getParameter("m") != null) {
			m = Integer.parseInt(request.getParameter("m"));
		}
		if(m==0) {
			m = 12;
			y = y-1;
		}
		if(m==13) {
			m = 1;
			y = y+1;
		}
		
		System.out.println(y + " <-- y");
		System.out.println(m + " <-- m");
		
		/*
		 
		 1) startBlank
		 2) endDay
		 3) endBlank
		 4) totalBlank
		 
		 */
		
		// 시작시 필요한 공백 <TD>의 갯수를 구하는 알고리즘 -> startBlank 
		// firstDay는 오늘날짜를 먼저구하여 날짜만 1일로 변경해서 구하자
		Calendar firstDay = Calendar.getInstance(); // ex) 2022.04.19
		firstDay.set(Calendar.YEAR, y);
		firstDay.set(Calendar.MONTH, m-1); // 자바 달력API는 1월을 0으로, 2월을 1로, ... 12월을 11로 설정되어있음
		firstDay.set(Calendar.DATE, 1); // ex) 2022.04.01
		int dayOfWeek = firstDay.get(Calendar.DAY_OF_WEEK);
		// dayOfWeek 	일1, 월2, ... 토7
		// startBlank 	일0, 월1, ... 토6
		// 1)
		int startBlank = dayOfWeek - 1;
		// 마지막 날짜는 자바 달력api를 이요하여 구하자
		// 2)
		int endDay = firstDay.getActualMaximum(Calendar.DATE);// firstDay달의 제일 큰수자 -> 마지막날짜
		// strartBlank와 endDay를 합의 결과에 endBlank를 더해서 7의 배수가 되도록
		// 3)
		int endBlank = 0;
		if((startBlank+endDay)%7 != 0) {
			// 7에서 startBlank+endDay의 7로 나눈 나머지값을 빼면
			endBlank = 7-((startBlank+endDay)%7);
		}
		// 4)
		int totalTd = startBlank + endDay + endBlank;
		
		
		// 2) 모델값(월별 가계부 리스트)을 반환하는 비지니스로직(모델) 호출
		CashbookDao cashbookDao = new CashbookDao();
		List<Map<String, Object>> list = cashbookDao.selectCashbookListByMonth(y, m);
		/*
		 달력출력에 필요한 모델값(1), 2), 3), 4)) + 데이터베이스에서 반환된 모델값(list, y출력년도, m출력월)
		 */
		request.setAttribute("startBlank", startBlank);
		request.setAttribute("endDay", endDay);
		request.setAttribute("endBlank", endBlank);
		request.setAttribute("totalTd", totalTd);
		
		request.setAttribute("list", list);
		request.setAttribute("y", y);
		request.setAttribute("m", m);
		// 3) 뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/view/CashBookListByMonth.jsp").forward(request, response);
	}

}

