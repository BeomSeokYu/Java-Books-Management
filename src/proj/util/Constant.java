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
	String SELECT = ">> 선택 : ";
	String FORMAT_BOOK_LIST_COL = " %-5s\t%-10s\t%-10s\t%-50s\n";
	String FORMAT_BOOK_LIST_DAT = " %-6d\t%-10s\t%-10s\t%-50s\n";
	int SEARCH_LIST = 1;
	int BOOK_LIST = 2;
	
	
	/****************************************************
	 * 헤더
	 ****************************************************/
	// 공용 헤더
	
	String HD_BOOK_LIST = FL+"도서 목록"+FL;
	String HD_BOOK_INFO = FL+"도서 상세 보기"+FL;
	String HD_BOOK_MENU = FL+"도서 메뉴"+FL;
	String HD_BOOK_SEARCH_MENU = FL+"도서 검색 메뉴"+FL;
	String HD_BOOK_SEARCH_BOOK_NAME = FL+"도서명 검색"+FL;
	String HD_BOOK_SEARCH_AUTHOR = FL+"저자 검색"+FL;
	String HD_BOOK_SEARCH_PUBLISHER = FL+"출판사 검색"+FL;
	String HD_BOOK_SEARCH_LIST = FL+"검색 목록"+FL;
	String HD_BOOK_ADMIN_MENU = FL+"도서 관리"+FL;
	String HD_BOOK_REGIST = FL+"도서 등록"+FL;
	String HD_BOOK_DEL = FL+"도서 삭제"+FL;
	String HD_BOOK_MOD = FL+"도서 수정"+FL;
	
	
	// 회원 헤더

	
	// 관리자 헤더

}