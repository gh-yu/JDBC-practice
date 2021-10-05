package com.kh.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.kh.model.vo.Employee;

public class EmployeeDAO { // DB와 직접적으로 연결되는 역할

	public ArrayList<Employee> selectAll() {
		
		ResultSet rset = null; // // try와 finally는 영역이 달라서 변수 위에서 먼저 선언
		Statement stmt = null;
		Connection conn = null;
		ArrayList<Employee> list = null;

		try {
			// 1. DB에 대한 Driver 등록 : Class.forName("JDBC 드라이버");
			// DBMS종류에 따라 드라이버가 달라짐
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. 데이터베이스 연결 작업 
			// DriverManager : Connection을 만들어주기 위한 클래스
			// 		DriverManager.getConnection(String url, String user, String password):Connection
			//     		특정 DB에 연결된 Connection 반환하는 메소드   
			//          	url : 어느 컴퓨터 DB에 연결할 것인지
			//			 	user : 연결할 계정 이름
			//           	password : 연결할 계정 비밀번호
			// Connection : DB와 연결된 길
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe",
														  "SCOTT", "SCOTT");
			// jdbc:oracle:thin       jdbc드라이버가 thin타입임
			// @127.0.0.1                            접근할 데이터베이스가 내 컴퓨터에 있음(내 컴퓨터에 대한 ip주소)
			// 1521:xe                포트번호와 xe타입
			
			// 3. 쿼리 작성 및 쿼리 전송
			// 4. 쿼리 결과 값 받기
			String query = "SELECT * FROM EMP"; // DB에 보낼때 자동으로 세미콜론 붙여지기 때문에 쿼리문 마지막에 ; 생략	
			stmt = conn.createStatement(); // Statement 객체 생성
			rset = stmt.executeQuery(query); // SELECT문 쿼리를보낸 결과로 Result set 반환됨 -> ResultSet객체에 담아주기
			
			// 5. 받아온 결과 값 변환
			// ResultSet 안에 0~n개의 행이 들어가 있을 가능성이 있다면 while문으로 진행 : 다음 값이 있느냐 없느냐
			list = new ArrayList<>(); // <> : 타입 추론, 앞에 제네릭 타입 지정해줬으면 생략 가능
			while (rset.next()) { // next():boolean 다음 행이 존재한다면 true 반환, 없으면 false반환-while문 종료
				int empNo = rset.getInt("EMPNO"); // getInt()의 인수는 resultSet의 컬럼명을 가져와야함(별칭 지정시 인수에 별칭 작성)
				String empName = rset.getString("ENAME");
				String job = rset.getString("job");
				int mgr = rset.getInt("MGR");
				Date hireDate = rset.getDate("HIREDATE");
				int sal = rset.getInt("SAL");
				int comm = rset.getInt("COMM");
				int deptNo = rset.getInt("DEPTNO");
				Employee emp = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptNo);
				list.add(emp);
				
			}
			
//			for (Employee e : list) { // list에 잘 들어갔는지 확인용
//				System.out.println(e);
//			}
			
			// ResultSet 안에 0~1개의 행만 들어가 있을 가능성이 있다면 if문으로 진행 : 행이 있느냐 없느냐
//			if (rset.next()) {

//			}
		
			
		} catch (ClassNotFoundException e) { // 드라이버가 존재하지 않으면 예외 발생(잘못 입력했거나, Driver에 관한 자료 파일이 없는 경우 대비 예외 잡아줌)
			e.printStackTrace();
		} catch (SQLException e) { // 연결하려고 하는 DBMS계정에 연결 실패시 예외 발생
			e.printStackTrace();
		} finally { 
			// 6. 자원 반납
			// 연 순서의 반대 순서로 닫아줌
			// 자원 반납은 필수로 이루어져야 하기 때문에 예외 발생시에도 실행하는 finally구문에 작성
			try {
				rset.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return list;
	}

	public Employee selectEmployee(int empNo) {

		// try구문 안에 선언한 변수들은 try구문 안에서만 영향을 미침, try문 밖에 변수 선언 후 try문 안에서 변수 할당
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Employee emp = null;
//		Statement stmt = null;
		try {
			// 1. DB에 대한 Driver 등록 : Class.forName()
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. DB와 연결 : DriverManager                                 // @localhost : 내 컴퓨터  IP주소 (= @127.0.0.1)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "SCOTT");
			                                                            
			// 3. query작성 및 전송
			String query = "SELECT * FROM EMP WHERE EMPNO = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, empNo);
			rset = pstmt.executeQuery();
			
			// query Statement에 담기
//			String query = "SELECT * FROM EMP WHERE EMPNO = " + empNo;
//			stmt = conn.createStatement();
//			rset = stmt.executeQuery(query);
			
			// 4. 결과 값 변환 : ResultSet 안에는 최대 1개의 행이 들어가 있을 것이므로 if 사용
			if (rset.next()) {
//				int empNo = rset.getInt("EMPNO"); // 매개변수로 받아온 empNo로 대체 사용
				String empName = rset.getString("ename"); //소문자로 적어줘도 됨
				String job = rset.getString("job");
				int mgr = rset.getInt("mgr");
				Date hireDate = rset.getDate("hiredate");
				int sal = rset.getInt("sal");
				int comm = rset.getInt("comm");
				int deptNo = rset.getInt("deptno");
				
				emp = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptNo);
			}
			
		} catch (ClassNotFoundException e) { // 잘못 입력했거나, Driver에 관한 자료 파일이 없는 경우 대비 예외 잡아줌
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 5. 자원 반납 및 데이터 반환
				rset.close();
				pstmt.close();
//				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return emp;
		
	}

	public int insertEmployee(Employee emp) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "SCOTT");
			conn.setAutoCommit(false); // 자동커밋 off, 커밋을 컴퓨터에게 위임하지 않고 트랜잭션 권한 개발자에게 넘김
			
//			String query = 	"INSERT INTO EMP VALUES(" + emp.getEmpNo() + ", " 
//											 		  + emp.getEmpName() + ", "
//											 		  + emp.getJob() + ", "
//											 		  + emp.getMgr() + ", "
//											 		  + emp.getSal() + ", "
//											 		  + emp.getComm() + ", "
//											 		  + emp.getDeptNo() + ")";
			String query = "INSERT INTO EMP VALUES(?, ?, ?, ?, SYSDATE, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, emp.getEmpNo());
			pstmt.setString(2, emp.getEmpName());
			pstmt.setString(3, emp.getJob());
			pstmt.setInt(4, emp.getMgr());
			pstmt.setInt(5, emp.getSal());
			pstmt.setInt(6, emp.getComm());
			pstmt.setInt(7, emp.getDeptNo());
			
			// SELECT - executeQuery() - ResultSet
			// DML(INSERT, UPDATE, DELETE) - executeUpdate() - int
			result = pstmt.executeUpdate(); // PreparedStatement객체 만들때 query넣어서 만들었기 때문에 인수로 query넣지 않고 실행

			if (result > 0) {
				conn.commit(); // 1개 이상 행 삽입되었으면 변경사항 커밋
			} else {
				conn.rollback(); // 0개 이하면 변경사항 취소
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result; // 삽입 성공/실패 확인 위해
	}

	public int updateEmployee(Employee e) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // OracleDriver클래스의 풀네임(패키지까지) -> 해당 이름의 클래스 객체 생성
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "SCOTT");
			conn.setAutoCommit(false); // 쿼리가 DML문일때 자동커밋 모드 해제
			
			String query = "UPDATE EMP SET JOB = ?, SAL = ?, COMM = ? WHERE EMPNO = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, e.getJob());
			pstmt.setInt(2, e.getSal());
			pstmt.setInt(3, e.getComm());
			pstmt.setInt(4, e.getEmpNo());
			
			result = pstmt.executeUpdate();
			
			if (result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}
		
		return result;
	}

	public int deleteEmployee(int empNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// Driver등록 후 DriverManager클래스의 메소드 사용 가능
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "SCOTT");
			conn.setAutoCommit(false);
			
			String query = "DELETE FROM EMP WHERE EMPNO = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, empNo);
			
			result = pstmt.executeUpdate();
			if (result > 0) {
				conn.commit();
			} else {
				conn.rollback();
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		
		return result;
	}

}
