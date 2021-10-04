package com.kh.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
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
		ArrayList<Employee> list = null; // <> : Ÿ�� �߷�, �տ� ���׸� Ÿ�� ������������ ���� ����
		
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
			
//			for (Employee e : list) { // list�� �� ������ Ȯ��
//				System.out.println(e);
//			}
			
			// ResultSet �ȿ� 0~1���� �ุ �� ���� ���ɼ��� �ִٸ� if������ ���� : ���� �ִ��� ������
			if (rset.next()) {
				
			}
		
			
		} catch (ClassNotFoundException e) { // ����̹��� �������� ������ ���� �߻�
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

}
