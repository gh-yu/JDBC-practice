package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.EmployeeController;
import com.kh.model.vo.Employee;

public class Menu {
	private Scanner sc = new Scanner(System.in);
	// ĸ��ȭ : �ۿ��� �������� ���ϵ��� ����
	
	EmployeeController ec = new EmployeeController();
	
	public void mainMenu() {

		
		int user = 0;
		do {
			System.out.println("========================");
			System.out.println("[Main Menu]");
			System.out.println("1. ��ü ��� ���� ��ȸ");
			System.out.println("2. ������� ��� ���� ��ȸ");
			System.out.println("3. ������� ��� ���� �߰�");
			System.out.println("4. ������� ��� ���� ����");
			System.out.println("5. ������� ��� ���� ����");
			System.out.println("0.���α׷� ����");
			System.out.println("========================");
			System.out.print("�޴� ����  : ");
			user = Integer.parseInt(sc.nextLine());
			
			
			switch(user) {
			case 1 : selectAll(); break; 
//			case 2 : selectEmp(); break;
//			case 3 : addEmp(); break;
//			case 4 : modifyEmp(); break;
//			case 5 : deleteEmp(); break;
			case 0 : System.out.println("�����մϴ�."); break;
			default : System.out.println("�߸� �Է��ϼ̽��ϴ�."); 
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
