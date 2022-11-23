package proj.main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import proj.dao.MemberDAO;
import proj.dao.RentDAO;
import proj.util.Constant;
import proj.util.DBCon;
import proj.util.Pub;
import proj.vo.MemberVO;
import proj.vo.RentVO;
import proj.vo.SuggesVO;

public class Main {
	private int input;		//키보드 입력값 저장 
	private MemberDAO mdao = new MemberDAO();

	// GEN_001 메인 메뉴
		public void menu() {
			System.out.println(Constant.HD_MENU);
			System.out.println("  1.회원 가입   2.로그인   3.아이디 찾기");
			System.out.println("  4.비밀번호 찾기        5.시스템 종료");
			System.out.println(Constant.EL_S);
			System.out.print(Constant.SELECT);
			input = Pub.sc.nextInt();
			Pub.sc.nextLine();	
			
			switch (input) {
			case 1 :	join();		break; //회원가입 메뉴 실행
			case 2 :	login(); 	break; //로그인 메뉴 실행
			case 3 :	findId();//아이디 찾기
						break;
			case 4 :	findPw();//비밀번호 찾기
						break;
			case 5 :	System.out.println(">> 시스템을 종료합니다. ");	//5이면 시스템 종료
						System.exit(0);
						break;
			default:	//그외의 경우 '1~5을 입력해주세요' 출력
				System.out.println(">> 1 ~ 5 중에 하나를 입력해주세요");
			} 
		}

			// COM_001 시스템 종료
		// GEN_002 로그인
		public void login() {
			// 회원 메뉴
			System.out.println(Constant.HD_LOGIN);
			System.out.print("  아이디 : ");
			String id = Pub.sc.nextLine();
			
			System.out.print("  비밀번호 : ");
			String pw = Pub.sc.nextLine();
			System.out.println(Constant.EL_S);
			boolean result = mdao.loginChk(id, pw);	//로그인 체크 메소드 호출
			if(result) {
				Pub.id = id;
				new RentDAO().checkOverdue(Pub.id);
				if(id.equals("admin")) { //로그인 성공 시 - 관리자이면 adminMenu() 호출
					adminMenu();
				} else { 
					memberMenu(); // 그렇지 않으면 memberMenu() 호출
				}
			} else { //로그인 실패 시 - 
				System.out.println(">> 아이디 또는 비밀번호가 일치하지 않습니다.");
				System.out.println();//메인 메뉴 표시
				menu();
			}
		}
		// 관리자 메뉴
		public void adminMenu() {
			System.out.println(Constant.HD_ADMIN_MENU);
			System.out.println("  1.회원 관리   2. 도서관리   3. 건의사항");
			System.out.println("  4.공지사항    5.로그아웃");
			System.out.println(Constant.EL_S);
			System.out.print(Constant.SELECT);
			input = Pub.sc.nextInt();
			Pub.sc.nextLine();	

			switch (input) {
			case 1 :	memberList();		break; //회원목록 출력
			case 2 :	new BookMain().bookAdminMenu();			break;
			case 3 :	new SuggestionMain().suggesList(Pub.id);	break;
			case 4 :	new NoticeMain().noticeList();	break;
			case 5 :	System.out.println(">> 로그아웃합니다. ");	//5이면 시스템 종료
						logout();
						break;
			default:	//그외의 경우 '1~2 입력해주세요' 출력
				System.out.println(">> 1 ~ 5 중에 하나를 입력해주세요");
			} 
		}
		// GEN_003 회원 가입
		public void join() {
			MemberVO mvo = new MemberVO();
			System.out.println(Constant.HD_JOIN);
			System.out.print("  아이디 : ");
			String id = Pub.sc.nextLine();		mvo.setId(id);
			System.out.print("  비밀번호 : ");
			String pw = Pub.sc.nextLine();		mvo.setPw(pw);
			System.out.print("  이름 : ");
			String name = Pub.sc.nextLine();		mvo.setName(name);
			System.out.print("  주소 : ");
			String address = Pub.sc.nextLine();	mvo.setAddress(address);
			System.out.print("  전화 : ");
			String phone = Pub.sc.nextLine();		mvo.setPhone(phone);
			System.out.println(Constant.EL_S);
			boolean result = mdao.insertMember(mvo); //MemberDAO의 회원 가입 메소드를 호출하고
			if(result == true) { //결과를 넘겨받아 회원가입에 성공한 경우
				//lc.putMember(id, pw); //신규 회원 아이디와 비밀번호를 loginMap에 저장하는 메소드 호출
				
				System.out.println(">> 회원 가입이 완료되었습니다.");
				System.out.println(">> 로그인 후 이용해주세요.");
				System.out.println();
				menu();
			}
		}

		
		
		
		/**
		 * 회원 메서드
		 * @throws IOException 
		 */
		//USR_001 회원 메뉴
		public void memberMenu() {
			System.out.println(Constant.HD_MEMBER_MENU);
			System.out.println("    1.내 정보     2.내 서재     3.도서");
			System.out.println("    4.건의사항     5. 공지사항    6.로그아웃");
			System.out.println(Constant.EL_S);
			System.out.print(Constant.SELECT);
			input = Pub.sc.nextInt();
			Pub.sc.nextLine();	
			
			switch (input) {
			case 1 :	memberInfo(Pub.id);		break; //회원정보 출력
			case 2 :	memberLibrary();			break; //내 서재
			case 3 :	new BookMain().bookMemberMenu();		break; //도서
			case 4 :	new SuggestionMain().suggesList(Pub.id);	break;
			case 5 :	new NoticeMain().noticeList();	break;
			case 6 : 	logout();	break;
			default:	//그외의 경우 '1~2 입력해주세요' 출력
				System.out.println(">> 1 ~ 6 중에 하나를 입력해주세요");
			memberMenu();
			}
		}
		//COM_005 내서재
		public void memberLibrary() {
			while (true) {
				boolean loop = true;
				System.out.println(Constant.HD_MY_LIBRARY);
				System.out.println("  1.대여 정보  2.도서 예약 현황   3.신청 도서 현황");
				System.out.println("  4.내 건의사항       5. 뒤로");
				System.out.println(Constant.EL_S);
				System.out.print(Constant.SELECT);
				input = Pub.sc.nextInt();
				Pub.sc.nextLine();
				switch (input) {
				case 1 :	new BookManagement().borrowList(Pub.id, Constant.FLAG_MEMBER_LIB);		
							break; 
				case 2 :	new BookManagement().reserStatus(Pub.id);		
							break;
				case 3 :	new	BookManagement().prpsBookList(Pub.id);	
							break; //도서
				case 4 :	new SuggestionMain().mySuggesList(Pub.id);	
							break;
				case 5 :	memberMenu();
							break;
				default:	System.out.println(">> 1 ~ 5 중에 하나를 입력해주세요");
							loop = false;
				}
				if(!loop) break;
			}
		}
		// GEN_004 아이디 찾기
			public void findId() {
				System.out.println(Constant.HD_FIND_ID);
				System.out.print(">>  이름 : ");
				String name = Pub.sc.nextLine();
				System.out.print(">>  전화번호 : ");
				String phone= Pub.sc.nextLine();
				MemberVO mvo = mdao.select(name, phone);
				System.out.println(Constant.EL_S);
				if(mvo != null) {
					System.out.println(">> 회원님의 아이디 : " + mvo.getId());
				} else {
					System.out.println(">> 가입된 회원이 없습니다.");
				}
				System.out.println();
				menu();
				
			}
			// GEN_005 비밀번호 찾기
			public void findPw() {
				System.out.println(Constant.HD_FIND_PW);
				System.out.print(">>  아이디 : ");
				String id= Pub.sc.nextLine();
				System.out.print(">>  이름 : ");
				String name = Pub.sc.nextLine();
				System.out.print(">>  전화번호 : ");
				String phone= Pub.sc.nextLine();
				MemberVO mvo = mdao.select(id, name, phone);
				System.out.println(Constant.EL_S);
				if(mvo != null) {
					System.out.println(">> 입력 정보가 일치합니다. 비밀번호를 변경해주세요");
					changeFindPw(mvo);
				} else {
					System.out.println(">> 조건을 확인해주세요");
				}
				System.out.println();
				menu();		
			}
		//비밀번호 변경
		public void changePw(MemberVO mvo) {
			System.out.println(">> 비밀번호를 변경합니다.");
			System.out.print(">> 기존 비밀번호 : ");
			String oldPw = Pub.sc.nextLine();
			System.out.print(">> 신규 비밀번호 : ");
			String newPw = Pub.sc.nextLine();
			if (!mvo.getPw().equals(oldPw)) {
				System.out.println(">> 비밀번호를 확인해 주세요.");
			} else if (mvo.getPw().equals(newPw)) {
				System.out.println(">> 기존 비밀번호와 같은 비밀번호입니다.");
			} else {
				mvo.setPw(newPw);
				if(mdao.update(mvo)) {
					System.out.println(">> 비밀번호가 변경되었습니다.");
				} else {
					System.out.println(">> 비밀번호 변경에 실패하였습니다.");
				}
			}
		}
		//비밀번호 변경
		public void changeFindPw(MemberVO mvo) {
			System.out.println(">> 비밀번호를 변경합니다.");
			System.out.print(">> 신규 비밀번호 : ");
			String Pw = Pub.sc.nextLine();
			System.out.print(">> 비밀번호 확인 : ");
			String chkPw = Pub.sc.nextLine();
			if (!Pw.equals(chkPw)) {
				System.out.println(">> 입력한 비밀번호가 다릅니다.");
			} else if (mvo.getPw().equals(Pw)) {
				System.out.println(">> 기존 비밀번호와 같은 비밀번호입니다.");
			} else {
				mvo.setPw(Pw);
				if(mdao.update(mvo)) {
					System.out.println(">> 비밀번호가 변경되었습니다.");
				} else {
					System.out.println(">> 비밀번호 변경에 실패하였습니다.");
				}
			}
		}
		//USR_004 회원 탈퇴
		public void memberRemove() {
			System.out.print(">> 탈퇴하시려면 y를 입력해주세요. : ");
			String yesNo = Pub.sc.nextLine();	
			
			if(yesNo.equalsIgnoreCase("y")) {
				boolean result = mdao.delete(Pub.id);
				if(result == true) {
					System.out.println(">> 회원탈퇴가 완료되었습니다.");
					logout();
				}
			} else {
				System.out.println(">> 회원탈퇴가 취소되었습니다.");
			}
			System.out.println();
			logout();
		}
		
		
		/**
		 * 관리자  메서드
		 */
		//ADM_002회원관리
		public void memberList() {
			ArrayList<MemberVO> memberList = mdao.selectAllMember(); //MemberDAO 클래스의 selelctAllMeber() 메소드를 호출하여
			//전체 회원 목록을 넘겨 받는다
			if(memberList == null || memberList.size() < 1) { //만약에 회원 목록이 없다면
				System.out.println(">> 가입된 회원이 없습니다.");
			} else  { //그렇지 않다면
				System.out.println(Constant.HD_MEMBER_LIST);
				System.out.printf("%-8s\t%-8s\t%-20s\t%-2s\t%s\n","아이디", "이름","전화번호","연체여부", "주소");
				System.out.println(Constant.EL_XL);
				for (MemberVO mvo : memberList) {
					if(mvo.getId().equals(Constant.ADMIN_ID)) continue;
					System.out.printf("%-8s\t%-8s\t%-20s\t%-2s\t%s\n",
										mvo.getId(),
										mvo.getName(),
										mvo.getPhone(),
										(mvo.getOverdue().equals("N")==true?"연체없음":"연체중"),						   
										mvo.getAddress());
				}
				System.out.println(Constant.EL_XL);
			}
			System.out.print(">> 상세히 볼 아이디 입력 (뒤로 : 0) : ");
			String input = Pub.sc.nextLine();
			if(input.equals("0")) adminMenu();
			memberInfo(input);
		}
		public void memberInfo(String id) {
			while (true) {
				boolean loop = false;
				System.out.println(Constant.HD_MEMBER_INFO);
				MemberVO mvo = mdao.select(id);
				if (mvo != null) {
					System.out.println("   아이디 : \t" + mvo.getId());
					System.out.println("   비밀번호 : \t" + mvo.getPw());
					System.out.println("   이름 : \t" + mvo.getName());
					System.out.println("   전화번호 : \t" + mvo.getPhone());
					System.out.println("   주소 : \t" + mvo.getAddress());
					System.out.println("   가입날짜 : \t"
							+ Pub.sdf.format(mvo.getJoinDate()));
					System.out.println("   연체 여부 : \t" + (mvo.getOverdue().equals("N")?"연체없음":"연체중"));
					System.out.println(Constant.EL_L);
				}
				if(Pub.id.equals(Constant.ADMIN_ID)) {
					System.out.println("    1.회원 정보 수정  2.회원 삭제  3.회원 대여 정보 보기   4. 뒤로");
				} else {
					System.out.println("    1.내 정보 수정  2.회원 탈퇴  4.뒤로 ");
				}
				System.out.println(Constant.EL_L);
				System.out.print(Constant.SELECT);
				input = Pub.sc.nextInt();
				Pub.sc.nextLine();	
				switch (input) {
				case 1 :	if(Pub.id.equals(Constant.ADMIN_ID)) {
								modMemberAdmin(mvo.getId());		
							} else {
								modMember();
							}
							break; 
				case 2 :	memberDelete(id);			
							break; 
				case 3 : 	if(Pub.id.equals(Constant.ADMIN_ID)) {
								new BookManagement().borrowList(id, Constant.FLAG_MEMBER_LIB);
							} else {
								System.out.println(">> 1 ~ 4 중에 하나를 입력해주세요");
								loop = true;
							}
							break; 
				case 4 : 	if(Pub.id.equals(Constant.ADMIN_ID)) {
								adminMenu();
							} else {
								memberMenu();
							}
							break; 
				default:	//그외의 경우 '1~2 입력해주세요' 출력
					System.out.println(">> 1 ~ 4 중에 하나를 입력해주세요");
					loop = true;
				}
				if(!loop) break;
			}
		}
		//COM_002
		public void modMember() {
			MemberVO mvo = mdao.select(Pub.id);
			boolean loop = false;
			while(true) {
				System.out.println(Constant.HD_MOD_MY_INFO);
				if (mvo != null) {
					System.out.println("   이름 : \t" + mvo.getName());
					System.out.println("   전화번호 : \t" + mvo.getPhone());
					System.out.println("   주소 : \t" + mvo.getAddress());
				}
				System.out.println(Constant.EL_S);
				System.out.println("  1.비밀번호 변경   2.주소 변경  3.이름 변경");
				System.out.println("  4.전화번호 변경   5.저장     6.취소");
				System.out.println(Constant.EL_S);
				System.out.print(Constant.SELECT);
				int select = Pub.sc.nextInt();
				Pub.sc.nextLine();
				switch (select) {
					case 1:	System.out.println(">> 비밀번호를 변경합니다.");
							System.out.print(">> 기존 비밀번호 : ");
							String oldPw = Pub.sc.nextLine();
							System.out.print(">> 신규 비밀번호 : ");
							String newPw = Pub.sc.nextLine();
							if (!mvo.getPw().equals(oldPw)) {
								System.out.println("비밀번호를 확인해 주세요.");
							} else if (mvo.getPw().equals(newPw)) {
								System.out.println("기존 비밀번호와 같은 번호입니다.");
							} else {
								mvo.setPw(newPw);
								System.out.println("비밀번호가 변경되었습니다.");
							}
							break;
					case 2:	System.out.println(">> 주소를 변경합니다.");
							System.out.print(">> 신규 주소 : ");
							String newAddress = Pub.sc.nextLine();
							if (newAddress.equals(mvo.getAddress())) {
								System.out.println("기존 주소와 동일한 주소입니다.");
							} else {
								mvo.setAddress(newAddress);
								System.out.println("주소가 변경되었습니다.");
							}
							break;
					case 3:	System.out.println(">> 이름을 변경합니다.");
							System.out.print(">> 신규 이름 : ");
							String newName = Pub.sc.nextLine();
							if (newName.equals(mvo.getName())) {
								System.out.println("기존 이름과 동일한 이름입니다.");
							} else {
								mvo.setName(newName);
								System.out.println("이름이 변경되었습니다.");
							}
							break;
					case 4:	System.out.println(">> 전화번호를 변경합니다.");
							System.out.print(">> 신규 전화번호 : ");
							String newPhone = Pub.sc.nextLine();
							if (newPhone.equals(mvo.getPhone())) {
								System.out.println("기존 전화번호와 동일한 전화번호입니다.");
							} else {
								mvo.setPhone(newPhone);
								System.out.println("전화번호가 변경되었습니다.");
							}
							break;
					case 5:	mdao.update(mvo);
							System.out.println("회원 정보가 변경되었습니다.");
							loop = true;
							break;
					case 6:	System.out.println("취소되었습니다.");
							loop = true;
							break;
					default:System.out.println(">> 1 ~ 5 중에 하나만 입력해 주세요.");
							break;
				}
				if(loop) break;
			}
			memberInfo(Pub.id);
		}
		public void modMemberAdmin(String id) {
			System.out.println(Constant.HD_MOD_MEMBER_INFO);
			System.out.print(">> 이름 : \t");
			String na = Pub.sc.nextLine();
			System.out.print(">> 비밀번호 : \t");
			String pw = Pub.sc.nextLine();
			System.out.print(">> 전화번호 : \t");
			String ph = Pub.sc.nextLine();
			System.out.print(">> 주소 : \t");
			String ad = Pub.sc.nextLine();
			System.out.println(Constant.EL_S);
			if(na.equals("") || pw.equals("") || ph.equals("") || ad.equals("")) {
				System.out.println(">> 입력이 없는 항목이 있어 취소되었습니다.");
			} else {
				MemberVO mvo = new MemberVO();
				mvo.setName(na);
				mvo.setPw(pw);
				mvo.setPhone(ph);
				mvo.setAddress(ad);
				mvo.setId(id);
				if(mdao.update(mvo)) {
					System.out.println(">> 수정되었습니다.");
				} else {
					System.out.println(">> 수정이 실패했습니다.");
				}
			}
			adminMenu();
		}
		
		//ADM_004회원삭제
		public void memberDelete(String id) {
			if(Pub.id.equals(Constant.ADMIN_ID)) {
				System.out.println(Constant.HD_DEL_MEMBER);
			} else {
				System.out.println(Constant.HD_DEL_MY);
			}
			System.out.print(">> 삭제하시려면 y를 입력해주세요. : ");
			String yesNo = Pub.sc.nextLine();	
			
			if(yesNo.equalsIgnoreCase("y")) {
				boolean result = mdao.delete(id);
				if(result == true) {
					System.out.println(">> 회원삭제가 완료되었습니다.");
					if (Pub.id.equals(Constant.ADMIN_ID))
						memberList();
					else
						logout();
				}
			} else {
				System.out.println(">> 회원삭제가 취소되었습니다.");
				if (Pub.id.equals(Constant.ADMIN_ID))
					memberList();
				else
					memberInfo(Pub.id);
			}
			System.out.println();
			adminMenu();
			}

		//ADM_001관리자 메뉴
		/**
		 * 공용 메서드
		 */
		public void logout() {
			System.out.println(Constant.HD_LOGOUT);
			Pub.id = "";
			System.out.println(">> 로그아웃이 완료되었습니다. ");
			menu();	
		}
		public static void main(String[] args) {
			DBCon.getConnection();
			new Main().menu();
		}

}
