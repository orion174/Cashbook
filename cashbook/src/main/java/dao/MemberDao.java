package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DButil;
import vo.Member;

public class MemberDao {
	
	// 1) 로그인 page
	public String selectMemberByIdPw(Member member) {
		// 로그인 실패 -> null
		String memberId = null; 
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DButil
		conn = DButil.getConnection();
		// SQL 쿼리
		String sql = " SELECT * FROM member "
				+ "WHERE member_id=? AND member_pw=PASSWORD(?)";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			rs =stmt.executeQuery();
			if(rs.next()) {
				memberId = rs.getString("member_id");
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
		return memberId;
	}
	
	// 2) 회원 정보 상세보기 page
	public Member selectMemberOne(String memberId) {
		Member m = new Member();
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		// DButil
		conn = DButil.getConnection();
		String sql = "SELECT member_id memberId"
				+ "					,name"
				+ "					,gender"
				+ "					,age"
				+ "					,create_date createDate "
				+ "		 FROM member "
				+ "		 WHERE member_id=? ";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				m.setMemberId(rs.getString("MemberID"));
				m.setName(rs.getString("name"));
				m.setGender(rs.getString("gender"));
				m.setAge(rs.getInt("age"));
				m.setCreateDate(rs.getString("createDate"));
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
		return m;
	}
	
	// 3) 회원 가입 page
	public int insertMember(Member member) {
		// 회원 가입 성공 여부 리턴할 정수형 변수 선언
		int row = -1; 
		// 로그인 실패시, 처리 코드 -> memberId = null
		String memberId = null; 
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		// DButil
		conn = DButil.getConnection();
		String sql = "INSERT INTO member (member_id "
				+ "						   ,member_pw "
				+ " 					   ,name "
				+ "						   ,gender "
				+ "						   ,age "
				+ "						   ,create_date) "
				+ "	VALUES (?,PASSWORD(?),?,?,?,NOW()) ";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			stmt.setString(3, member.getName());
			stmt.setString(4, member.getGender());
			stmt.setInt(5, member.getAge());
			row = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return row;
	}
	
	// 4) 회원 삭제 page
	public int deleteMember(Member member) {
		// 회원 삭제 성공 여부 리턴할 정수형 변수 선언
		int row = -1; 
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		// DButil
		conn = DButil.getConnection();
		// 1) cashbook 테이블 데이터 삭제
		String sql1 = "DELETE FROM cashbook WHERE member_id=?"; 
		// 2) member 테이블 데이터 삭제
		String sql2 = "DELETE FROM member WHERE member_id=? AND member_pw=PASSWORD(?)";
		try {
			// auto commit 해제
			conn.setAutoCommit(false); 
			// 1) cashbook 테이블 데이터 삭제
			stmt = conn.prepareStatement(sql1);
			stmt.setString(1, member.getMemberId());
			stmt.executeUpdate();
			stmt.close();
			// 2) member 테이블 데이터 삭제
			stmt = conn.prepareStatement(sql2);
			stmt.setString(1, member.getMemberId());
			stmt.setString(2, member.getMemberPw());
			row = stmt.executeUpdate();
			
			// 삭제 성공(row값이 1) -> commit / 이외 상황 rollback
			if (row == 1) { 
				conn.commit();
			} else { 
				conn.rollback();
			}
		} catch (Exception e1) {
			try {
				conn.rollback();

			} catch(SQLException e2) {
				e2.printStackTrace();
			}
			e1.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch(SQLException e1) {
				e1.printStackTrace();
			}
		}
		return row;
	}
	
	// 5) 회원 정보 수정 page
	public int updateMemberByIdPw(Member member,String newMemberPw) {
		// 회원 정보 수정 성공 여부 리턴할 정수형 변수 선언
		int row = -1; 
		// 로그인 실패시, 처리 코드 -> memberId = null
		String memberId = null;
		// newMemberPw(새 비밀번호)가 공백 값이면, memberPw(현 비밀번호)로 값을 채움
		if(newMemberPw.equals("")) {
			newMemberPw = member.getMemberPw();
		}
		// DB 초기화
		Connection conn = null;
		PreparedStatement stmt = null;
		// DButil
		conn = DButil.getConnection();
		// SQL 쿼리
		String sql = "UPDATE member SET name = ? "
				+ "						,gender=? "
				+ "						,age=? "
				+ "						,member_pw = PASSWORD(?) "
				+ "	WHERE member_id = ? AND member_pw =PASSWORD(?)";
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, member.getName());
			stmt.setString(2, member.getGender());
			stmt.setInt(3, member.getAge());
			stmt.setString(4, newMemberPw);
			stmt.setString(5, member.getMemberId());
			stmt.setString(6, member.getMemberPw());
			row = stmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return row;
	}
}