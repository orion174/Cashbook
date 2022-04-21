package dao;

import java.sql.*;
import java.util.*;

import util.DButil;

public class HashtagDao {
	// request TagController
	public List<Map<String,Object>> selectTagRankList() {
		List<Map<String,Object>> list = new ArrayList<>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DButil
		conn = DButil.getConnection();
		try {
			String sql = "SELECT t.tag, t.cnt, RANK() over(ORDER BY t.cnt DESC) rank"
					+ "				FROM"
					+ "				(SELECT tag, COUNT(*) cnt"
					+ "				FROM hashtag"
					+ "				GROUP BY tag) t";
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("tag", rs.getString("tag"));
				map.put("cnt", rs.getInt("t.cnt"));
				map.put("rank", rs.getInt("rank"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// request TagKindController
	public List<Map<String,Object>> selectKindTagRankList(String kind) {
		List<Map<String,Object>> list = new ArrayList<>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DButil
		conn = DButil.getConnection();
			try {
				String sql = "SELECT t.kind, t.tag, t.cnt, RANK() over(ORDER BY t.cnt DESC) rank"
						+ "	FROM "
						+ "	(SELECT c.kind, h.tag, COUNT(*) cnt"
						+ "			FROM hashtag h"
						+ "			INNER JOIN cashbook c"
						+ "			ON h.cashbook_no = c.cashbook_no"
						+ "			WHERE c.kind = ? "
						+ "			GROUP BY tag)t";
				
				stmt = conn.prepareStatement(sql);
				// kind -> 수입or지출
				stmt.setString(1, kind);
				rs = stmt.executeQuery();
				// 데이터 요청
				while(rs.next()) {
					Map<String, Object> map = new HashMap<>();
					map.put("kind", rs.getString("kind"));
					map.put("tag", rs.getString("tag"));
					map.put("cnt", rs.getInt("t.cnt"));
					map.put("rank", rs.getInt("rank"));
					list.add(map);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return list;
		}
	
	// request TagOneController
	public List<Map<String,Object>> selectTagOneList(String tag) {
		List<Map<String,Object>> list = new ArrayList<>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DButil
		conn = DButil.getConnection();
		try {
			// 태그별로 상세보기 -> hashtag inner join cashbook (on = cashbook_no)해서 태그별로 데이터 SELECT
			// cash_date 내림차순
			String sql = "SELECT h.tag tag, c.cash_date cashDate, c.kind kind, c.cash cash, c.memo memo"
					+ "				FROM hashtag h INNER JOIN cashbook c"
					+ "				ON h.cashbook_no = c.cashbook_no"
					+ "				WHERE h.tag = ?"
					+ "ORDER BY c.cash_date DESC";
			
			stmt = conn.prepareStatement(sql);
			// tag -> n개
			stmt.setString(1, tag);
			rs = stmt.executeQuery();
			// 데이터 변환
			while(rs.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("tag", rs.getString("tag"));
				map.put("cashDate", rs.getString("cashDate"));
				map.put("kind", rs.getString("kind"));
				map.put("cash", rs.getInt("cash"));
				map.put("memo", rs.getString("memo"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// request TagDateController
	public List<Map<String, Object>> selectDateTagRankList(String beginDate, String endDate) {
		List<Map<String, Object>> list = new ArrayList<>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DButil
		conn = DButil.getConnection();
		try {
			// 범위내 날짜별로 데이터 출력
			String sql = "SELECT"
					+ "	 		c.cash_date cashDate"
					+ "			,c.cashbook_no cashbookNo"
					+ "			,c.kind"
					+ "			,h.tag"
					+ "			,c.cash"
					+ "			,c.memo"
					+ "	  FROM hashtag h INNER JOIN cashbook c"
					+ "	  ON c.cashbook_no = h.cashbook_no"
					+ "	  WHERE c.cash_date BETWEEN ? AND ?"
                    + "ORDER BY c.cash_date ASC";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, beginDate);
			stmt.setString(2, endDate);
			rs = stmt.executeQuery();
			// 데이터변환
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cashDate", rs.getString("cashDate"));
				map.put("cashbookNo", rs.getInt("cashbookNo"));
				map.put("kind", rs.getString("kind"));
				map.put("tag", rs.getString("tag"));
				map.put("cash", rs.getString("cash"));
				map.put("memo", rs.getString("memo"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}

}
