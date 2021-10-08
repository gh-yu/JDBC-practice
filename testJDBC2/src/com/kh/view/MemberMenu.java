package com.kh.view;

import java.util.ArrayList;
import java.util.Scanner;

import com.kh.controller.MemberController;
import com.kh.model.vo.Member;

public class MemberMenu {

	private Scanner sc = new Scanner(System.in);
	
	public void mainMenu() {
		MemberController mc = new MemberController();
		
		int select = 0;
		do {
			System.out.println("\n *** ȸ�� ���� ���α׷� *** \n");
			System.out.println("1. �� ȸ�� ���");
			System.out.println("2. ��� ȸ�� ��ȸ");
			System.out.println("3. Ư�� ���� ȸ�� ��ȸ");
			System.out.println("4. ȸ�� ���� ����");
			System.out.println("5. ȸ�� Ż��");
			System.out.println("0. ���α׷� ����");
			System.out.print("��ȣ ���� : ");
			select = Integer.parseInt(sc.nextLine());
			
			switch(select) {
			case 1 : mc.insertMember(); break;
			case 2 : mc.seletAll(); break;
			case 3 : mc.selectMember(); break;
			case 4 : mc.updateMember(); break;
			case 5 : mc.deleteMember(); break;
			case 0 : System.out.println("���α׷��� �����մϴ�."); mc.exitProgram(); break; // ���α׷� �����ϸ鼭 ����������  Connection close() �ڿ��ݳ�
			default : System.out.println("�߸��� ��ȣ�Դϴ�. �ٽ� �Է����ּ���.");
			}
		} while (select != 0);
		
	}

	public Member insertMember() {
		System.out.print("ȸ�� ���̵� : ");
		String memberId = sc.nextLine();
		
		System.out.print("��й�ȣ : ");
		String memberPwd = sc.nextLine();
		
		System.out.print("�̸� : ");
		String memberName = sc.nextLine();
		
		System.out.print("����(��:M/��:F) : ");
		char gender = sc.nextLine().toUpperCase().charAt(0);
		
		System.out.print("�̸��� : ");
		String email = sc.nextLine();
		
		System.out.print("��ȭ��ȣ(-����) : ");
		String phone = sc.nextLine();
		
		System.out.print("�ּ� : ");
		String address = sc.nextLine();
		
		System.out.print("���� : ");
		int age = Integer.parseInt(sc.nextLine());
		
		Member member = new Member(memberId, memberPwd, memberName, gender, email, phone, address, age);
		
		return member;
	}

	public void displaySuccess(String msg) {
		System.out.println("���� ��û ���� : " + msg);
	}

	public void displayError(String msg) {
		System.out.println("���� ��û ���� : " + msg);
	}

	public void displayMember(ArrayList<Member> list) {
		System.out.println("���̵�, ��й�ȣ, �̸�, ����, �̸���, ��ȭ��ȣ, �ּ�, ����, ������");
		for(Member mem : list) {
			System.out.println(mem);
		}
	}

	public int selectMember() {
		
		while (true) {
			System.out.println("1. ���̵�� ȸ�� ��ȸ");
			System.out.println("2. ������ ȸ�� ��ȸ");
			System.out.println("0. ���θ޴��� ���ư���");
			System.out.print("��ȣ ���� : ");
			int select = Integer.parseInt(sc.nextLine());
			
			switch(select) {
			case 1 : case 2 : case 0 : return select;
			default : System.out.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
			}
		} 
		
	}

	public String inputMemberId() {
		System.out.print("ȸ�� ���̵� : ");
		String id = sc.nextLine();
		return id;
	}

	public char inputGender() {
		System.out.print("ȸ�� ���� (��:M/��:F) : ");
		char gen = sc.nextLine().toUpperCase().charAt(0);
		return gen;
	
	}

	public int updateMember() {
		while (true) {
			System.out.println("1. ��й�ȣ ����");
			System.out.println("2. �̸��� ����");
			System.out.println("3. ��ȭ��ȣ ����");
			System.out.println("4. �ּ� ����");
			System.out.println("0. ���� �޴��� ���ư���");
			System.out.print("��ȣ ���� : ");
			int select = Integer.parseInt(sc.nextLine());
			
			switch(select) {
			case 1 : case 2 : case 3 : case 4 : case 0 : return select;
			default : System.out.println("�߸� �Է��ϼ̽��ϴ�. �ٽ� �Է����ּ���.");
			}
		}		
	}

	public String inputUpdate() {
		System.out.print("������ ������ �Է� : ");
		String input = sc.nextLine();
		
		return input;
	}

	public char checkDelete() {
		System.out.println("\n *** ID�� Ȯ�εǾ����ϴ� ***\n");
		
		System.out.print("������ �����Ͻðڽ��ϱ�?(Y/N) : ");
		char yn = sc.nextLine().toUpperCase().charAt(0);
		return yn;
	}

	
}
