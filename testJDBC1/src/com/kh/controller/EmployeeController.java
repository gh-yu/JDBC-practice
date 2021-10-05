package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.EmployeeDAO;
import com.kh.model.vo.Employee;
import com.kh.view.Menu;

public class EmployeeController { // Controller�� ���� : View��  Model ����, �Ѱܹ��� ������ ����
	private Menu menu = new Menu();
	private EmployeeDAO empDAO = new EmployeeDAO(); // DAO�� ���� : DB�� �������� ����
	
	public void selectAll() {  // ���̺� ��ü SELECT�� �ؿ��� �Ǳ� ������ ���� �ʿ�X
		ArrayList<Employee> list = empDAO.selectAll();
		
		if(!list.isEmpty()) {
			// list �ȿ� ����� �����Ͱ� �ִٸ� ��ȸ ����� ����ϴ� view�̵�
			menu.selectAll(list);
		} else {
			// list �ȿ� ����� �����Ͱ� ���ٸ� ������ ���� view�̵�
			menu.displayError("��ȸ ����� �����ϴ�.");
		}
	}

	public void selectEmployee() {
		// ����� �ޱ� ���� view�� �̵� (����ڿ��� �Է� ����)
		int empNo = menu.selectEmpNo();
		
		Employee emp = empDAO.selectEmployee(empNo);
		
		if (emp != null) {
			menu.selectEmployee(emp);
		} else {
			menu.displayError("�ش� ����� ���� ����� �����ϴ�.");
		}
		
	}

	public void insertEmployee() {
		Employee emp = menu.insertEmployee();
		
		int result = empDAO.insertEmployee(emp);
		
		if (result > 0) {
			menu.displaySuccess(result + "���� ���� �߰��Ǿ����ϴ�.");
		} else {
			menu.displayError("������ ���� ���� �� ���� �߻�");
		}
	}

	public void updateEmployee() {
		int empNo = menu.selectEmpNo();
		
		Employee e = menu.updateEmployee();
		e.setEmpNo(empNo);
		
		int result = empDAO.updateEmployee(e);
		
		if (result > 0) {
			menu.displaySuccess(result + "���� ���� �����Ǿ����ϴ�.");
		} else {
			menu.displayError("������ ���� ���� �� ���� �߻�");
		}
	}

	public void deleteEmployee() {
		int empNo = menu.selectEmpNo();
		
		char check = menu.deleteEmployee(); // ���� ������ ������ ����ڿ��� Ȯ��
		
		if (check == 'y') {
			int result = empDAO.deleteEmployee(empNo);
			
			if (result > 0) {
				menu.displaySuccess(result + "���� ���� �����Ǿ����ϴ�.");
			} else {
				menu.displayError("����� �������� �ʽ��ϴ�.");
			}

		} else if(check == 'n'){
			menu.displayError("��� ���� ���� ���");
		} else {
			menu.displayError("�߸� �Է��ϼ̽��ϴ�(y �Ǵ� n �Է�).");
		}
	}
	
	
}
