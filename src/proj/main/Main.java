package proj.main;

import proj.dao.MemberDAO;
import proj.util.Constant;
import proj.util.DBCon;

public class Main{
	private MemberDAO dao;
	
	public static void main(String[] args) {
		//DB connector 실행
		DBCon.getConnection();
		//메인 메뉴 호출
		new Main().menu();
	}
	
	// GEN_001 메인 메뉴
	public void menu() {
		// COM_001 시스템 종료
	}
	// GEN_002 로그인
	public void login() {
		// 회원 메뉴
		// 관리자 메뉴
	}
	
	// GEN_003 회원 가입
	public void join() {

	}
	
	// GEN_004 아이디 찾기
	public void findID() {

	}
	// GEN_005 비밀번호 찾기
	public void findPW() {

	}
	
	/**
	 * 회원 메서드
	 */
	//USR_001 회원 메뉴
	public void memberMenu() {
		System.out.println(">> 회원 메뉴");
	}
	//USR_003 내 정보 수정
	public void myInforEdit(String id) {
	}
	//USR_004 회원 탈퇴
	public void memberRemove(String id) {

	}
	//USR_005 내 서재
	public void memberLibrary() {
		System.out.println("    1.대여정보   2.도서 예약 현황   3.신청 도서 현황  4.내 건의사항");
		System.out.println("------------------------------------------");
	}
	
	/**
	 * 관리자  메서드
	 */
	//ADM_001관리자 메뉴
	public void adminMenu() {
		System.out.println(">> 관리자 메뉴");
	}
	//ADM_003회원정보수정
	public void memberRevise(String id) {

	}
	//ADM_004회원삭제
	public void memberDelete(String id) {

	}
	//ADM_002회원관리
	public void memberList() {
	}
	/**
	 * 공용 메서드
	 */
	//COM_002 내정보
	public void memberInfo(String id) {
	}
	public void logout() {
	}
}
