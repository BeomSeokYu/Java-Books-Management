package proj.main;

import java.awt.Container;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import proj.util.Pub;

import proj.dao.SuggesDAO;
import proj.util.Constant;
import proj.util.DBCon;
import proj.vo.SuggesVO;

public class SuggestionMain {
	private SuggesDAO sdao = new SuggesDAO();

//	/* 공통 */
//	public static void main(String[] a) {
//		Pub.id = "admin";
//		DBCon.getConnection();
//		SuggesVO svo = new SuggesVO();
//		svo.setId(Pub.id);
//		new SuggestionMain().suggesList(svo.getId());
//	}

	// COM_009 건의 사항 목록 (전체 건의사항 가져오기) done
	public void suggesList(String id) {
		List<SuggesVO> suggesList = sdao.selectAllSugges();

		System.out.println(Constant.HD_SUG_LIST);

		if (suggesList == null || suggesList.size() < 1) {
			System.out.println(">> 등록된 건의사항이 없습니다.");
			if (Pub.id.equalsIgnoreCase("admin")) {
				new Main().adminMenu();
			} else {
				System.out.println("등록  'n' 뒤로가기 '0'");
				System.out.print(">> 선택 : ");
				String inpu = Pub.sc.nextLine();
				if (inpu.equalsIgnoreCase("n")) {
					regSugges();
				} else if (Integer.parseInt(inpu) == 0) {
					new Main().memberMenu();
				}
			}
		} else {
			System.out.println();
			System.out.printf("%-4s\t%-10s\t%-2s\t%s\n", "글번호", "작성자", "관리자확인", "제목");
			System.out.println(Constant.EL_S);
			for (SuggesVO s : suggesList) {
				System.out.printf("%-4d\t%-10s\t%-2s\t%s\n", s.getSuggesId(), s.getId(), s.getChecked(), s.getTitle());
			}
			System.out.println(Constant.EL_S);
			if (Pub.id.equals("admin")) {
				System.out.print(">> 상세히 볼 글번호 선택 (뒤로가기 0) : ");
				String input = Pub.sc.nextLine();
				if (input.matches("^[0-9]+$")) { // input에 숫자만 등장하는지 확인
					int inputInt = Integer.parseInt(input); // String (input) -> int (input)로 변경

					if (inputInt == 0) { // 뒤로가기
						new Main().adminMenu(); // 이렇게 하는게 맞나 모르겠음,,. : 클래스간 이동이라 이렇게 해야되는게 맞는거 같네요
					} else {
						SuggesVO ssvo = sdao.select(inputInt);
						if (ssvo != null) {
							suggesInfo(ssvo, inputInt, Constant.FLAG_MEMBER_SUG); // 상세히 볼 글로 선택
						} else {
							System.out.println(">> 올바른 번호를 입력해주세요");
							suggesList(ssvo.getId());
							System.out.println(Constant.EL_S);
						}
					}
				}
			} else {
				System.out.println(">> 상세히 볼 글번호 선택 (등록 n 메뉴 0) : ");
				System.out.print(">> 선택 : ");
				String input = Pub.sc.nextLine();

				if (input.matches("^[0-9]+$")) { // input에 숫자만 등장하는지 확인
					int inputInt = Integer.parseInt(input); // String (input) -> int (input)로 변경

					if (inputInt == 0) { // 뒤로가기
						new Main().memberMenu();
					} else {
						SuggesVO asvo = sdao.select(inputInt);
						if (asvo != null) {
							suggesInfo(asvo, inputInt, Constant.FLAG_MEMBER_SUG); // 상세히 볼 글로 선택
						} else {
							System.out.println(">> 올바른 번호를 입력해주세요");
							suggesList(asvo.getId());
							System.out.println(Constant.EL_S);
						}
					}
				} else if (input.equalsIgnoreCase("n")) { // 만약에 n이면 등록
					regSugges();
				} else {
					System.out.println(">> 올바른 형식의 문자를 입력해주세요");
				}

			}

		}

	}

	// USR_008 내 건의 사항 목록
	public void mySuggesList(String id) {
		List<SuggesVO> suggesList = sdao.selectMySugges(id);
		System.out.println(Constant.HD_SUG_MYLIST);
		if (suggesList == null || suggesList.size() < 1) {
			System.out.println(Constant.EL_S);
			System.out.println(">> 등록된 건의사항이 없습니다.");
		} else {
			System.out.printf("%-4s\t%-10s\t%-2s\t%s\n", "글번호", "작성자", "관리자확인", "제목");
			System.out.println(Constant.EL_S);
			for (SuggesVO a : suggesList) {
				System.out.printf("%-4d\t%-10s\t%-2s\t%s\n", a.getSuggesId(), a.getId(), a.getChecked(), a.getTitle());
			}
			System.out.println(Constant.EL_S);
			System.out.println("상세히 볼 글 선택 (뒤로가기 : 0)");
			int input = Pub.sc.nextInt();
			Pub.sc.nextLine();
			if (input == 0) {
				new Main().memberLibrary(); // 내 서재로 이동
			} else if (sdao.select(input) != null) {
				suggesInfo(sdao.select(input), input, Constant.FLAG_MEMBER_LIB);
			} else {
				System.out.println("다시 입력해주세요!");
				mySuggesList(id);
			}
		}
	}

	// COM_010 건의 사항 상세 보기
	public void suggesInfo(SuggesVO svo, int suggesId, int from) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일 hh:mm:ss");
		if (svo != null) {
			System.out.println(Constant.HD_SUG_INFO);
			System.out.println("   글번호 : \t" + svo.getSuggesId());
			System.out.println("   제목 : \t" + svo.getTitle());
			System.out.println("   내용 : \t" + svo.getContent());
			System.out.println("   등록일 : \t" + sdf.format(svo.getRegDate()));
			System.out.print("   수정일 : \t");
			if (svo.getModDate() == null) {
				System.out.println("없음");
			} else {
				System.out.println(sdf.format(svo.getModDate()));
			}
			System.out.print("   관리자 확인 : \t");
			if (svo.getChecked().equalsIgnoreCase("n")) {
				System.out.println("미확인");
			} else {
				System.out.println("확인");
			}
			System.out.print("   댓글 : \t");
			if (svo.getReply() != null) {
				System.out.println(svo.getReply());
			} else {
				System.out.println("없음");
			}
			System.out.println(Constant.EL_S);
			if ((Pub.id.equals("admin"))) { // 지금 로그인된 사람과 작성자가 같은거 확인,,,(?)
				/**
				 * Pub.id가 현재 로그인 한 사람입니다.
				 */
				System.out.println(" 1.관리자 확인  2.댓글달기  3.댓글수정  4.삭제  5.뒤로");
				System.out.println(Constant.EL_S);
				System.out.print(">> 선택 :");
				String input = Pub.sc.nextLine();
				String num = "^[0-9]+$";
				if (input.matches(num)) {
					switch (Integer.parseInt(input)) {
					case 1:
						checkedSugges(svo);
						break;
					case 2:
						regSuggesReply(svo);
						break;
					case 3:
						modSuggesReply(svo);
						break;
					case 4:
						removeSugges(svo, svo.getSuggesId());
						break;
					case 5:
						if(from == Constant.FLAG_MEMBER_LIB)
							mySuggesList(svo.getId());
						else
							suggesList(svo.getId());
						break;
					default:
						System.out.println("1~4 중의 숫자를 입력해주세요");
						System.out.println(Constant.EL_S);
						suggesInfo(svo, svo.getSuggesId(), from);
						break;
					}
				} else {
					System.out.println(">> 올바른 형식의 문자를 입력해주세요");
					suggesInfo(svo, svo.getSuggesId(), from);
					System.out.println(Constant.EL_S);

				}

			} else if (Pub.id.equals(svo.getId())) {
				System.out.println("1.수정   2.삭제   3.뒤로");
				System.out.println(Constant.EL_S);
				System.out.print(">> 선택 :");
				String input = Pub.sc.nextLine();
				String num = "^[0-9]+$";
				if (input.matches(num)) {
					switch (Integer.parseInt(input)) {
					case 1:
						modSuggesMember(svo);
						break;
					case 2:
						removeSugges(svo, svo.getSuggesId());
						break;
					case 3:
						suggesList(svo.getId());
						break;
					default:
						System.out.println("1~3 중의 숫자를 입력해주세요");
						System.out.println(Constant.EL_S);
						suggesInfo(svo, svo.getSuggesId(), from);
						break;
					}
				} else {
					System.out.println(">> 올바른 형식의 문자를 입력해주세요");
					suggesInfo(svo, svo.getSuggesId(), from);
					System.out.println(Constant.EL_S);

				}
			} else {
				System.out.println("1.뒤로");
				System.out.println(Constant.EL_S);
				System.out.print(">> 선택 :");
				String input = Pub.sc.nextLine();
				String num = "^[0-9]+$";
				if (input.matches(num)) {
					switch (Integer.parseInt(input)) {
					case 1:
						suggesList(svo.getId());
						break;
					default:
						System.out.println("알맞은 숫자를 입력해주세요");
						System.out.println(Constant.EL_S);
						suggesInfo(svo, svo.getSuggesId(), from);
						break;
					}
				} else {
					System.out.println(">> 올바른 형식의 문자를 입력해주세요");
					suggesInfo(svo, svo.getSuggesId(), from);
					System.out.println(Constant.EL_S);

				}
			}

		}

		// --건의사항상세정보--
//		글번호 : 2
//		제목 : 신청 도서 처리가 늦다
//		내용 : ddddd
//		
//		등록일 : 2022.11.13
//		수정일 : 없음
//		관리자확인 : 미확인
//		-- 관리자 댓글
//		제목 : 없음
//		내용 : 없음
//		등록일 : 없음
//		수정일 : 없음
//		
//		일반회원 
//		---------
//		1.수정  2.삭제 3.뒤로 <- 본인
//		1. 뒤로 <- 다른회원
//		--------
//		>> 선택 :
//		
//		관리자
//		-------
//		1.삭제 2. 뒤로
//		--------
//		>> 선택 : 
	}

	/* 관리자 */

	// 댓글 달기
	public void regSuggesReply(SuggesVO svo) {
		if (svo.getReply() != null) {
			System.out.println("이전에 작성한 댓글이 존재합니다.");
			suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_SUG);
		} else {
			System.out.println(Constant.HD_SUG_REPLY_REGIST);
			System.out.print("  내용  :  ");
			String comment = Pub.sc.nextLine();
			svo.setReply(comment);
			boolean result = sdao.update(svo);
			if (comment.equals("")) {
				System.out.println(">> 입력이 없는 항목이 존재하여 등록을 취소합니다.");
			} else {
				if (result) {
					System.out.println("댓글이 등록되었습니다.");
					suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_SUG);
				} else {
					System.out.println("댓글 등록이 실패하였습니다");
					suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_SUG);
				}
			}
		}

	}

	// 댓글 수정
	public void modSuggesReply(SuggesVO svo) {
		System.out.println(Constant.HD_SUG_REPLY_REGIST);
		System.out.println("기존 내용  :  " + svo.getReply());
		System.out.print("수정할 내용 : ");
		String comment = Pub.sc.nextLine();
		svo.setReply(comment);
		boolean result = sdao.update(svo);
		if (comment.equals("")) {
			System.out.println(">> 입력이 없는 항목이 존재하여 수정을 취소합니다.");
		} else {
			if (result) {
				System.out.println("댓글이 수정되었습니다.");
				suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_SUG);
			} else {
				System.out.println("댓글 수정이 실패하였습니다");
				suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_SUG);
			}
		}
	}

	// 관리자 확인
	public void checkedSugges(SuggesVO svo) {
		System.out.println(Constant.HD_SUG_REPLY_CHECK);
		System.out.println("1. 건의사항 확인");
		System.out.println("2. 뒤로가기");
		System.out.print(">> 입력 : ");
		String input = Pub.sc.nextLine();
		if (input.matches("^[0-9]+$")) { // input에 숫자만 등장하는지 확인
			int inputInt = Integer.parseInt(input); // String (input) -> int (input)로 변경
			switch (inputInt) {
			case 1:
				if (svo.getChecked().equalsIgnoreCase("y")) {
					System.out.println(">> 이미 확인한 건의사항입니다");
				} else {
					boolean res = sdao.update(svo);
					if (res) {
						System.out.println(">> 건의사항 확인이 완료되었습니다");
						suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_SUG);
					} else {
						System.out.println(">> 건의사항 확인이 실패하였습니다.");
						suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_SUG);
					}
				}
				break;
			case 2:
				suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_SUG);
				break;
			}
		} else {
			System.out.println(">> 올바른 형식의 문자를 입력해주세요");
		}

	}

	/* 유저 */

	// USR_015 건의 사항 등록
	public void regSugges() {
		SuggesVO svo = new SuggesVO();
		System.out.println(Constant.HD_SUG_REGIST);
		System.out.print("  제목  : ");
		String title = Pub.sc.nextLine();
		System.out.print("  내용  : ");
		String content = Pub.sc.nextLine();
		if (title.equals("") || content.equals("")) {
			System.out.println(">> 입력이 없는 항목이 존재하여 등록을 취소합니다.");
		} else {
			svo.setTitle(title);
			svo.setContent(content);
			svo.setId(Pub.id);
			if (sdao.insertSugges(svo)) {
				System.out.println("건의사항이 등록되었습니다.");
			} else {
				System.out.println("건의사항 등록이 실패하였습니다.");
			}
		}
		suggesList(svo.getId());
	}

	// USR_009 건의 사항 수정
	public void modSuggesMember(SuggesVO svo) {

		System.out.println(Constant.HD_SUG_MOD);
		System.out.println("  원래제목  :  " + svo.getTitle()); // 가져온거 수정 가능한지 ?
		System.out.print("  제목  :  ");
		String title = Pub.sc.nextLine();
		System.out.println("원래내용 : " + svo.getContent()); // 가져온거 수정 가능한지?
		System.out.print("  내용 :  ");
		String content = Pub.sc.nextLine();
		// svo.ModDate(sdf.) = svo.setRegdate; //현재 시간 받아와서 등록시간 수정하기
		if (title.equals("") || content.equals("")) {
			System.out.println(">> 입력이 없는 항목이 존재하여 수정을 취소합니다.");
			suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_LIB); // 비어있으면 다시 건의사항 상세보기로 돌아가기 (매개변수에 어떤거,,,?)
		} else {
			System.out.println("건의사항을 수정하시겠습니까? (y/n)");
			String yesNo = Pub.sc.nextLine();
			if (yesNo.equalsIgnoreCase("y")) {
				svo.setTitle(title);
				svo.setContent(content);
				boolean result = sdao.update(svo);
				if (result) {
					System.out.println("건의사항이 수정되었습니다.");
					suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_LIB);
				} else {
					System.out.println("건의사항 수정 실패");
				}
			} else if (yesNo.equalsIgnoreCase("n")) {// y말고 입력했을때 건의사항 목록으로 돌아가기
				suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_LIB);
			} else {
				System.out.println(">> y나 n을 입력해주세요");
				suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_LIB);
			}
		}
	}

	// COM_011 건의사항 삭제
	public void removeSugges(SuggesVO svo, int id) {
		System.out.println(Constant.HD_SUG_DELETE);
		System.out.print(">> 정말로 삭제하시겠습니가? (y/n) : ");
		String yesNo = Pub.sc.nextLine();

		if (yesNo.equalsIgnoreCase("y")) {
			if (Pub.id.equals(Constant.ADMIN_ID)) {
				boolean result = sdao.delete(id);
				if (result) {
					System.out.println(">> 삭제되었습니다.");
				} else {
					System.out.println(">> 삭제 실패");
				}
			} else if (Pub.id.equals(svo.getId())) {
				boolean result = sdao.delete(id);
				if (result) {
					System.out.println(">> 삭제되었습니다.");
				} else {
					System.out.println(">> 삭제 실패");
				}
			} else {
				System.out.println(">> 자신이 작성한 건의사항만 삭제가능합니다.");
			}

			if (Pub.id.equals(Constant.ADMIN_ID)) { // 관리자이면 건의사항목록
				suggesList(svo.getId());
			} else if (Pub.id.equals(svo.getId())) { // 일반회원 건의사항 메뉴
				suggesList(svo.getId());
			} else {
				suggesList(svo.getId());
			}
		} else if (yesNo.equalsIgnoreCase("n")) {// y말고 입력했을때 건의사항 목록으로 돌아가기
			if (Pub.id.equals(Constant.ADMIN_ID)) { // 관리자이면 건의사항목록
				suggesList(svo.getId());
			} else if (Pub.id.equals(svo.getId())) { // 일반회원 건의사항 메뉴
				mySuggesList(svo.getId()); // String 넣어줘야되는데 뭐넣어줘야될지,,,
			} else {
				suggesList(svo.getId());
			}
		} else {
			System.out.println(">> y나 n을 입력해주세요");
			if(Pub.id == Constant.ADMIN_ID)
				suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_SUG);
			else
				suggesInfo(svo, svo.getSuggesId(), Constant.FLAG_MEMBER_LIB);
		}

	}

}
