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
	
	private JDBCTemplate() {} // 기본생성자 private -> 이 클래스 외부에서 객체 생성 못함
	
	public static Connection getConnection() { // = DriverManager.getConnection()
		
		if(conn == null) { // Connection객체 생성은 한 번만 하면 되기 때문(한 번 연결 이후로 다시 연결x)
			try {
				
				Properties prop = new Properties(); // 키와 값이 String타입으로 제한된 Map컬렉션
				prop.load(new FileReader("driver.properties")); // 키=값,키=값 ..받아옴
				// properties파일에 있는 데이터를 읽어와서 Properties객체에 저장하는 메소드
				
				// 바로 적는 방법, 작성한 properties파일에 있는 데이터 읽어오는 방법 2가지 중 하나 사용하면 됨
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
	} // 이 메소드 호출로 JDBC드라이버 등록 및 DB연결(1~2번 업무) 수행
	
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
	
	public static void close(ResultSet rset) { // 매개변수 타입이 달라서 오버로딩 적용됨
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
	// PreparedStatement는 Statement를 상속받고 있기 때문에 Statement.close()하면 PreparedStatement도 같이 처리 가능
	// -> 다형성 적용된 것/ 메소드 호출할때 인수로 PreparedStatement객체 값 입력하면 업캐스팅 적용(부모클래스 타입이 자식클래스객체 받아줌)

}
