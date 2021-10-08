package com.kh.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.model.vo.Member;

public class MemberDAO {

	private Properties prop = null;
	
	public MemberDAO() {
		prop = new Properties();
		try {
			prop.load(new FileReader("query.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int insertMember(Connection conn, Member member) {
		/*
		 *  이전 프로젝트에서  DAO가 맡은 업무
		 *  	1) JDBC드라이버 등록
		 *  	2) DB연결 Connection객체 생성
		 *  	3) SQL 실행
		 *  	4) 트랜잭션 처리 (쿼리가 DML문일떄)
		 *  	5) 자원 반납
		 * 	
		 * 	--> 사실 DAO는 SQL문을 DB로 전달하고 결과 값을 반환 받는 일만 진행(3번 업무)
		 * 	--> 1, 2, 4, 5 역할을 분리
		 * 		--> 위 업무들은 공통적인 업무이기 때문에 한 번에 묶어서 처리 : JDBCTemplate	
		 * 
		 */
		
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			String query = prop.getProperty("insertMember"); // Properties의 키를 통해 해당 키의 값인 쿼리문 가져오기
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender()+""); // setChar()는 없음 -> +""해서 String값으로 만들어주기
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getPhone());
			pstmt.setString(7, member.getAddress());
			pstmt.setInt(8, member.getAge());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/* JDBCTemplate. */close(pstmt); // 상단에 import static com.kh.common.JDBCTemplate.close;작성 -> JDBCTemplate. 생략 가능
		}
		
		return result;
	}

	public ArrayList<Member> selectAll(Connection conn) {

		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Member> list = null;
		
		String query = prop.getProperty("selectAll");
	
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			list = new ArrayList<>();
			while (rset.next()) {
				String memberId = rset.getString("member_id");
				String memberPwd = rset.getString("member_pwd");
				String memberName = rset.getString("member_name");
				char gender = rset.getString("gender").charAt(0);
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				int age = rset.getInt("age");
				Date enrollDate = rset.getDate("enroll_date");
				
				Member mem = new Member(memberId, memberPwd, memberName, gender, email, phone, address, age, enrollDate);
				list.add(mem);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rset);
		}
		
		return list;
	}

	public ArrayList<Member> selectMemberId(Connection conn, String id) {
		
//		PreparedStatement pstmt = null; 
		Statement stmt = null; // Statement버전으로도 가능
		ResultSet rset = null;
		ArrayList<Member> list = null;
		
//		String query = prop.getProperty("selectMemberId");
		String query = prop.getProperty("selectMemberId") + "'%" + id + "%'";
		// selectMemberId=SELECT * FROM MEMBER WHERE MEMBER_ID LIKE
		// query에 like가 들어갔다는 이야기는 부분만 일치해도 가져오겠다는 이야기.
		// 다시 말하면 내가 원하는 데이터에 %나 _와 같은 와일드 카드가 있어야 함
		// 따라서 123번째 줄을 id라고만 넣는게 아니라  '% 와 %' 사이에 들어가게 해야함
		
		try {
//			pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, "%" + id + "%");
//			rset = pstmt.executeQuery();
			// PreparedStatement를 쓸때 위치홀더를 사용하면 위치홀더 자리에 데이터가 들어갈때 자동으로 ''으로 감싸주기때문에 따로 표기하지 않아도 된다
			
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			list = new ArrayList<>();
			while (rset.next()) {
				Member m = new Member(rset.getString("member_id"), 
									  rset.getString("member_pwd"),
									  rset.getString("member_name"),
									  rset.getString("gender").charAt(0),
									  rset.getString("email"),
									  rset.getString("phone"),
									  rset.getString("address"),
									  rset.getInt("age"),
									  rset.getDate("enroll_date"));
				list.add(m);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}
		
		return list;
	}

	public ArrayList<Member> selectGender(Connection conn, char gen) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Member> list = null;
		
		String query = prop.getProperty("selectGender");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, gen+""); // gen이 char형이어서 ""붙여서 String형으로 바꿔주기
			rset = pstmt.executeQuery();
			
			list = new ArrayList<>();
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String memberPwd = rset.getString("member_pwd");
				String memberName = rset.getString("member_name");
				char gender = rset.getString("gender").charAt(0);
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				int age = rset.getInt("age");
				Date enrollDate = rset.getDate("enroll_date");
				
				Member mem = new Member(memberId, memberPwd, memberName, gender, email, phone, address, age, enrollDate);
				list.add(mem);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int checkMember(Connection conn, String memberId) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		String query = prop.getProperty("checkMember"); // 해당 회원ID와 일치하는 회원 몇 명인지 count함수 씀
		// checkMember=SELECT COUNT(*) FROM MEMBER WHERE MEMBER_ID = ?
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) { // SELECT COUNT(*) 실행하면 결과 값(행)은 1개
//				result = rset.getInt("conut(*)"); // count함수 결과 데이터 int값
				result = rset.getInt(1); // 컬럼의 순번
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return result;
	}

	public int updateMember(Connection conn, String memberId, String input, int sel) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("updateMember" + sel); // key값 updateMember1, ... , updateMembe4까지 쿼리문 따로 작성
		// updateMember1=UPDATE MEMBER SET MEMBER_PWD = ? WHERE MEMBER_ID = ?
		// updateMember2=UPDATE MEMBER SET EMAIL = ? WHERE MEMBER_ID = ?
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, input);
			pstmt.setString(2, memberId);
			
			result = pstmt.executeUpdate();
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String memberId) {
		
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = prop.getProperty("deleteMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

}
