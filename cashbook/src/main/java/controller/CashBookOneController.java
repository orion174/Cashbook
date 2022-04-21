package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;
import vo.Cashbook;

@WebServlet("/CashBookOneController")
public class CashBookOneController extends HttpServlet {
	// doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// NULL 값 체크
		if(request.getParameter("cashbookNo") == null) {
			  response.sendRedirect(request.getContextPath()+"/CashBookListByMonthController?msg=null");
			  return;
			}
		
		// selectCashBookOne request
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		// vo
		Cashbook cashbook = new Cashbook();
		// dao
		CashbookDao cashbookDao = new CashbookDao();
		// dao.selectCashBookOne
		cashbook = cashbookDao.selectCashBookOne(cashbookNo); 
		
		request.setAttribute("cashbook", cashbook);
		// forward
		request.getRequestDispatcher("/WEB-INF/view/cashBookOne.jsp").forward(request, response);
	}
}