package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.EmployeeDAO;
import com.kh.model.vo.Employee;
//import com.kh.view.Menu;

public class EmployeeController { // Controller�� ���� : View��  Model ����, �Ѱܹ��� ������ ����
//	private Menu menu = new Menu();
	private EmployeeDAO empDAO = new EmployeeDAO(); // DAO�� ���� : DB�� �������� ����
	
	public ArrayList<Employee> selectAll() {  // ���̺� ��ü SELECT�� �ؿ��� �Ǳ� ������ ���� �ʿ�X
//		ArrayList<Employee> list = empDAO.selectAll();
		return empDAO.selectAll();
	}
	
	
}
