package com.kh.common;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	private static Connection conn = null;
	
	private JDBCTemplate() {} // �⺻������ private -> �� Ŭ���� �ܺο��� ��ü ���� ����
	
	public static Connection getConnection() { // = DriverManager.getConnection()
		
		if(conn == null) { // Connection��ü ������ �� ���� �ϸ� �Ǳ� ����(�� �� ���� ���ķ� �ٽ� ����x)
			try {
				
				Properties prop = new Properties(); // Ű�� ���� StringŸ������ ���ѵ� Map�÷���
				prop.load(new FileReader("driver.properties")); // Ű=��,Ű=�� ..�޾ƿ�
				// properties���Ͽ� �ִ� �����͸� �о�ͼ� Properties��ü�� �����ϴ� �޼ҵ�
				
				// �ٷ� ���� ���, �ۼ��� properties���Ͽ� �ִ� ������ �о���� ��� 2���� �� �ϳ� ����ϸ� ��
				Class.forName(prop.getProperty("driver"));
				conn = DriverManager.getConnection(prop.getProperty("url"), 
												   prop.getProperty("user"), 
												   prop.getProperty("password"));
				conn.setAutoCommit(false);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return conn;
	} // �� �޼ҵ� ȣ��� JDBC����̹� ��� �� DB����(1~2�� ����) ����
	
	public static void commit(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rset) { // �Ű����� Ÿ���� �޶� �����ε� �����
		try {
			if (rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if (stmt != null && !stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} 
	// PreparedStatement�� Statement�� ��ӹް� �ֱ� ������ Statement.close()�ϸ� PreparedStatement�� ���� ó�� ����
	// -> ������ ����� ��/ �޼ҵ� ȣ���Ҷ� �μ��� PreparedStatement��ü �� �Է��ϸ� ��ĳ���� ����(�θ�Ŭ���� Ÿ���� �ڽ�Ŭ������ü �޾���)

}
