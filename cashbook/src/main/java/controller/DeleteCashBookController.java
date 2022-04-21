package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;

@WebServlet("/DeleteCashBookController")
public class DeleteCashBookController extends HttpServlet {
	// doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// NULL 체크
		if(request.getParameter("cashbookNo") == null) {
		  response.sendRedirect(request.getContextPath() + "/CashBookListByMonthController?msg=null");
		  return;
		}
		
		// request
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		// 디버깅
		System.out.println(cashbookNo + " <-- cashbookNo DeleteCashBookController");
		// dao
		CashbookDao cashbookdao = new CashbookDao();
		// dao.deleteCashBook 요청
		cashbookdao.deleteCashBook(cashbookNo);
		
		// Redirect
		// 삭제 완료 후 클라리언트에게 CashBookListByMonthController를 요청
		response.sendRedirect(request.getContextPath() + "/CashBookListByMonthController");
	}	
}