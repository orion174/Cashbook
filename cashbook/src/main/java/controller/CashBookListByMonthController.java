package controller;

import java.io.IOException;
import java.util.*;

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
		Calendar today = Calendar.getInstance();
		int y = today.get(Calendar.YEAR);
		int m = today.get(Calendar.MONTH) + 1; // 0 -> 1월, 1 -> 2월, ...
		
		if(request.getParameter("y") != null ) {
			y = Integer.parseInt(request.getParameter("y"));
		}
		if(request.getParameter("m") != null ) {
			m = Integer.parseInt(request.getParameter("m"));
		}
		//
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
		
		// 2) 모델값(월별 가계부 리스트)을 반환하는 비지니스로직(모델) 호출
		CashbookDao cashbookDao = new CashbookDao();
		List<Map<String, Object>> list = cashbookDao.selectCashbookListByMonth(y, m);
		request.setAttribute("list", list);
		request.setAttribute("y", y);
		request.setAttribute("m", m);
		
		// 3) 뷰 포워딩
		request.getRequestDispatcher("/WEB-INF/view/CashBookListByMonth.jsp").forward(request, response);
	}

}
