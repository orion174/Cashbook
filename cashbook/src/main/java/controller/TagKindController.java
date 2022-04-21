package controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.HashtagDao;

@WebServlet("/TagKindController")
public class TagKindController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// 한글깨짐 방지 인코딩
			request.setCharacterEncoding("utf-8");
			// get request -> kind
			String kind = request.getParameter("kind");
			// 디버깅
			System.out.println(kind + " <-- kind");
			
			// dao 
			HashtagDao hashtagDao = new HashtagDao();
			// dao.selectKindTagList
			List<Map<String,Object>> list = hashtagDao.selectKindTagRankList(kind);
			
			// request -> list, kind
			request.setAttribute("list", list);
			request.setAttribute("kind", kind);
			request.getRequestDispatcher("/WEB-INF/view/TagKindList.jsp").forward(request, response);
	}

}