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

@WebServlet("/DeleteMemberController")
public class DeleteMemberController extends HttpServlet {
	// doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 값 요청
		HttpSession session = request.getSession();
	    String sessionMemberId = (String)session.getAttribute("sessionMemberId");
	    // 로그인 check
	    if(sessionMemberId == null) {
	        response.sendRedirect(request.getContextPath() + "/LoginController");
	        System.out.println("logout");
	        return;
	      }
	    // 회원탈퇴 Form 호출 -> memberId 이용
	    request.setAttribute("memberId", sessionMemberId);
	    request.getRequestDispatcher("/WEB-INF/view/deleteMemberForm.jsp").forward(request, response);
	}	
	
	// doPost
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 값 요청
		HttpSession session = request.getSession();
	    String sessionMemberId = (String)session.getAttribute("sessionMemberId");
	    // 로그인 check
	    if(sessionMemberId == null) {
	        response.sendRedirect(request.getContextPath() + "/LoginController");
	        System.out.println("logout");
	        return;
	      }
		// null check
	    // if) null이면, request DeleteMemberController
	    if(request.getParameter("memberPw") == null || request.getParameter("memberId") == null) {
	    	response.sendRedirect(request.getContextPath()+"/DeleteMemberController?msg=null");
	    	return;
	    }
	    // request
	    Member member = new Member();
	    member.setMemberId(request.getParameter("memberId"));
	    member.setMemberPw(request.getParameter("memberPw"));
	    // 디버깅
	    System.out.println(member.toString() + "DeleteMemberController.dopost");
	    // dao.deleteMember request
	    MemberDao memberDao = new MemberDao();
	    int row = memberDao.deleteMember(member);
	    
	    // 회원 탈퇴 성공 check 코드
	    // 1) 회원 탈퇴 성공시, Login.jsp page로 이동
	    if (row == 1) { 
	    	System.out.println("삭제성공 DeleteMemberController.dopost");
	    	request.getSession().invalidate();//session 갱신 메서드-로그아웃
	    	response.sendRedirect(request.getContextPath() + "/LoginController");
	    	return;
	    }
	    // 2) if) row가 0값이면 영향받은 행이 없으므로 (row 기본값 -1), 삭제 실패 -> 비밀번호 오류
	    else if(row == 0) {
	    	System.out.println("삭제실패 deleteMemberController.dopist");
	    	response.sendRedirect(request.getContextPath() + "/DeleteMemberController?msg=wrongPw");
	    	return;
	    }
	    // 3) row가 0값이면, SQL오류 -> 예외 발생
	    else if (row == -1) {
	    	System.out.println("예외 발생 DEleteMemberController.dopist");
	    	response.sendRedirect(request.getContextPath() + "/DeleteMemberController?msg=exception");
	    	return;
	    }
	}
}