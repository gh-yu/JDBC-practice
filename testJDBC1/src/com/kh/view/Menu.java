package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.EmployeeController;
import com.kh.model.vo.Employee;

public class Menu {
	private Scanner sc = new Scanner(System.in);
	// ĸ��ȭ : �ۿ��� �������� ���ϵ��� ����
	
	public void mainMenu() {
		EmployeeController ec = new EmployeeController(); // ��Ʈ�ѷ� ��ü ���� �޼ҵ� ���ΰ� �ƴ� �ʵ忡 �ۼ��ϸ� StackOverflowError �߻�

		int user = 0;
		do {
			System.out.println("========================");
			System.out.println("[Main Menu]");
			System.out.println("1. ��ü ��� ���� ��ȸ");
			System.out.println("2. ������� ��� ���� ��ȸ");
			System.out.println("3. ��� ���� �߰�");
			System.out.println("4. ������� ��� ���� ����");
			System.out.println("5. ������� ��� ���� ����");
			System.out.println("0. ���α׷� ����");
			System.out.println("========================");
			System.out.print("�޴� ����  : ");
			user = Integer.parseInt(sc.nextLine());
			
			
			switch(user) {
			case 1 : ec.selectAll(); break; 
			case 2 : ec.selectEmployee(); break;
			case 3 : ec.insertEmployee(); break;
			case 4 : ec.updateEmployee(); break;
			case 5 : ec.deleteEmployee(); break;
			case 0 : System.out.println("�����մϴ�."); break;
			default : System.out.println("�߸� �Է��ϼ̽��ϴ�."); 
			}
			System.out.println();
		} while (user != 0);

	}
	
	public void selectAll(ArrayList<Employee> list) {
		System.out.println("��� / �̸� / ��å / ���ӻ�� / ����� / �޿� / Ŀ�̼� / �μ���ȣ");
		for (Employee emp : list) { // for each��
			System.out.println(emp); // emp�ӽú����� list�� ��� ��ü �ϳ��� �ְ� ���
		}
	}

	public void displayError(String msg) {
		System.out.println("���� ��û ���� : " + msg);
	}

	public int selectEmpNo() {
		
		System.out.print("����� �Է��ϼ��� : ");
		int empNo = Integer.parseInt(sc.nextLine());
		
		return empNo;
	}

	public void selectEmployee(Employee emp) {
		System.out.println("��� : " + emp.getEmpNo());
		System.out.println("�̸� : " + emp.getEmpName());
		System.out.println("��å : " + emp.getJob());
		System.out.println("���� ��� : " + emp.getMgr());
		System.out.println("����� : " + emp.getHireDate());
		System.out.println("�޿� : " + emp.getSal());
		System.out.println("Ŀ�̼� : " + emp.getComm());
		System.out.println("�μ���ȣ : " + emp.getDeptNo());
	}

	public Employee insertEmployee() {
		System.out.println("[���ο� ������� �߰�]");
		
		System.out.print("��� : ");
		int empNo = Integer.parseInt(sc.nextLine());
	
		System.out.print("�̸� : ");
		String empName = sc.nextLine();
		
		System.out.print("��å : ");
		String job = sc.nextLine();
		
		System.out.print("���� ��� ��� : ");
		int mgr = Integer.parseInt(sc.nextLine());
		
		System.out.print("�޿� : ");
		int sal = Integer.parseInt(sc.nextLine());

		System.out.print("�μ�Ƽ�� : ");
		int comm = Integer.parseInt(sc.nextLine());
		
		System.out.print("�μ� ��ȣ : ");
		int deptNo = Integer.parseInt(sc.nextLine());
		
		System.out.println();
		
		// hireDate�� SYSDATE�� �־��� ��
		Employee emp = new Employee(empNo, empName, job, mgr, sal, comm, deptNo);
		
		return emp;
	}

	public void displaySuccess(String msg) {
		System.out.println("���� ��û ���� : " + msg);
	}

	public Employee updateEmployee() {
		System.out.print("��å : ");
		String job = sc.nextLine();
		
		System.out.print("�޿� : ");
		int sal = Integer.parseInt(sc.nextLine());
		
		System.out.print("�μ�Ƽ�� : ");
		int comm = Integer.parseInt(sc.nextLine());
		
		Employee e = new Employee(job, sal, comm);
		
		return e;
	}

	public char deleteEmployee() {
		System.out.print("������ �����Ͻðڽ��ϱ�?(y/n) : ");
		char check = sc.nextLine().toLowerCase().charAt(0);

		return check;
	}
	

}
