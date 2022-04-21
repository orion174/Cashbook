package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;
import vo.Cashbook;
import dao.CashbookDao;

@WebServlet("/InsertCashBookController")
public class InsertCashBookController extends HttpServlet {
	// doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// y년, m월, d일
		String y = request.getParameter("y");
		String m = request.getParameter("m");
		String d = request.getParameter("d");
		String cashDate = y + "-" + m + "-" + d;
		
		request.setAttribute("cashDate", cashDate);
		request.getRequestDispatcher("/WEB-INF/view/insertCashBookForm.jsp").forward(request, response);
	}

	// doPost
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글 깨짐 방지 인코딩
		request.setCharacterEncoding("utf-8");
		// request
		String cashDate = request.getParameter("cashDate");
		String kind = request.getParameter("kind");
		int cash = Integer.parseInt(request.getParameter("cash"));
		String memo = request.getParameter("memo");
		
		// 디버깅
		System.out.println(cashDate + " <--cashDate InsertCashBookController.doPost()");
		System.out.println(kind + " <--kind InsertCashBookController.doPost()");
		System.out.println(cash + " <--cash InsertCashBookController.doPost()");
		System.out.println(memo + " <--memo InsertCashBookController.doPost()");
		
		// vo
		Cashbook cashbook = new Cashbook();
		cashbook.setCashDate(cashDate);
		cashbook.setKind(kind);
		cashbook.setCash(cash);
		cashbook.setMemo(memo);
		
		// 해시태그 처리 -> String 기본 메서드 사용 ...
		List<String> hashtag = new ArrayList<>();
		// replace : 자신이 바꾸고 싶은 문자로 문자열을 치환해주는 함수
		String memo2 = memo.replace("#", " #"); // ##방지 -> '#' 을 ' #'로 치환히여 memo2에 저장
		// split : 특정문자를 기준으로 문자열을 나누어 배열에 저장하여 리턴
		String[] arr = memo2.split(" "); // " " <- 공백을 사용하여 문자열을 분리
		
		for(String s : arr) {
			if(s.startsWith("#")) { // startsWith : 대상 문자열이 특정 문자 or 문자열로 시작하는지 체크 -> #로 시작하는지?
				String temp = s.replace("#", ""); // '#'를 공백으로 치환하여 temp에 임시저장
				if(temp.length()>0) { // temp가 공백이 아니면 hashtag에 추가
					hashtag.add(temp);
				}
			}
		}
		
		// 해시태그 디버깅
		System.out.println(hashtag.size() + " <--hashtag.size InsertCashBookController.doPost()");
		for(String h : hashtag) {
			System.out.println(h + " <-- hashtag InsertCashBookController.doPost()");
		}
		
		// dao
		CashbookDao cashbookDao = new CashbookDao();
		// dao.insertCashbook 호츌
		cashbookDao.insertCashbook(cashbook, hashtag);
		
		// Redirect
		response.sendRedirect(request.getContextPath() + "/CashBookListByMonthController");
	}
}
