package dao;

import java.sql.*;
import java.util.*;


import util.DButil;
import vo.Category;

// MVC 
// 1) Model
public class CategoryDao {
	// category list 출력 
	public List<Category> selectCategoryList(){
		List<Category> list = new ArrayList<>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DButil
		conn = DButil.getConnection();
		// SQL 쿼리
		String sql = 
			"SELECT category_no categoryNo, category_title categoryTitle FROM category ORDER BY category_no";
		// DB 데이터 요청
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while(rs.next()) {
				Category c = new Category();
				c.setCategoryNo(rs.getInt("categoryNo"));
				c.setCategoryTitle(rs.getString("categoryTitle"));
				list.add(c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// category 데이터 insert 
	public void insertCategory(Category category) {
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		// DButil
		conn = DButil.getConnection();
		// SQL 쿼리
		String sql = "INSERT INTO category(category_title) VALUES(?)";
		// DB 데이터 요청
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, category.getCategoryTitle());
			int row = stmt.executeUpdate();
			if(row==1) {
				System.out.println("입력 성공");
			} else {
				System.out.println("입력 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}