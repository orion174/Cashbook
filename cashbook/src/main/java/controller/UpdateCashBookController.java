package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CashbookDao;
import vo.Cashbook;

@WebServlet("/UpdateCashBookController")
public class UpdateCashBookController extends HttpServlet {
	// doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// NULL 체크
		if(request.getParameter("cashbookNo") == null) {
		  response.sendRedirect(request.getContextPath() + "/CashBookListByMonthController?msg=null");
		  return;
		}
		
		// request
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		System.out.println(cashbookNo + " <-- cashbookNo UpdateCashBookController.doget");  // 디버깅
		
		// dao
		CashbookDao cashbookDao = new CashbookDao();
		// vo
		Cashbook cashbook = new Cashbook();
		// dao.selectCashBookOne 상세보기 요청 
		cashbook = cashbookDao.selectCashBookOne(cashbookNo); 
		
		request.setAttribute("cashbook", cashbook);
		request.getRequestDispatcher("/WEB-INF/view/updateCashBookForm.jsp").forward(request, response);
	}

	// doPost
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글깨짐 방지 인코딩
		request.setCharacterEncoding("UTF-8"); 
		// request 
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		int cash = Integer.parseInt(request.getParameter("cash"));
		String cashDate = request.getParameter("cashDate"); 
		String kind = request.getParameter("kind");
		String memo = request.getParameter("memo");
		// 디버깅
		System.out.println(cashbookNo + "<-- cashbookNo UpdateCashBookController.dopost");
		System.out.println(cash + " <-- cash updateCashBookController");
		System.out.println(cashDate + " <-- cashDate updateCashBookController");
		System.out.println(kind + " <-- kind updateCashBookController");
		System.out.println(memo + " <-- memo updateCashBookController");
		
		// tag 값 구하기
		// 해시태그 처리 -> String 기본 메서드 사용 ...
		List<String> hashtag = new ArrayList<String>(); 
		// replace : 자신이 바꾸고 싶은 문자로 문자열을 치환해주는 함수
		String memo2 = memo.replace("#"," #"); // ##방지 -> '#' 을 ' #'로 치환하여 memo2에 저장
		// split : 특정문자를 기준으로 문자열을 나누어 배열에 저장하여 리턴
		String[] arr = memo2.split(" "); // " " <- 공백을 사용하여 문자열을 분리
		for(String s : arr) {
			if(s.startsWith("#")) { // startsWith : 대상 문자열이 특정 문자 or 문자열로 시작하는지 체크 -> #로 시작하는지?
				String temp = s.replace("#",""); // '#'를 공백으로 치환하여 temp에 임시저장
				if(!temp.equals("")) { // temp가 공백이 아니라면 hashtag에 추가
				hashtag.add(temp);
				}
			}
		}
		
		// 해시태크 디버깅
		System.out.println(hashtag.size() + "hashtag.size UpdateCashBookController.dopost");
		for(String h : hashtag) {
			System.out.println(h + "<--hashtag UpdateCashBookController.dopost");
		}
		
		// vo
		Cashbook cashbook = new Cashbook();
		cashbook.setCashbookNo(cashbookNo);
		cashbook.setCashDate(cashDate);
		cashbook.setKind(kind);
		cashbook.setCash(cash);
		cashbook.setMemo(memo);
		
		// dao
		CashbookDao cashbookDao = new CashbookDao();
		// dao.updateCashBook 호출
		cashbookDao.updateCashBook(cashbook,hashtag);
		
		// Redirect
		response.sendRedirect(request.getContextPath() + "/CashBookListByMonthController");
	}

}