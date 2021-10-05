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

public class EmployeeDAO { // DB�� ���������� ����Ǵ� ����

	public ArrayList<Employee> selectAll() {
		
		ResultSet rset = null; // // try�� finally�� ������ �޶� ���� ������ ���� ����
		Statement stmt = null;
		Connection conn = null;
		ArrayList<Employee> list = null;

		try {
			// 1. DB�� ���� Driver ��� : Class.forName("JDBC ����̹�");
			// DBMS������ ���� ����̹��� �޶���
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. �����ͺ��̽� ���� �۾� 
			// DriverManager : Connection�� ������ֱ� ���� Ŭ����
			// 		DriverManager.getConnection(String url, String user, String password):Connection
			//     		Ư�� DB�� ����� Connection ��ȯ�ϴ� �޼ҵ�   
			//          	url : ��� ��ǻ�� DB�� ������ ������
			//			 	user : ������ ���� �̸�
			//           	password : ������ ���� ��й�ȣ
			// Connection : DB�� ����� ��
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:xe",
														  "SCOTT", "SCOTT");
			// jdbc:oracle:thin       jdbc����̹��� thinŸ����
			// @127.0.0.1                            ������ �����ͺ��̽��� �� ��ǻ�Ϳ� ����(�� ��ǻ�Ϳ� ���� ip�ּ�)
			// 1521:xe                ��Ʈ��ȣ�� xeŸ��
			
			// 3. ���� �ۼ� �� ���� ����
			// 4. ���� ��� �� �ޱ�
			String query = "SELECT * FROM EMP"; // DB�� ������ �ڵ����� �����ݷ� �ٿ����� ������ ������ �������� ; ����	
			stmt = conn.createStatement(); // Statement ��ü ����
			rset = stmt.executeQuery(query); // SELECT�� ���������� ����� Result set ��ȯ�� -> ResultSet��ü�� ����ֱ�
			
			// 5. �޾ƿ� ��� �� ��ȯ
			// ResultSet �ȿ� 0~n���� ���� �� ���� ���ɼ��� �ִٸ� while������ ���� : ���� ���� �ִ��� ������
			list = new ArrayList<>(); // <> : Ÿ�� �߷�, �տ� ���׸� Ÿ�� ������������ ���� ����
			while (rset.next()) { // next():boolean ���� ���� �����Ѵٸ� true ��ȯ, ������ false��ȯ-while�� ����
				int empNo = rset.getInt("EMPNO"); // getInt()�� �μ��� resultSet�� �÷����� �����;���(��Ī ������ �μ��� ��Ī �ۼ�)
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
			
//			for (Employee e : list) { // list�� �� ������ Ȯ�ο�
//				System.out.println(e);
//			}
			
			// ResultSet �ȿ� 0~1���� �ุ �� ���� ���ɼ��� �ִٸ� if������ ���� : ���� �ִ��� ������
//			if (rset.next()) {

//			}
		
			
		} catch (ClassNotFoundException e) { // ����̹��� �������� ������ ���� �߻�(�߸� �Է��߰ų�, Driver�� ���� �ڷ� ������ ���� ��� ��� ���� �����)
			e.printStackTrace();
		} catch (SQLException e) { // �����Ϸ��� �ϴ� DBMS������ ���� ���н� ���� �߻�
			e.printStackTrace();
		} finally { 
			// 6. �ڿ� �ݳ�
			// �� ������ �ݴ� ������ �ݾ���
			// �ڿ� �ݳ��� �ʼ��� �̷������ �ϱ� ������ ���� �߻��ÿ��� �����ϴ� finally������ �ۼ�
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

		// try���� �ȿ� ������ �������� try���� �ȿ����� ������ ��ħ, try�� �ۿ� ���� ���� �� try�� �ȿ��� ���� �Ҵ�
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Employee emp = null;
//		Statement stmt = null;
		try {
			// 1. DB�� ���� Driver ��� : Class.forName()
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			// 2. DB�� ���� : DriverManager                                 // @localhost : �� ��ǻ��  IP�ּ� (= @127.0.0.1)
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "SCOTT");
			                                                            
			// 3. query�ۼ� �� ����
			String query = "SELECT * FROM EMP WHERE EMPNO = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, empNo);
			rset = pstmt.executeQuery();
			
			// query Statement�� ���
//			String query = "SELECT * FROM EMP WHERE EMPNO = " + empNo;
//			stmt = conn.createStatement();
//			rset = stmt.executeQuery(query);
			
			// 4. ��� �� ��ȯ : ResultSet �ȿ��� �ִ� 1���� ���� �� ���� ���̹Ƿ� if ���
			if (rset.next()) {
//				int empNo = rset.getInt("EMPNO"); // �Ű������� �޾ƿ� empNo�� ��ü ���
				String empName = rset.getString("ename"); //�ҹ��ڷ� �����൵ ��
				String job = rset.getString("job");
				int mgr = rset.getInt("mgr");
				Date hireDate = rset.getDate("hiredate");
				int sal = rset.getInt("sal");
				int comm = rset.getInt("comm");
				int deptNo = rset.getInt("deptno");
				
				emp = new Employee(empNo, empName, job, mgr, hireDate, sal, comm, deptNo);
			}
			
		} catch (ClassNotFoundException e) { // �߸� �Է��߰ų�, Driver�� ���� �ڷ� ������ ���� ��� ��� ���� �����
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// 5. �ڿ� �ݳ� �� ������ ��ȯ
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
			conn.setAutoCommit(false); // �ڵ�Ŀ�� off, Ŀ���� ��ǻ�Ϳ��� �������� �ʰ� Ʈ����� ���� �����ڿ��� �ѱ�
			
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
			result = pstmt.executeUpdate(); // PreparedStatement��ü ���鶧 query�־ ������� ������ �μ��� query���� �ʰ� ����

			if (result > 0) {
				conn.commit(); // 1�� �̻� �� ���ԵǾ����� ������� Ŀ��
			} else {
				conn.rollback(); // 0�� ���ϸ� ������� ���
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
		
		return result; // ���� ����/���� Ȯ�� ����
	}

	public int updateEmployee(Employee e) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver"); // OracleDriverŬ������ Ǯ����(��Ű������) -> �ش� �̸��� Ŭ���� ��ü ����
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "SCOTT");
			conn.setAutoCommit(false); // ������ DML���϶� �ڵ�Ŀ�� ��� ����
			
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
			
			// Driver��� �� DriverManagerŬ������ �޼ҵ� ��� ����
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
