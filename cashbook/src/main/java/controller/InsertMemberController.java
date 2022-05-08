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

@WebServlet("/InsertMemberController")
public class InsertMemberController extends HttpServlet {
	// doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 이미 로그인 상태면 CashBookByMonthController 호출
		HttpSession session = request.getSession();
		if(session.getAttribute("sessionMemberId") != null) {
			response.sendRedirect(request.getContextPath() + "/CashBookByMonthController");
			return;
		}
		// 로그인 상태가 아니면 insertMemberForm.jsp 호출
		request.getRequestDispatcher("/WEB-INF/view/insertMemberForm.jsp").forward(request, response);
	}
	
	// doPost
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 인코딩
		request.setCharacterEncoding("UTF-8");
		// 이미 로그인 상태면 CashBookByMonthController 호출
		HttpSession session = request.getSession();
		if(session.getAttribute("sessionMemberId") != null) {
			response.sendRedirect(request.getContextPath() + "/CashBookByMonthController");
			return;
		}
	    // null check
		// 요청값에 null있으면 UpdateMemberController 호출
	    if(request.getParameter("name") == null || request.getParameter("age") == null || request.getParameter("memberId") == null || request.getParameter("gender") == null) {
	    	System.out.println("null insertMembercontroller.dopost");
	    	response.sendRedirect(request.getContextPath() + "/UpdateMemberController?msg=null");
	    	return;
	    }
	    
	    // 요청값 처리
	    // memberPw = 비밀번호 선언후 초기화
	    String memberPw = null; 
	    if(request.getParameter("memberPw1") != null && request.getParameter("memberPw2") != null &&!request.getParameter("memberPw1").equals("") && request.getParameter("memberPw1").equals(request.getParameter("memberPw2"))) {
	    // null, 빈칸이 아닌 비밀번호가 둘이 일치한다면 memberPw에 저장
	     memberPw = request.getParameter("memberPw1");
	    } 
	    // null, 빈칸은 아니지만 memberPw1, memberPw2가 일치하지 않으면, InsertMemberController 호출 
	    else if(request.getParameter("memberPw1") != null && request.getParameter("memberPw2") != null&&!request.getParameter("memberPw1").equals("") &&!request.getParameter("memberPw1").equals(request.getParameter("memberPw2"))) {
	    	response.sendRedirect(request.getContextPath()+"/InsertMemberController?msg=passwordisnotMatch");
	    	return;
	    }
	    
	    // vo
	    Member member = new Member();
	    member.setMemberId(request.getParameter("memberId"));
	    member.setName(request.getParameter("name"));
	    member.setAge(Integer.parseInt(request.getParameter("age")));
	    member.setGender(request.getParameter("gender"));
	    member.setMemberPw(memberPw);
	    System.out.println(member.toString() + "<-insertMemberController.dopost");
	    
	    // dao.insertMember 호출
	    MemberDao memberDao = new MemberDao();
	    int row = memberDao.insertMember(member);
	    
	    // 회원 가입 성공 check코드
	    // 1) 회원가입 성공시, Logincontroller 호출
	    if (row == 1) { 
	    	System.out.println("가입성공 InsertMemberController.dopist");
	    	response.sendRedirect(request.getContextPath() + "/LoginController");
	    	return;
	    }
	    // 2) 회원가입 실패시(row값이 0이면...), 가입실패 + InsertMemberController 호출
	    else if(row == 0) {
	    	System.out.println("가입실패 insertMemberController.dopist");
	    	response.sendRedirect(request.getContextPath() + "/InsertMemberController?msg=insertmemberfail");

	    }
	    // 3) row값이 -1이면(default 값) SQL오류
	    else if (row == -1) {
	    	System.out.println("예외 발생 insertMemberController.dopist");
	    	response.sendRedirect(request.getContextPath() + "/InsertMemberController?msg=exception");
	    }
	}
}
