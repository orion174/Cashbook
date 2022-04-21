package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HashtagDao;


@WebServlet("/TagDateController")
public class TagDateController extends HttpServlet {
	//doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 한글깨짐 방지 인코딩
		request.setCharacterEncoding("utf-8");
		// get request
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		// 디버깅
		System.out.println(beginDate + " <--beginDate");
		System.out.println(endDate + " <--endDate");
		// dao
		HashtagDao hashtagDao = new HashtagDao();
		// dao.selectDateTagRankList
		List<Map<String, Object>> list = hashtagDao.selectDateTagRankList(beginDate, endDate);
		
		// Request
		request.setAttribute("list", list);
		request.getRequestDispatcher("/WEB-INF/view/TagDateList.jsp").forward(request, response);
		
	}

}