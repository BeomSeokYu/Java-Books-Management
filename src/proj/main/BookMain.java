package proj.main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;

import proj.dao.BookDAO;
import proj.util.Constant;
import proj.util.DBCon;
import proj.util.Pub;
import proj.vo.BookVO;

public class BookMain {
	private BookDAO bDao;
	private ArrayList<BookVO> list;
	
	public BookMain() {
		bDao = new BookDAO();
	}
	
	public static void main(String[] args) {
		Pub.id = "admin1";
		//DB connector 실행
		DBCon.getConnection();
		//메인 메뉴 호출
		if(Pub.id.equals(Constant.ADMIN_ID))
			new BookMain().bookAdminMenu();
		else
			new BookMain().bookMemberMenu();
	}
	
	
	
	/********************************************************************************
	 * 공용 메서드
	 ********************************************************************************/
	/**
	 * COM_005 전체 도서 목록
	 */
	public void bookList() {
		while (true) {
			boolean loop = false;
			list = (ArrayList<BookVO>) bDao.selectAllBook();
			System.out.println(Constant.HD_BOOK_LIST);
			System.out.printf(Constant.FORMAT_BOOK_LIST_COL,"번호", "저자명", "출판사", "제목");
			if(!list.isEmpty()) {
				for(int i = 0; i < list.size(); i++) {
					System.out.printf(Constant.FORMAT_BOOK_LIST_DAT, 
							i+1, 
							list.get(i).getAuthor(), 
							list.get(i).getPublisher(), 
							list.get(i).getBookName());
				}
			} else {
				System.out.println(">> 저장된 목록이 없습니다");
			}
			System.out.println(Constant.EL_S);
			System.out.print(">> 상세히 볼 번호 선택(뒤로 : 0) : ");
			int bookidx = -2;
			try {
				bookidx = Pub.sc.nextInt()-1;
			} catch (Exception e) {}
			Pub.sc.nextLine();
			if(bookidx == -1 && Pub.id != null) {
				if(Pub.id.equals(Constant.ADMIN_ID)) {
					bookAdminMenu();
				} else {
					bookMemberMenu();
				}
			} else if (bookidx > -1 && bookidx < list.size()){
				bookInfo(list.get(bookidx), Constant.BOOK_LIST);
			} else {
				System.out.println(">> 없는 번호 입니다.");
				loop = true;
			}
			if (!loop) break;
		}
	}
	/**
	 * COM_006 도서 상세보기
	 */
	public void bookInfo(BookVO bvo, int from) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일 hh:mm:ss");
		while (true) {
			boolean loop = false;
			System.out.println(Constant.HD_BOOK_INFO);
			System.out.println("   도서번호 : " + bvo.getBookId());
			System.out.println("   도서명 : " + bvo.getBookName());
			System.out.println("   저자 : " + bvo.getAuthor());
			System.out.println("   출판사 : " + bvo.getPublisher());
			System.out.println("   등록일 : " + sdf.format(bvo.getRegDate()));
			System.out.println("   예약자수 : " + bvo.getReserNum());
			System.out.println("   대여가능여부 : " + bvo.getRentAvail());
			System.out.println(Constant.EL_M);
			if(Pub.id.equals(Constant.ADMIN_ID)) {
				System.out.println("   1.수정    2.삭제    3.뒤로");
			} else {
				System.out.println("   1.대여    2.예약    3.뒤로");
			}
			System.out.println(Constant.EL_M);
			System.out.print(Constant.SELECT);
			int select = 0;
			try {
				select = Pub.sc.nextInt();
			} catch (InputMismatchException e) {}
			Pub.sc.nextLine();
			switch (select) {
			case 1:	if(Pub.id.equals(Constant.ADMIN_ID)) {
						modBook(bvo);
					} else {
						if("Y".equals(bvo.getRentAvail())) {
							new BookManagement().borrow(bvo.getBookId());
						} else {
							System.out.println(">> 이미 대여중입니다.");
						}
					}
					break;
			case 2:	if(Pub.id.equals(Constant.ADMIN_ID)) {
						delBook(bvo);
					} else {
						new BookManagement().reserve(bvo.getBookId());
					}
					break;
			case 3:	if(from == Constant.BOOK_LIST)   bookList();
					if(from == Constant.SEARCH_LIST) searchList();
					break;
			default:System.out.println(">> 1~3 중에 선택해 주세요.");
					loop = true;
					break;
			}
			if (!loop) break;
		}
	}
	
	
	
	
	
	/********************************************************************************
	 * 회원 메서드
	 ********************************************************************************/
	/**
	 * USR_010 도서 메뉴
	 */
	public void bookMemberMenu() {
		while (true) {
			boolean loop = false;
			System.out.println(Constant.HD_BOOK_MENU);
			System.out.println("  1.도서목록  2.도서검색  3.도서반납  4.뒤로");
			System.out.println(Constant.EL_S);
			System.out.print(Constant.SELECT);
			int select = 0;
			try {
				select = Pub.sc.nextInt();
			} catch (InputMismatchException e) {}
			Pub.sc.nextLine();
			switch (select) {
			case 1:	bookList();
					break;
			case 2:	searchMenu();
					break;
			case 3:	new BookManagement().borrowList();
					break;
			case 4:	new Main().memberMenu();
					break;
			default:System.out.println(">> 1~4 중에 선택해 주세요.");
					loop = true;
			}
			if(!loop) break;
		}
	}
	
	/**
	 * USR_011 도서 검색 메뉴
	 */
	public void searchMenu() {
		while (true) {
			boolean loop = false;
			System.out.println(Constant.HD_BOOK_SEARCH_MENU);
			System.out.println("  1.도서명   2.저자명   3.출판사   4.뒤로");
			System.out.println(Constant.EL_S);
			System.out.print(Constant.SELECT);
			int select = 0;
			try {
				select = Pub.sc.nextInt();
			} catch (InputMismatchException e) {}
			Pub.sc.nextLine();
			switch (select) {
			case 1:	searchByName();
					break;
			case 2:	searchByAuthor();
					break;
			case 3:	searchByPubli();
					break;
			case 4:	bookMemberMenu();
					break;
			default:System.out.println(">> 1~4 중에 선택해 주세요.");
					loop = true;
					break;
			}
			if(!loop) break;
		}
	}
	
	/**
	 * USR_012 검색 목록
	 */
	public void searchList() {
		while (true) {
			boolean loop = false;
			if(!list.isEmpty()) {
				System.out.println(Constant.HD_BOOK_SEARCH_LIST);
				System.out.printf(Constant.FORMAT_BOOK_LIST_COL,"번호", "저자명", "출판사", "제목");
				for(int i = 0; i < list.size(); i++) {
					System.out.printf(Constant.FORMAT_BOOK_LIST_DAT, 
							i+1,
							list.get(i).getAuthor(), 
							list.get(i).getPublisher(), 
							list.get(i).getBookName());
				}
				System.out.println(Constant.EL_S);
				System.out.print(">> 상세히 볼 번호 선택(뒤로 : 0) : ");
				int bookidx = -2;
				try {
					bookidx = Pub.sc.nextInt()-1;
				} catch (InputMismatchException e) {}
				Pub.sc.nextLine();	
				if(bookidx == -1) {
					searchMenu();
				} else if (bookidx > -1 && bookidx < list.size()){
					bookInfo(list.get(bookidx), Constant.SEARCH_LIST);
				} else {
					System.out.println(">> 없는 번호 입니다.");
					searchList();
				}
			} else {
				System.out.println(Constant.HD_BOOK_SEARCH_LIST);
				System.out.println("  검색 결과 없음");
				System.out.println(Constant.EL_S);
				System.out.println("  1.도서 신청     2.뒤로");
				System.out.println(Constant.EL_S);
				System.out.print(Constant.SELECT);
				int select = Pub.sc.nextInt();
				Pub.sc.nextLine();
				switch (select) {
				case 1:	new BookManagement().appBook();
						break;
				case 2:	searchMenu();
						break;
				default:System.out.println(">> 1~2 중에 선택해 주세요.");
						loop = true;
						break;
				}
			}
			if (!loop) break;
		}
	}
	
	/**
	 * USR_016 도서명검색 
	 */
	public void searchByName() {
		System.out.println(Constant.HD_BOOK_SEARCH_BOOK_NAME);
		System.out.print(">> 도서명 입력 : ");
		String sword = Pub.sc.nextLine();
		list = (ArrayList<BookVO>) bDao.selectName(sword);
		searchList();
		System.out.println(Constant.EL_S);
	}
	/**
	 * USR_017 저자명검색
	 */
	public void searchByAuthor(){
		System.out.println(Constant.HD_BOOK_SEARCH_BOOK_NAME);
		System.out.print(">> 저자명 입력 : ");
		String sword = Pub.sc.nextLine();
		list = (ArrayList<BookVO>) bDao.selectAuthor(sword);
		searchList();
		System.out.println(Constant.EL_S);
	}
	
	/**
	 * USR_018 출판사검색
	 */
	public void searchByPubli() {
		System.out.println(Constant.HD_BOOK_SEARCH_BOOK_NAME);
		System.out.print(">> 출판사 입력 : ");
		String sword = Pub.sc.nextLine();
		list = (ArrayList<BookVO>) bDao.selectPublisher(sword);
		searchList();
		System.out.println(Constant.EL_S);
	}
	
	
	
	
	
	/********************************************************************************
	 * 관리자 메서드
	 ********************************************************************************/
	/**
	 * ADM_005 도서 관리
	 */
	public void bookAdminMenu() {
		while (true) {
			boolean loop = false;
			System.out.println(Constant.HD_BOOK_MENU);
			System.out.println("  1.도서목록     2.도서등록     3.뒤로");
			System.out.println(Constant.EL_S);
			System.out.print(Constant.SELECT);
			int select = 0;
			try {
				select = Pub.sc.nextInt();
			} catch (InputMismatchException e) {}
			Pub.sc.nextLine();
			switch (select) {
			case 1:	bookList();
					break;
			case 2:	regBook();
					break;
			case 3:	new Main().adminMenu();
					break;
			default:System.out.println(">> 1~3 중에 선택해 주세요.");
					loop = true;
					break;
			}
			if(!loop) break;
		}
	}
	/**
	 * ADM_008 신규 도서 등록 
	 */
	public void regBook() {
		BookVO bvo = new BookVO();
		System.out.println(Constant.HD_BOOK_REGIST);
		System.out.print("  제목 : ");
		String title = Pub.sc.nextLine();
		System.out.print("  저자 : ");
		String auth = Pub.sc.nextLine();
		System.out.print("  출판사 : ");
		String pub = Pub.sc.nextLine();
		if (title.equals("") || auth.equals("") || pub.equals("")) {
			System.out.println(">> 입력이 없는 항목이 존재하여 등록이 취소되었습니다.");
		} else {
			bvo.setBookName(title);
			bvo.setAuthor(auth);
			bvo.setPublisher(pub);
			if(bDao.insert(bvo)) {
				System.out.println(">> 등록되었습니다.");
			} else {
				System.out.println(">> 등록에 실패하였습니다.");
			}
		}
		bookAdminMenu();
	}
	/**
	 * ADM_007 도서 삭제
	 * @param bvo : BookVO
	 */
	public void delBook(BookVO bvo) {
		System.out.println(Constant.HD_BOOK_DEL);
		System.out.print(">> 정말로 삭제하시겠습니까? (Y/n) :");
		String select = Pub.sc.nextLine();
		if (select.equalsIgnoreCase("y") || select.equalsIgnoreCase("")) {
			if(bDao.delete(bvo.getBookId())) {
				System.out.println(">> 삭제되었습니다.");
				bookList();
			}
		} else {
			System.out.println(">> 삭제를 취소하였습니다.");
			bookAdminMenu();
		}
	}
	/**
	 * ADM_006 도서 수정
	 * @param bvo : BookVO
	 */
	public void modBook(BookVO bvo) {
		System.out.println(Constant.HD_BOOK_MOD);
		System.out.print("  제목 : ");
		String title = Pub.sc.nextLine();
		System.out.print("  저자 : ");
		String auth = Pub.sc.nextLine();
		System.out.print("  출판사 : ");
		String pub = Pub.sc.nextLine();
		if (title.equals("") || auth.equals("") || pub.equals("")) {
			System.out.println(">> 입력이 없는 항목이 존재하여 수정이 취소되었습니다.");
		} else {
			bvo.setBookName(title);
			bvo.setAuthor(auth);
			bvo.setPublisher(pub);
			if(bDao.update(bvo)) {
				System.out.println(">> 도서가 수정되었습니다.");
			} else {
				System.out.println(">> 도서 수정에 실패하였습니다.");
			}
		}
	}
}
