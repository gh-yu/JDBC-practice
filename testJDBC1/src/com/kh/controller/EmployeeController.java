package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.EmployeeDAO;
import com.kh.model.vo.Employee;
//import com.kh.view.Menu;

public class EmployeeController { // Controller의 역할 : View와  Model 연결, 넘겨받은 데이터 가공
//	private Menu menu = new Menu();
	private EmployeeDAO empDAO = new EmployeeDAO(); // DAO의 역할 : DB와 직접적인 연결
	
	public ArrayList<Employee> selectAll() {  // 테이블 전체 SELECT만 해오면 되기 때문에 가공 필요X
//		ArrayList<Employee> list = empDAO.selectAll();
		return empDAO.selectAll();
	}
	
	
}
