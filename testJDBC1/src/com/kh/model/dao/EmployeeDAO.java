package com.kh.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
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
		ArrayList<Employee> list = null; // <> : 타입 추론, 앞에 제네릭 타입 지정해줬으면 생략 가능
		
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
			
//			for (Employee e : list) { // list에 잘 들어갔는지 확인
//				System.out.println(e);
//			}
			
			// ResultSet 안에 0~1개의 행만 들어가 있을 가능성이 있다면 if문으로 진행 : 행이 있느냐 없느냐
			if (rset.next()) {
				
			}
		
			
		} catch (ClassNotFoundException e) { // 드라이버가 존재하지 않으면 예외 발생
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

}
