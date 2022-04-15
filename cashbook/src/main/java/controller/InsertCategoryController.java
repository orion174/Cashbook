package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDao;
import vo.Category;

// MVC 
// 2) 입력Action Controller
@WebServlet("/category/InsertCategoryController")
public class InsertCategoryController extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String categoryTitle = request.getParameter("categoryTitle");
		Category category = new Category();
		category.setCategoryTitle(categoryTitle);
		System.out.println(category);
		
		CategoryDao categoryDao = new CategoryDao();
		categoryDao.insertCategory(category); // 끝 -> 뷰가 없다
		
		// redirect : 클라이언트에게 새로운 요청(새로운 컨터롤러)을 명령 
		response.sendRedirect(request.getContextPath()+"/CategoryListController");
	}
}
