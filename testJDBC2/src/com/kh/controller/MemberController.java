package com.kh.controller;

import java.util.ArrayList;

import com.kh.model.service.MemberService;
import com.kh.model.vo.Member;
import com.kh.view.MemberMenu;

public class MemberController {
	private MemberMenu menu = new MemberMenu();
//	private MemberDAO md = new MemberDAO();
	private MemberService mService = new MemberService();
	
	public void insertMember() {
		Member mem = menu.insertMember();
		int result = mService.insertMember(mem);
		if (result > 0) {
			menu.displaySuccess(result + "���� ���� �߰��Ǿ����ϴ�.");
		} else {
			menu.displayError("������ ���� ���� �� ���� �߻�");
		}
	}

	public void seletAll() {
		ArrayList<Member> list = mService.seletAll();
		
		if (!list.isEmpty()) {
			menu.displayMember(list);
		} else {
			menu.displayError("��ȸ�� ����� �����ϴ�.");
		}
	}

	public void selectMember() {
		int sel = menu.selectMember(); // � �������� �˻��� ������ View���� ����ڿ��� �Է¹��� 
		ArrayList<Member> list = null;
		
		switch (sel) {
		case 1 : 
			String id = menu.inputMemberId();
			list = mService.selectMemberId(id);
			if (!list.isEmpty()) {
				menu.displayMember(list);
			} else {
				menu.displayError("��ȸ�� ����� �����ϴ�.");
			}
			break;
		case 2 :
			char gen = menu.inputGender();
			list = mService.selectGender(gen);
			if (!list.isEmpty()) {
				menu.displayMember(list);
			} else {
				menu.displayError("��ȸ�� ����� �����ϴ�.");
			}
			break;
		case 0 :
			return;
		}

	}

	public void updateMember() {
		String memberId = menu.inputMemberId();
		
		// DB�� ����ڿ��� �Է¹��� memberId�� �ִ��� ������ Ȯ���ؾ���
		int check = mService.checkMember(memberId); // id��ġ�ϴ� ȸ�� ������ 1 ��ȯ�� ������ 0
		
		if (check != 1) {
			menu.displayError("�Է��� ���̵� �������� �ʽ��ϴ�.");
		} else {
			int sel = menu.updateMember();
			
			if (sel == 0) return;
			
			String input = menu.inputUpdate();
			
			int result = mService.updateMember(memberId, input, sel);
			
			if (result > 0) {
				menu.displaySuccess(result + "���� ���� �����Ǿ����ϴ�.");
			} else {
				menu.displayError("������ ���� ���� �� ���� �߻�");
			}

		}
		
	}


	public void deleteMember() {
		String memberId = menu.inputMemberId();
		
		int check = mService.checkMember(memberId);
		
		if(check != 1) {
			menu.displayError("�Է��� ���̵� �������� �ʽ��ϴ�.");
		} else {
			
			char yn = menu.checkDelete();
			if (yn == 'N') return;
			
			int result = mService.deleteMember(memberId);
				
			if (result > 0) {
				menu.displaySuccess(result + "���� ���� �����Ǿ����ϴ�.");
			} else {
				menu.displayError("������ ���� ���� �� ���� �߻�");
			}

		}
		
	}
	
	public void exitProgram() {
		mService.exitProgram();
	}
	
}
