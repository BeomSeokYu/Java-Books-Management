package proj.util;

public interface Constant {
	/****************************************************
	 * 상수
	 ****************************************************/
	String ADMIN_ID = "admin";
	String FL = " ============== ";
	String EL_S = " ------------------------------------ ";
	String EL_M = " -------------------------------------------- ";
	String EL_L = " ---------------------------------------------------- ";
	String EL_XL = " ------------------------------------------------------------------------ ";
	String SELECT = ">> 선택 : ";
	String FORMAT_BOOK_LIST_COL = " %-5s\t%-25s\t%-25s\t%s\n";
	String FORMAT_BOOK_LIST_DAT = " %-6d\t%-25s\t%-25s\t%s\n";
	String STR_EMPTY = "";
	int LIST_ALL = 1;
	int LIST_SEARCH = 2;
	int FLAG_MEMBER_LIB = 1;
	int FLAG_MEMBER_SUG = 2;
	
	/****************************************************
	 * 헤더
	 ****************************************************/
	String HD_MENU = FL+"메인 메뉴"+FL;
	String HD_LOGIN = FL+"로그인"+FL;
	String HD_ADMIN_MENU = FL+"관리자 메뉴"+FL;
	String HD_MEMBER_MENU = FL+"회원 메뉴"+FL;
	String HD_JOIN = FL+"회원 가입"+FL;
	String HD_MY_LIBRARY = FL+"내 서재"+FL;
	String HD_FIND_ID = FL+"아이디 찾기"+FL;
	String HD_FIND_PW = FL+"비밀번호 찾기"+FL;
	String HD_MEMBER_LIST = FL+"회원 목록"+FL;
	String HD_MEMBER_INFO = FL+"회원 상세 정보"+FL;
	String HD_MOD_MY_INFO = FL+"내 정보 수정"+FL;
	String HD_MOD_MEMBER_INFO = FL+"회원 정보 수정"+FL;
	String HD_DEL_MEMBER = FL+"회원 삭제"+FL;
	String HD_LOGOUT = FL+"로그아웃"+FL;
	
	String HD_BOOK_LIST = FL+"도서 목록"+FL;
	String HD_BOOK_INFO = FL+"도서 상세 보기"+FL;
	String HD_BOOK_MENU = FL+"도서 메뉴"+FL;
	String HD_BOOK_SEARCH_MENU = FL+"도서 검색 메뉴"+FL;
	String HD_BOOK_SEARCH_BOOK_NAME = FL+"도서명 검색"+FL;
	String HD_BOOK_SEARCH_AUTHOR = FL+"저자 검색"+FL;
	String HD_BOOK_SEARCH_PUBLISHER = FL+"출판사 검색"+FL;
	String HD_BOOK_SEARCH_LIST = FL+"검색 목록"+FL;
	String HD_BOOK_ADMIN_MENU = FL+"도서 관리"+FL;
	String HD_BOOK_REGIST = FL+"신규 도서 등록"+FL;
	String HD_APP_BOOK_REGIST = FL+"신청 도서 등록"+FL;
	String HD_BOOK_DEL = FL+"도서 삭제"+FL;
	String HD_BOOK_MOD = FL+"도서 수정"+FL;
	
	String HD_BORROWLIST = FL+"대여 목록"+FL;
	String HD_BORROW = FL+"대여"+FL;
	
	String HD_SUG_LIST = FL + "건의 사항" + FL;
	String HD_SUG_MYLIST = FL + "건의 사항" + FL;
	String HD_SUG_REGIST = FL + "건의 사항 등록" + FL;
	String HD_SUG_DELETE = FL + "건의 사항 등록" + FL;
	String HD_SUG_REPLY_REGIST =  FL + "건의 사항 댓글 등록" + FL;
	String HD_SUG_REPLY_CHECK = FL + "건의 사항 확인 체크" + FL;
	
	String HD_NOTICE_LIST = FL+"공지 사항 목록"+FL;
	
	String HD_MEMB_INFO = FL+"회원 상세 정보"+FL;
	String HD_SUG_INFO = FL + "건의 사항 상세 정보" + FL;

	String HD_MOD_INFO_MENU = FL+"내 정보 수정"+FL;
	String HD_WITHDRAW = FL+"회원 탈퇴"+FL;

	String HD_ADMIN_S_COMMENT = FL+"관리자 댓글"+FL;
	String HD_SUG_MOD = FL + "건의 사항 수정" + FL;

}