package proj.util;

import java.util.Scanner;

public class Pub {
	public static Scanner sc = new Scanner(System.in);
	public static String id;
}

interface Constant {
	/****************************************************
	 * 상수
	 ****************************************************/
	String ADMIN_ID = "admin";
	String FL = " ============== ";
	String EL = " -------------------------------------------- ";
	
	
	/****************************************************
	 * 헤더
	 ****************************************************/
	// 공용 헤더
	String HD_MAIN_MENU = FL+"메인 메뉴"+FL;
	String HD_LOGIN = FL+"로그인"+FL;
	String HD_JOIN = FL+"회원 가입"+FL;
	String HD_FIND_ID = FL+"아이디 찾기"+FL;
	String HD_FIND_PW = FL+"비밀번호 찾기"+FL;
	String HD_EXIT = FL+"시스템 종료"+FL;
	
	String HD_MEMB_INFO = FL+"회원 상세 정보"+FL;
	
	String HD_BORROW = FL+"대여 정보"+FL;
	
	// 회원 헤더
	String HD_MOD_INFO_MENU = FL+"내 정보 수정"+FL;
	String HD_WITHDRAW = FL+"회원 탈퇴"+FL;
	String HD_MY_LIBRARY = FL+"내 서재"+FL;
	
	// 관리자 헤더
	String HD_ADMIN_MANU = FL+"관리자 메뉴"+FL;
	
	

}