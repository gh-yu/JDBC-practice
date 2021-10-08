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
		 *  ���� ������Ʈ����  DAO�� ���� ����
		 *  	1) JDBC����̹� ���
		 *  	2) DB���� Connection��ü ����
		 *  	3) SQL ����
		 *  	4) Ʈ����� ó�� (������ DML���ϋ�)
		 *  	5) �ڿ� �ݳ�
		 * 	
		 * 	--> ��� DAO�� SQL���� DB�� �����ϰ� ��� ���� ��ȯ �޴� �ϸ� ����(3�� ����)
		 * 	--> 1, 2, 4, 5 ������ �и�
		 * 		--> �� �������� �������� �����̱� ������ �� ���� ��� ó�� : JDBCTemplate	
		 * 
		 */
		
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			String query = prop.getProperty("insertMember"); // Properties�� Ű�� ���� �ش� Ű�� ���� ������ ��������
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPwd());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender()+""); // setChar()�� ���� -> +""�ؼ� String������ ������ֱ�
			pstmt.setString(5, member.getEmail());
			pstmt.setString(6, member.getPhone());
			pstmt.setString(7, member.getAddress());
			pstmt.setInt(8, member.getAge());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/* JDBCTemplate. */close(pstmt); // ��ܿ� import static com.kh.common.JDBCTemplate.close;�ۼ� -> JDBCTemplate. ���� ����
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
		Statement stmt = null; // Statement�������ε� ����
		ResultSet rset = null;
		ArrayList<Member> list = null;
		
//		String query = prop.getProperty("selectMemberId");
		String query = prop.getProperty("selectMemberId") + "'%" + id + "%'";
		// selectMemberId=SELECT * FROM MEMBER WHERE MEMBER_ID LIKE
		// query�� like�� ���ٴ� �̾߱�� �κи� ��ġ�ص� �������ڴٴ� �̾߱�.
		// �ٽ� ���ϸ� ���� ���ϴ� �����Ϳ� %�� _�� ���� ���ϵ� ī�尡 �־�� ��
		// ���� 123��° ���� id��� �ִ°� �ƴ϶�  '% �� %' ���̿� ���� �ؾ���
		
		try {
//			pstmt = conn.prepareStatement(query);
//			pstmt.setString(1, "%" + id + "%");
//			rset = pstmt.executeQuery();
			// PreparedStatement�� ���� ��ġȦ���� ����ϸ� ��ġȦ�� �ڸ��� �����Ͱ� ���� �ڵ����� ''���� �����ֱ⶧���� ���� ǥ������ �ʾƵ� �ȴ�
			
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
			pstmt.setString(1, gen+""); // gen�� char���̾ ""�ٿ��� String������ �ٲ��ֱ�
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
		
		String query = prop.getProperty("checkMember"); // �ش� ȸ��ID�� ��ġ�ϴ� ȸ�� �� ������ count�Լ� ��
		// checkMember=SELECT COUNT(*) FROM MEMBER WHERE MEMBER_ID = ?
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) { // SELECT COUNT(*) �����ϸ� ��� ��(��)�� 1��
//				result = rset.getInt("conut(*)"); // count�Լ� ��� ������ int��
				result = rset.getInt(1); // �÷��� ����
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
		
		String query = prop.getProperty("updateMember" + sel); // key�� updateMember1, ... , updateMembe4���� ������ ���� �ۼ�
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
