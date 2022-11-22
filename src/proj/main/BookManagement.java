package proj.main;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import proj.dao.AppDAO;
import proj.dao.BookDAO;
import proj.dao.RentDAO;
import proj.dao.ReserDAO;
import proj.util.Constant;
import proj.util.Pub;
import proj.vo.AppVO;
import proj.vo.BookVO;
import proj.vo.RentVO;
import proj.vo.ReserVO;

public class BookManagement {
	private AppDAO adao;
	private BookDAO bdao;
	private RentDAO renDao;
	private ReserDAO resDao;
	private boolean result;
	private String input;
	private int inp;
	private String num;


	public BookManagement() {
		bdao = new BookDAO();
		renDao = new RentDAO();
		resDao = new ReserDAO();
		adao = new AppDAO();
	}

	/**
	 * 대여 실행
	 * 
	 * @param bookid
	 */
	public void borrow(int bookid, int from) {
		RentVO rvo = new RentVO();
		rvo.setBookId(bookid);
		rvo.setId(Pub.id);
		System.out.println(Constant.EL_S);
		ArrayList<RentVO> a = (ArrayList<RentVO>) renDao.selectAllRent(rvo.getId());
		int cnt = 0;
		for (RentVO vo : a) {
			if (vo.getReturnDate() == null) cnt++;
		}
		if (cnt >= 5) {
			System.out.println(">> 최대 대여 권수 (5권)을 초과해 대여할 수 없습니다");
		} else {
			if (renDao.insertRent(rvo)) {
				System.out.println("도서 대여가 완료되었습니다.");
			} else {
				System.out.println("도서 대여가 실패하였습니다.");
			}
		}
		new BookMain().bookList(from);
	};

	// 대여목록 (COM_003)
	public void borrowList(String id, int from) {
		List<RentVO> list = renDao.selectAllRent(id);
		System.out.println(Constant.HD_BORROWLIST);
		if (list == null || list.size() < 1) {
			System.out.println(">> 대여한 도서가 없습니다");
			if (Pub.id.equals(Constant.ADMIN_ID)) {
				new Main().adminMenu();
			} else if(from == Constant.FLAG_MEMBER_BOOK) {
				new BookMain().bookMemberMenu();
			} else {
				new Main().memberLibrary();
			}
		} else {
			System.out.printf("%-4s\t%-12s\t%-12s\t%-20s\t%s\n", "대여번호", "반납일", "반납마감일", "저자명", "제목");
			System.out.println(Constant.EL_XL);
			for (int i = 0; i < list.size(); i++) {
				RentVO rvo = list.get(i);
				System.out.printf("%-4d\t%-12s\t%-12s\t%-20s\t%s\n", rvo.getRentId(),
				(rvo.getReturnDate() == null ? "- 대여중 -" : rvo.getReturnDate()),
				rvo.getReturnDeadline(), rvo.getAuthor(), rvo.getBookName());
			}
			System.out.println(Constant.EL_XL);
			System.out.print(">> 상세히 볼 대여번호 선택 (0 : 뒤로 가기) : ");
			input = Pub.sc.nextLine();
			num = "^[0-9]+$";
			if (input.matches(num)) {
				if (Integer.parseInt(input) == 0) {
					if (Pub.id.equals(Constant.ADMIN_ID)) {
						new Main().adminMenu();
					} else if(from == Constant.FLAG_MEMBER_BOOK) {
						new BookMain().bookMemberMenu();
					} else {
						new Main().memberLibrary();
					}
				} else {
					RentVO rvo = renDao.select(Integer.parseInt(input));
					if (rvo != null) {
						borrowDetail(rvo, id, from);
					} else {
						System.out.println(">> 올바른 번호를 입력해주세요");
						borrowList(id, from);
						System.out.println(Constant.EL_S);
					}
				}
			} else {
				System.out.println(">> 올바른 형식의 문자를 입력해주세요");
				borrowList(id, from);
				System.out.println(Constant.EL_S);
			}
		}

	};

	// 대여정보 상세보기 (COM_004)
	public void borrowDetail(RentVO rvo, String id, int from) {
		boolean isBack = false;
		String today = Pub.sdf.format(new Date(System.currentTimeMillis()));
		String dead = Pub.sdf.format(rvo.getReturnDeadline());
		int r = today.compareTo(dead);
		long d = rvo.getReturnDeadline().getTime();
		long s = rvo.getRentDate().getTime();

		while (!isBack) {
			System.out.println(Constant.HD_BORROW);
			System.out.println("도서번호 : " + rvo.getBookId());
			System.out.println("제목 : " + rvo.getBookName());
			System.out.println("저자명 : " + rvo.getAuthor());
			System.out.println("대여일 : " + Pub.sdf.format(rvo.getRentDate()));
			System.out.println("반납마감일 : " + Pub.sdf.format(rvo.getReturnDeadline()));
			System.out.print("연장 가능 여부 : ");
			if (r < 0 && (d - s) / (24 * 1000 * 60 * 60) < 28) {
				System.out.println("YES");
			} else {
				System.out.println("NO");
			}
			;
			System.out.println(Constant.EL_S);
			if (Pub.id.equalsIgnoreCase(Constant.ADMIN_ID)) {
				System.out.println("1. 뒤로");
				System.out.println(Constant.EL_S);
				System.out.print(Constant.SELECT);
				input = Pub.sc.nextLine();
				num = "^[0-9]+$";
				if (input.matches(num)) {
					switch (Integer.parseInt(input)) {
					case 1:
						isBack = true;
						borrowList(id, from);
						break;
					default:
						System.out.println("올바른 숫자를 입력해주세요");
						System.out.println(Constant.EL_S);
						break;
					}
				} else {
					System.out.println(">> 올바른 형식의 문자를 입력해주세요");
					borrowDetail(rvo, id, from);
					System.out.println(Constant.EL_S);

				}
			} else {
				System.out.println("1. 기한 연장(1주일)    2. 반납    3. 뒤로");
				System.out.println(Constant.EL_S);
				System.out.print(">> 선택 : ");
				input = Pub.sc.nextLine();
				num = "^[0-9]+$";
				if (input.matches(num)) {
					switch (Integer.parseInt(input)) {
					case 1:
						borrowExtend(rvo, from);
						break;
					case 2:
						returnBook(rvo);
						break;
					case 3:
						borrowList(id, from);
						break;
					default:
						System.out.println("1~3 중의 숫자를 입력해주세요");
						System.out.println(Constant.EL_S);
						break;
					}
				} else {
					System.out.println(">> 올바른 형식의 문자를 입력해주세요");
					borrowDetail(rvo, id, from);
					System.out.println(Constant.EL_S);

				}
			}
		}
	}

	// 반납기한 연장
	public void borrowExtend(RentVO rvo, int from) {
		rvo.setId(Pub.id);
		System.out.println(Constant.EL_S);
		result = renDao.update(rvo);
		if (result) {
			System.out.println("반납기한 연장이 완료되었습니다.");
		} else {
			System.out.println("반납기한 연장이 실패하였습니다.");
		}
		borrowList(Pub.id, from);
	};

	// 도서 예약 실행
	public void reserve(int bookId, int from) {
		ReserVO revo = new ReserVO();
		RentVO rvo = renDao.selectBookId(bookId);
		System.out.println(Constant.EL_S);
		if (rvo != null && rvo.getId().equals(Pub.id)) {
			System.out.println(">> 자신이 대여한 책은 예약할 수 없습니다");
			new BookMain().bookList(1);
		} else {
			revo.setId(Pub.id);
			revo.setBookid(bookId);
			result = resDao.insertReser(revo);
			if (result) {
				System.out.println("도서 예약이 완료되었습니다.");
			} else {
				System.out.println("도서 예약이 실패하였습니다.");
			}
			new BookMain().bookList(from);
		}
	}

	// 도서 예약현황 (USR_006)
	public void reserStatus(String id) {
		List<ReserVO> list = resDao.selectMyReser(id);
		System.out.println(Constant.FL + "도서 예약 현황" + Constant.FL);
		if (list == null || list.size() < 1) {
			System.out.println(">> 저장된 목록이 없습니다");
		} else {
			System.out.printf("%-4s\t%-4s\t%-12s\t%-6s\t%-20s\t%s\n", "예약번호","도서번호","예약일","대여상태","저자","제목");
			System.out.println(Constant.EL_XL);
			for (ReserVO r : list) {
				System.out.printf("%-4d\t%-4d\t%-12s\t%-6s\t%-20s\t%s\n", r.getReserId(), r.getBookid(), r.getReserDate(),
						(r.getRentAvail().equalsIgnoreCase("Y") == true?"-대여가능-":"-대여중-"), r.getAuthor(), r.getBookName());

			}
		}
		System.out.println(Constant.EL_XL);
		System.out.println(">> 취소를 원하시면 해당 예약 번호를 입력해주세요");
		System.out.println(Constant.EL_S);
		System.out.print(">> 입력 (나가기 0) : ");
		input = Pub.sc.nextLine();
		num = "^[0-9]+$";
		if (input.matches(num)) {

			if (Integer.parseInt(input) == 0) {
				new Main().memberLibrary();

			} else {
				ReserVO revo = resDao.selectReser(Integer.parseInt(input));
				if (revo != null) {
					reserCancel(Integer.parseInt(input));
				} else {
					System.out.println(">> 올바른 번호를 입력해 주세요");
					reserStatus(id);
					System.out.println(Constant.EL_S);

				}
			}
		} else {
			System.out.println(">> 올바른 형식의 문자를 입력해주세요");
			reserStatus(id);
			System.out.println(Constant.EL_S);

		}

	}

	// 도서 예약취소
	public void reserCancel(int reserId) {
		ReserVO revo = new ReserVO();
		revo.setId(Pub.id);
		revo.setReserId(reserId);
		result = resDao.delete(reserId);
		System.out.println(Constant.EL_S);
		if (result) {
			System.out.println("도서 예약 취소가 완료되었습니다.");
		} else {
			System.out.println("도서 예약 취소가 실패하였습니다.");
		}
		reserStatus(Pub.id);
	}

	/**
	 * 도서 반납 (USR_014)
	 * 
	 * @param id
	 */
	public void returnBook(RentVO rvo) {
		System.out.println(Constant.EL_S);
		if (renDao.updateRent(rvo)) {
			System.out.println("도서 반납이 완료되었습니다.");
		} else {
			System.out.println("도서 반납이 실패하였습니다.");
		}
		new BookMain().bookMemberMenu();

	}
		
	public void prpsBookList(String id) {
		ArrayList<AppVO> prpsList;
		if(id.equals(Constant.ADMIN_ID)) {
			prpsList = adao.selectAllApp();
		} else {				
			prpsList = adao.selectMyApp(id);
		}
		
		if(prpsList == null || prpsList.size() < 1) {
			System.out.println("----------------신청 도서가 없습니다------------");
			System.out.println("--------------이전 목록으로 되돌아갑니다---------------");
			System.out.println();
			System.out.println();
			if(id.equals(Constant.ADMIN_ID)) {
				new BookMain().regBookMenu();				
			} else {
				new Main().memberLibrary();
			}
		}else {
			System.out.println("================= 신청 도서 목록 =================");
			System.out.printf(Constant.FORMAT_BOOK_LIST_COL,"번호", "저자명", "출판사", "책제목");
			System.out.println(Constant.EL_XL);
			for(AppVO avo : prpsList) {
				System.out.printf(Constant.FORMAT_BOOK_LIST_COL, avo.getAppId(), avo.getAuthor(), avo.getPublisher(), avo.getBookName());
			}
			
			System.out.println(Constant.EL_XL);
			while(true) {
				System.out.print(">> 상세히 볼 도서번호 선택(뒤로가기 : 0) : ");
				inp = Pub.sc.nextInt();
				Pub.sc.nextLine();
				
				if(inp == 0) {
					System.out.println();
					if(id.equals(Constant.ADMIN_ID)) {
						new BookMain().regBookMenu();				
					} else {
						new Main().memberLibrary();
					}
					break;
				}else {
					for(AppVO avo : prpsList){
						if(avo.getAppId() == inp) {
							bookView(inp);
							break;
						}
					}
				
				}
			}
		}
	}
	
	public void bookView(int appId) {
		AppVO avo = adao.select(appId);
		System.out.println("==================== 신청 도서 상세 정보 ======================");
		System.out.println("신청도서번호 : " + avo.getAppId());
		System.out.println("제목 : " + avo.getBookName());
		System.out.println("저자명 : " + avo.getAuthor());
		System.out.println("출판사 : " + avo.getPublisher());
		System.out.println("신청일 : " + Pub.sdf.format(avo.getAppDate()));
		System.out.println("----------------------------------------------------------");
		if (Pub.id.equals(Constant.ADMIN_ID))
			System.out.println("  1.승인(등록)     2.거절     3.뒤로");
		else
			System.out.println("  3.뒤로");
		System.out.println("----------------------------------------------------------");
		while(true) {
			System.out.print(">> 선택 : ");
			inp = Pub.sc.nextInt();
			Pub.sc.nextLine();
			
			if(inp == 1) {
				if (Pub.id.equals(Constant.ADMIN_ID)) {
					regiBook(appId);						
				}
				else {
					System.out.println("잘못 입력하셨습니다.. 다시 입력해주세요.");
				}
				break;
			}else if(inp == 2) {
				if (Pub.id.equals(Constant.ADMIN_ID)){
					refuBook(appId);
				}
				else {
					System.out.println("잘못 입력하셨습니다.. 다시 입력해주세요.");
				}
				break;
			}else if(inp == 3) {
					prpsBookList(Pub.id);
				break;
			}else {
				System.out.println("잘못 입력하셨습니다.. 다시 입력해주세요.");
			}
		}
	}
	
	public void regiBook(int appId) {
		System.out.println();
		AppVO avo = adao.select(appId);
		BookVO bvo = new BookVO();
		String title = avo.getBookName();
		String auth = avo.getAuthor();
		String pub = avo.getPublisher();
		if (title.equals("") || auth.equals("") || pub.equals("")) {
			System.out.println(">> 입력이 없는 항목이 존재하여 등록이 취소되었습니다.");
		} else {
			bvo.setBookName(title);
			bvo.setAuthor(auth);
			bvo.setPublisher(pub);
			if(bdao.insert(bvo)) {
				System.out.println(">> 등록되었습니다.");
				adao.delete(appId);
			} else {
				System.out.println(">> 등록에 실패하였습니다.");
			}
		}
		prpsBookList(Pub.id);
	}
	
	public void refuBook(int appId) {
		System.out.println();
		AppVO avo = adao.select(appId);
		
		boolean result = adao.refuBook(avo);
		if(result == true) {
			System.out.println("거절되었습니다.");
		}else {
			System.out.println("오류가 발생하여, 목록으로 돌아갑니다.");
			prpsBookList(Pub.id);
		}
	}

	//USR_013 도서 신청 영
		public void appBook() {
			AppVO avo = new AppVO();
			System.out.println("============== 도서 신청 ==============");
			System.out.println("제목 : ");
			String title = Pub.sc.nextLine();
			System.out.println("저자명 : ");
			String author = Pub.sc.nextLine();
			System.out.println("출판사 : ");
			String publisher = Pub.sc.nextLine();
			System.out.println(Constant.EL_M);
			if(title.equals("") || author.equals("") || publisher.equals("")) {
			 System.out.println("입력이 없는 항목이 존재하여 신청을 취소 하였습니다.");
			}
			else {
				avo.setBookName(title);
				avo.setAuthor(author);
				avo.setPublisher(publisher);
				avo.setId(Pub.id);
				if(adao.insertApp(avo)) {
					System.out.println("도서가 신청되었습니다.");
				}else {
					System.out.println("도서 신청이 실패하였습니다.");
				}
			}
			new Main().memberLibrary(); //내서재로 이동
		}
		
		//도서 신청 현황 영
		public void appBookListMember(String name) {
			List<AppVO> appMyList = adao.selectMyApp(name);
			System.out.println("승인여부 번호 저자명 출판사 제목");
			for(AppVO avo : appMyList) {
				System.out.println(avo.getApproval() + avo.getAppId() + avo.getAuthor() + avo.getPublisher() + avo.getBookName());
			}
			System.out.println("취소를 원하시면 번호를 입력해주세요");
			System.out.println(">>입력 (나가기  '0' ) : "  );
			int input = Pub.sc.nextInt();
			if(input == 0) {
				new Main().memberLibrary();
			}
			else {
				AppVO avo = adao.select(input);
				if(avo != null) {
					if(adao.delete(input)) { //input번호의 신청취소
						System.out.println(input + "번호의 도서 신청되었습니다.");
					}
				} else {
					System.out.println(input + "번호의 도서가 없습니다.");
				}
				appBookListMember(name); //왜 밑줄뜨는지 모르겠음
			}
				
		}
}
