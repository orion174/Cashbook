package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HashtagDao;

@WebServlet("/TagOneController")
public class TagOneController extends HttpServlet {
	// doGet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {			
		// 한글깨짐 방지 인코딩
		request.setCharacterEncoding("utf-8");
		// get request -> kind
		String tag = request.getParameter("tag");
		// 디버깅
		System.out.println(tag + " <-- tag");
		
		// dao 
		HashtagDao hashtagDao = new HashtagDao();
		// dao.selectTagOneList
		List<Map<String,Object>> list = hashtagDao.selectTagOneList(tag);
		
		// request -> list, tag
		request.setAttribute("list", list);
		request.setAttribute("kind", tag);
		request.getRequestDispatcher("/WEB-INF/view/TagOneList.jsp").forward(request, response);
	}

}