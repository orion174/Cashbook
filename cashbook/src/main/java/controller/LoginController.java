package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.MemberDao;
import vo.Member;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	
	// 로그인 폼
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String sessionMemberId = (String)session.getAttribute("sessionMemberId");
		if(sessionMemberId != null) {
			// 이미 로그인이 되어 있는 상태라면
			response.sendRedirect(request.getContextPath() + "/CashBookListByMonthController");
			return;
		}
		// 로그인 되어있는 멤버라면 리다이렉트
		request.getRequestDispatcher("/WEB-INF/view/Login.jsp").forward(request, response);
	}
	// 로그인 액션
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// if) 이미 로그인 상태면, CashBookByMonthController 호출
		HttpSession session = request.getSession();
		if(session.getAttribute("sessionMemberId") != null) {
			response.sendRedirect(request.getContextPath() + "/CashBookListByMonthController"); 
			return;
		}
		// request 값 처리
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		
		// 모델 호출
		MemberDao memberDao = new MemberDao();
		String returnMemberId = memberDao.selectMemberByIdPw(member);
		if(returnMemberId == null) {
			// 로그인 실패시 로그인 폼을 재요청
			System.out.println("로그인실패 <-- LoginController.doPost()");
			response.sendRedirect(request.getContextPath()+"/LoginController");
			return;
		}
		// 로그인 성공
		// session에 sessionMemberId 저장
		session.setAttribute("sessionMemberId", returnMemberId);
		response.sendRedirect(request.getContextPath() + "/CashBookListByMonthController");
	}
}
