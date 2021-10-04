package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.EmployeeController;
import com.kh.model.vo.Employee;

public class Menu {
	private Scanner sc = new Scanner(System.in);
	// 캡슐화 : 밖에서 접근하지 못하도록 막음
	
	EmployeeController ec = new EmployeeController();
	
	public void mainMenu() {

		
		int user = 0;
		do {
			System.out.println("========================");
			System.out.println("[Main Menu]");
			System.out.println("1. 전체 사원 정보 조회");
			System.out.println("2. 사번으로 사원 정보 조회");
			System.out.println("3. 사번으로 사원 정보 추가");
			System.out.println("4. 사번으로 사원 정보 수정");
			System.out.println("5. 사번으로 사원 정보 삭제");
			System.out.println("0.프로그램 종료");
			System.out.println("========================");
			System.out.print("메뉴 선택  : ");
			user = Integer.parseInt(sc.nextLine());
			
			
			switch(user) {
			case 1 : selectAll(); break; 
//			case 2 : selectEmp(); break;
//			case 3 : addEmp(); break;
//			case 4 : modifyEmp(); break;
//			case 5 : deleteEmp(); break;
			case 0 : System.out.println("종료합니다."); break;
			default : System.out.println("잘못 입력하셨습니다."); 
			}
			System.out.println();
		} while (user != 0);

	}
	
	private void selectAll() {
		ArrayList<Employee> list = ec.selectAll();
		
		for (Employee e : list) {
			System.out.println(e);
		}
	}

}
