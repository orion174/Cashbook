package dao;

import java.sql.*;
import java.util.*;

import util.DButil;
import vo.Cashbook;

public class CashbookDao {
	public List<Map<String, Object>> selectCashbookListByMonth(int y, int m) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		/*
		 SELECT 
		 	cashbook_no cashbookNo
		 	,DAY(cash_date) day
		 	,kind
		 	,cash
		 FROM cashbook
		 WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ?
		 ORDER BY DAY(cash_date) ASC
		 */
		
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		// DButil
		conn = DButil.getConnection();
		// SQL 쿼리
		String sql = "SELECT"
				+ "			cashbook_no cashbookNo"
				+ "			,DAY(cash_date) day"
				+ "			,kind"
				+ "			,cash"
				+ "		FROM cashbook"
				+ "		WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ?"
				+ "		ORDER BY DAY(cash_date) ASC";
		
		// DB 데이터 요청
		try {
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			stmt.setInt(1, y);
			stmt.setInt(2, m);
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cashbookNo", rs.getInt("cashbookNo"));
				map.put("day", rs.getInt("day"));
				map.put("kind", rs.getString("kind"));
				map.put("cash", rs.getInt("cash"));
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
		}
				
		return list;
	}
}
