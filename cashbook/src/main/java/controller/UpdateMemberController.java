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

@WebServlet("/UpdateMemberController")
public class UpdateMemberController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// session 값 요청
		HttpSession session = request.getSession();
	    String sessionMemberId = (String)session.getAttribute("sessionMemberId");
	    // 로그인 check
	    if(sessionMemberId == null) {
	        response.sendRedirect(request.getContextPath() + "/LoginController");
	        System.out.println("log out");
	        return;
	      }
	    
	    // vo
	    Member member = new Member();
	    // dao.selectMemberOne
	    MemberDao memberDao = new MemberDao();
	    member  = memberDao.selectMemberOne(sessionMemberId); // memberId로 DB값 호출
	    request.setAttribute("member", member);
	    request.getRequestDispatcher("/WEB-INF/view/updateMemberForm.jsp").forward(request, response);
	}	

	// doPost
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글 깨짐 방지 인코딩
		request.setCharacterEncoding("UTF-8");
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
	    if(request.getParameter("name") == null || request.getParameter("age") == null || request.getParameter("memberPw") == null || request.getParameter("memberId") == null || request.getParameter("gender") == null) {
	    	System.out.println("null UpdateMembercontroller.dopost");
	    	response.sendRedirect(request.getContextPath() + "/UpdateMemberController");
	    	return;
	    }
	    // Form request 값 처리
	    // vo
	    Member member = new Member();
	    member.setMemberId(request.getParameter("memberId"));
	    member.setName(request.getParameter("name"));
	    member.setAge(Integer.parseInt(request.getParameter("age")));
	    member.setGender(request.getParameter("gender"));
	    member.setMemberPw(request.getParameter("memberPw"));
	    // 디버깅
	    System.out.println(member.toString() + "<-UPdateMemeberController.dopost");

	    // 비밀번호 수정 코드
	    // 새 비밀번호 = newMemberPw 선언 후 초기화
	    String newMemberPw = "";
	    if(request.getParameter("newPw") == null) {
	    	System.out.println("0000");
	    }
	    // 비밀번호 변경 요청 여부 확인
	    if(request.getParameter("newPw").equals("open")) {
	    	// newPw가 공백(null값) 아니고 + pw1,pw2가 일치하면 newMemberPw에 newPw값 저장하는 코드
	    	if(request.getParameter("newMemberPw1") != null &&!request.getParameter("newMemberPw1").equals("") && request.getParameter("newMemberPw1").equals(request.getParameter("newMemberPw2"))) {
	    		newMemberPw = request.getParameter("newMemberPw1");
	    		System.out.println(newMemberPw + "<- newMemberPw UpdateMemberController.dopost");
	    	}
	    	// newPw가 공백(null값) 아닌데, newMemberPw1 != newMemberPw2 일때 처리하는 코드(UpdateMemberController 호출)
	    	else if(request.getParameter("newMemberPw1") != null &&!request.getParameter("newMemberPw1").equals("") &&!request.getParameter("newMemberPw1").equals(request.getParameter("newMemberPw2"))) {
	    		response.sendRedirect(request.getContextPath() + "/UpdateMemberController?msg=newpasswordwasnotMatch");
	    		return;
	    	}
	    }
	   
	    // dao.updateMemberByIdPw 호출
	    MemberDao memberDao = new MemberDao();
	    int row = memberDao.updateMemberByIdPw(member, newMemberPw);
	    System.out.println(row + "<-row UpdateMemberController.dopist");
	    
	    // 비밀번호 수정 성공 확인 코드
	    // 1) row값이 1이면 비밀번호 수정 성공 -> SelectMemberOneController 호출
	    if (row == 1) {
	    	System.out.println("수정성공 UpdateMemberController.dopist");
	    	response.sendRedirect(request.getContextPath() + "/SelectMemberOneController");
	    	return;
	    }
	    // 2) row값이 0이면 비밀번호 수정 오류 -> UpdateMemberController 호출
	    else if(row == 0) {
	    	System.out.println("수정실패비밀번호오류 UpdateMemberController.dopist");
	    	response.sendRedirect(request.getContextPath() + "/UpdateMemberController?msg=failrenewpassword");
	    }
	    // 3) row값이 -1이면 SQL 오류
	    else if (row == -1) {
	    	System.out.println("예외 발생 UpdateMemberController.dopist");
	    	response.sendRedirect(request.getContextPath() + "/UpdateMemberController?msg=exception");
	    }
	}
}