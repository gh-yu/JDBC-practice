package com.kh.model.service;

import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.rollback;
import static com.kh.common.JDBCTemplate.getConnection;
import static com.kh.common.JDBCTemplate.close;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.model.dao.MemberDAO;
import com.kh.model.vo.Member;

public class MemberService {
	
	private MemberDAO mDAO = new MemberDAO(); // 실질적으로 DB와 연결된 곳은 DAO
	
	public int insertMember(Member mem) {
		Connection conn = /* JDBCTemplate. */getConnection(); // static메소드이기 때문에 객체 생성하지 않고도 전역에서 사용 가능
		
		int result = mDAO.insertMember(conn, mem);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		return result;
	}

	public ArrayList<Member> seletAll() {
		Connection conn = getConnection();
		
		ArrayList<Member> list = mDAO.selectAll(conn);
		
		return list;
		
	}

	public ArrayList<Member> selectMemberId(String id) {
		Connection conn = getConnection();
		
		ArrayList<Member> list = mDAO.selectMemberId(conn, id);
		
		return list;
		
	}

	public ArrayList<Member> selectGender(char gen) {
		Connection conn = getConnection();
		
		ArrayList<Member> list = mDAO.selectGender(conn, gen);
		
		return list;
		
	}

	public int checkMember(String memberId) {
		Connection conn = getConnection();
		
		int check = mDAO.checkMember(conn, memberId);
		
		return check;
	}

	public int updateMember(String memberId, String input, int sel) {
		Connection conn = getConnection();
		
		int result = mDAO.updateMember(conn, memberId, input, sel);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn); 
		}
		
		return result;
	}

	public int deleteMember(String memberId) {
		Connection conn = getConnection();
		
		int result = mDAO.deleteMember(conn, memberId);
		if (result > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		
		return result;
	}

	public void exitProgram() {
		Connection conn = getConnection();
		close(conn);
	}
	
}
