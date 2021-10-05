package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.dao.EmployeeDAO;
import com.kh.model.vo.Employee;
import com.kh.view.Menu;

public class EmployeeController { // Controller의 역할 : View와  Model 연결, 넘겨받은 데이터 가공
	private Menu menu = new Menu();
	private EmployeeDAO empDAO = new EmployeeDAO(); // DAO의 역할 : DB와 직접적인 연결
	
	public void selectAll() {  // 테이블 전체 SELECT만 해오면 되기 때문에 가공 필요X
		ArrayList<Employee> list = empDAO.selectAll();
		
		if(!list.isEmpty()) {
			// list 안에 저장된 데이터가 있다면 조회 결과를 출력하는 view이동
			menu.selectAll(list);
		} else {
			// list 안에 저장된 데이터가 없다면 에러를 띄우는 view이동
			menu.displayError("조회 결과가 없습니다.");
		}
	}

	public void selectEmployee() {
		// 사번을 받기 위해 view로 이동 (사용자에게 입력 받음)
		int empNo = menu.selectEmpNo();
		
		Employee emp = empDAO.selectEmployee(empNo);
		
		if (emp != null) {
			menu.selectEmployee(emp);
		} else {
			menu.displayError("해당 사번에 대한 결과가 없습니다.");
		}
		
	}

	public void insertEmployee() {
		Employee emp = menu.insertEmployee();
		
		int result = empDAO.insertEmployee(emp);
		
		if (result > 0) {
			menu.displaySuccess(result + "개의 행이 추가되었습니다.");
		} else {
			menu.displayError("데이터 삽입 과정 중 오류 발생");
		}
	}

	public void updateEmployee() {
		int empNo = menu.selectEmpNo();
		
		Employee e = menu.updateEmployee();
		e.setEmpNo(empNo);
		
		int result = empDAO.updateEmployee(e);
		
		if (result > 0) {
			menu.displaySuccess(result + "개의 행이 수정되었습니다.");
		} else {
			menu.displayError("데이터 수정 과정 중 오류 발생");
		}
	}

	public void deleteEmployee() {
		int empNo = menu.selectEmpNo();
		
		char check = menu.deleteEmployee(); // 정말 삭제할 것인지 사용자에게 확인
		
		if (check == 'y') {
			int result = empDAO.deleteEmployee(empNo);
			
			if (result > 0) {
				menu.displaySuccess(result + "개의 행이 삭제되었습니다.");
			} else {
				menu.displayError("사번이 존재하지 않습니다.");
			}

		} else if(check == 'n'){
			menu.displayError("사원 정보 삭제 취소");
		} else {
			menu.displayError("잘못 입력하셨습니다(y 또는 n 입력).");
		}
	}
	
	
}
