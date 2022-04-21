package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DButil;
import vo.Cashbook;

public class CashbookDao {
	
	// request CashBookListByMonthController
	public List<Map<String, Object>> selectCashbookListByMonth(int y, int m) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DButil
		conn = DButil.getConnection();
		// SQL 쿼리
		String sql = "SELECT"
				+ "		 	cashbook_no cashbookNo"
				+ "		 	,DAY(cash_date) day"
				+ "		 	,kind"
				+ "		 	,cash"
				+ "			,LEFT(memo, 5) memo"
				+ "		 FROM cashbook"
				+ "		 WHERE YEAR(cash_date) = ? AND MONTH(cash_date) = ?"
				+ "		 ORDER BY DAY(cash_date) ASC, kind ASC";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, y);
			stmt.setInt(2, m);
			rs = stmt.executeQuery();
			// 데이터 요청
			while(rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cashbookNo", rs.getInt("cashbookNo"));
				map.put("day", rs.getInt("day"));
				map.put("kind", rs.getString("kind"));
				map.put("cash", rs.getInt("cash"));
				map.put("memo", rs.getString("memo"));
				list.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// DB 종료
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	// request InsertCashBookController 
	public void insertCashbook(Cashbook cashbook, List<String> hashtag) {
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DButil
		conn = DButil.getConnection();
		try {
			conn.setAutoCommit(false); // 자동커밋을 해제
			// SQL 쿼리
			String sql = "INSERT INTO cashbook(cash_date,kind,cash,memo,update_date,create_date)"
					+ " VALUES(?,?,?,?,NOW(),NOW())";
			
			// insert + select 방금생성된 행의 키값 ex: select 방금입력한 cashbook_no from cashbook;
			stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS); 
			stmt.setString(1, cashbook.getCashDate());
			stmt.setString(2, cashbook.getKind());
			stmt.setInt(3, cashbook.getCash());
			stmt.setString(4, cashbook.getMemo());
			stmt.executeUpdate(); // insert
			rs = stmt.getGeneratedKeys(); // select 방금입력한 cashbook_no from cashbook
			
			//
			int cashbookNo = 0;
			if(rs.next()) {
				cashbookNo = rs.getInt(1);
			}
			
			// hashtag를 저장하는 코드
			PreparedStatement stmt2 = null;
			for(String h : hashtag) {
				String sql2 = "INSERT INTO hashtag(cashbook_no, tag, create_date) VALUES(?, ?, NOW())";
				stmt2 = conn.prepareStatement(sql2);
				stmt2.setInt(1, cashbookNo);
				stmt2.setString(2, h);
				stmt2.executeUpdate();
			}
			conn.commit();
		} catch(Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// request CashBookOneController 
	public Cashbook selectCashBookOne(int cashbookNo) {
		// vo
		Cashbook c = new Cashbook(); 
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DButil
		conn = DButil.getConnection();
		//쿼리 작성
		String sql = "SELECT "
				+ "			cashbook_no cashbookNo"
				+ "			,cash_date cashDate"
				+ "			,kind"
				+ "			,cash"
				+ "			,memo"
				+ "			,create_date createDate"
				+ "			,update_date updateDate "
				+ "		FROM cashbook"
				+ "		WHERE cashbook_no = ? ";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			rs = stmt.executeQuery();
			if(rs.next()) {
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setCashDate(rs.getString("cashDate"));
				c.setKind(rs.getString("kind"));
				c.setCash(rs.getInt("cash"));
				c.setMemo(rs.getString("memo"));
				c.setCreateDate(rs.getString("createDate"));
				c.setUpdateDate(rs.getString("updateDate"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}		
		return c;
	}
	
    // request DeleteCashBookController
	public void deleteCashBook(int cashBookNo) {
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		// DButil
		conn = DButil.getConnection();
		
		/* 쿼리 작성
			hashtag 테이블의 cashbook_no는 cashbook 테이블에 외래키를 가지고 있으므로, hashtag 테이블 -> cashbook 테이블 순으로 지워야 한다.
		*/
		
		// 1) hashtag 테이블에서 삭제 쿼리
		String sql1 = "DELETE FROM hashtag WHERE cashbook_no = ?";
		// 2) cashbook 테이블에서 삭제 쿼리
		String sql2 = "DELETE FROM cashbook WHERE cashbook_no = ?";
		
		try {
			conn.setAutoCommit(false); // auto commit close
			// hashtag 테이블 삭제 실행
			stmt = conn.prepareStatement(sql1);
			stmt.setInt(1, cashBookNo);
			stmt.executeUpdate();
			stmt.close();
			// cashbook 테이블 삭제 실행
			stmt = conn.prepareStatement(sql2);
			stmt.setInt(1, cashBookNo);
			stmt.executeUpdate();
			// commit
			conn.commit();
			
		} catch (Exception e) {
			try {
				// 실패시 rollback
				conn.rollback();
			} catch(SQLException e1) {
					e1.printStackTrace();
				}
					e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// request UpdateCashBookComtroller
	public void updateCashBook(Cashbook cashbook, List<String>hashtag) {
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DButil
		conn = DButil.getConnection();
		try {
			// auto commit close
			conn.setAutoCommit(false); 
			// SQL 쿼리
			String updateCashBookSql = "UPDATE cashbook SET"
					+ "cash_date=?"
					+ ",kind=?"
					+ ",cash=?"
					+ ",memo=?"
					+ ",update_date=NOW()"
					+ "		WHERE cashbook_no=?";
			
			stmt = conn.prepareStatement(updateCashBookSql);
			stmt.setString(1, cashbook.getCashDate());
			stmt.setString(2, cashbook.getKind());
			stmt.setInt(3, cashbook.getCash());
			stmt.setString(4, cashbook.getMemo());
			stmt.setInt(5, cashbook.getCashbookNo());
			stmt.executeUpdate();
			
			// hashtag 테이블 태그를 수정하는 코드
			// 1) hashtag table의 해당 태그 모두 삭제
			PreparedStatement stmt2 = null;
			String deleteHashtagSql = 
				"DELETE FROM hashtag WHERE cashbook_no=? ";
			stmt2 = conn.prepareStatement(deleteHashtagSql);
			stmt2.setInt(1, cashbook.getCashbookNo());
			stmt2.executeUpdate(); 
			
			// 2) hashtag table에 새로 태그 저장
			PreparedStatement stmt3 = null;
			// hashtag 수 만큼 반복해서 insert
			for(String h : hashtag) {
				String insertHashtagSql = 
					"INSERT INTO hashtag(cashBook_no,tag,create_date) VALUES (?,?,NOW())";
				stmt3= conn.prepareStatement(insertHashtagSql);
				stmt3.setInt(1, cashbook.getCashbookNo());
				stmt3.setString(2, h);
				stmt3.executeUpdate();
			}
			// commit
			conn.commit(); 
		} catch (Exception e) {
			try {
				// 실패시 rollback
				conn.rollback(); 
			} catch(SQLException e1) {
			e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

