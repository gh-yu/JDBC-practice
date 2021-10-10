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
			menu.displaySuccess(result + "개의 행이 추가되었습니다.");
		} else {
			menu.displayError("데이터 삽입 과정 중 오류 발생");
		}
	}

	public void seletAll() {
		ArrayList<Member> list = mService.seletAll();
		
		if (!list.isEmpty()) {
			menu.displayMember(list);
		} else {
			menu.displayError("조회된 결과가 없습니다.");
		}
	}

	public void selectMember() {
		int sel = menu.selectMember(); // 어떤 조건으로 검색할 것인지 View에서 사용자에게 입력받음 
		ArrayList<Member> list = null;
		
		switch (sel) {
		case 1 : 
			String id = menu.inputMemberId();
			list = mService.selectMemberId(id);
			if (!list.isEmpty()) {
				menu.displayMember(list);
			} else {
				menu.displayError("조회된 결과가 없습니다.");
			}
			break;
		case 2 :
			char gen = menu.inputGender();
			list = mService.selectGender(gen);
			if (!list.isEmpty()) {
				menu.displayMember(list);
			} else {
				menu.displayError("조회된 결과가 없습니다.");
			}
			break;
		case 0 :
			return;
		}

	}

	public void updateMember() {
		String memberId = menu.inputMemberId();
		
		// DB에 사용자에게 입력받은 memberId가 있는지 없는지 확인해야함
		int check = mService.checkMember(memberId); // id일치하는 회원 있으면 1 반환됨 없으면 0
		
		if (check != 1) {
			menu.displayError("입력한 아이디가 존재하지 않습니다.");
		} else {
			int sel = menu.updateMember();
			
			if (sel == 0) return;
			
			String input = menu.inputUpdate();
			
			int result = mService.updateMember(memberId, input, sel);
			
			if (result > 0) {
				menu.displaySuccess(result + "개의 행이 수정되었습니다.");
			} else {
				menu.displayError("데이터 수정 과정 중 오류 발생");
			}

		}
		
	}


	public void deleteMember() {
		String memberId = menu.inputMemberId();
		
		int check = mService.checkMember(memberId);
		
		if(check != 1) {
			menu.displayError("입력한 아이디가 존재하지 않습니다.");
		} else {
			
			char yn = menu.checkDelete();
			if (yn == 'N') return;
			
			int result = mService.deleteMember(memberId);
				
			if (result > 0) {
				menu.displaySuccess(result + "개의 행이 삭제되었습니다.");
			} else {
				menu.displayError("데이터 삭제 과정 중 오류 발생");
			}

		}
		
	}
	
	public void exitProgram() {
		mService.exitProgram();
	}
	
}
