package proj.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import proj.*;
import proj.dao.NoticeDAO;
import proj.util.Constant;
import proj.util.DBCon;
import proj.util.Pub;
import proj.vo.NoticeVO;

public class NoticeMain {
	private int input;		//키보드 입력값 저장 
	private NoticeDAO ndao = new NoticeDAO();
	
	/*
	 * public NoticeMain(Scanner scan, String id) { this.scan = scan; this.id = id;
	 * }
	 */

	// COM_007 공지 사항 목록
	public void noticeList() {
		System.out.println(Constant.HD_NOTICE_LIST);
		
		ArrayList<NoticeVO> nvoList =  ndao.selectAllNotice();
		
		if(nvoList == null || nvoList.size() < 1) {
			while(true) {
				System.out.println("----등록된 공지가 없습니다----");
				if(Pub.id.equals("admin")) {
					System.out.println("1.뒤로가기    -1.공지등록");
				} else {
					System.out.println("1.뒤로가기");
				}
				System.out.print(Constant.SELECT);
				input = Pub.sc.nextInt();
				Pub.sc.nextLine();
				
				if(input == 1) {
					// 뒤로 가기
					break;
				}else if(input == -1){
					if(Pub.id.equals("admin")) createNotice();
				}else {
					System.out.println("번호를 잘못 입력하셨습니다. 다시 입력해주세요.");
				}
			}
		}else {
			System.out.printf("%-5s\t%s\n","글번호", "제목");
			System.out.println(Constant.EL_M);
			for(NoticeVO nvo : nvoList) {
				System.out.printf("%-5s\t%s\n", nvo.getNoticeId(), nvo.getTitle() );
			}
			
			System.out.println(Constant.EL_M);
			if(Pub.id.equals("admin")) {
				while(true) {
					System.out.print(">>>>상세히 볼 글 번호 선택(뒤로가기 : 0 / 생성 : -1) : ");
					input = Pub.sc.nextInt();
					Pub.sc.nextLine();
					
					if(input == 0) {
						if(Pub.id.equals(Constant.ADMIN_ID)) {
							new Main().adminMenu();
						} else {
							new Main().memberMenu();
						}
					}else if(input == -1){   // 생성
						if(Pub.id.equals(Constant.ADMIN_ID)) createNotice();
						break;
					}else {	
						for(NoticeVO nvo : nvoList) {
							if(nvo.getNoticeId() == input) {
								noticeView(input);
								break;
							}
						}
					}
				}
			}else {
				while(true) {
					System.out.print(">>>>상세히 볼 글 번호 선택(뒤로가기 : 0) : ");
					input = Pub.sc.nextInt();
					Pub.sc.nextLine();
					
					if(input == 0) {
						if(Pub.id.equals(Constant.ADMIN_ID)) {
							new Main().adminMenu();
						} else {
							new Main().memberMenu();
						}
						break;
					}else {	
						noticeView(input);
				
					}
				}
			}
			
		}
	
	}
	
	public void noticeView(int noticeNo) {
		NoticeVO nvo = ndao.select(noticeNo);
		System.out.println();
		System.out.println("글 번호 : " + nvo.getNoticeId());
		System.out.println("제목 : " + nvo.getTitle());
		System.out.println("내용 : " + nvo.getContent());
		System.out.println();
		System.out.println();
		if(nvo.getRegDate() != null) System.out.println("등록일 : " + Pub.sdf.format(nvo.getRegDate()));
		if(nvo.getModDate() != null) System.out.println("수정일 : " + Pub.sdf.format(nvo.getModDate()));
		System.out.println();
		System.out.println();
		
		if(Pub.id.equals("admin")) {
			System.out.println("------------------------------");
			System.out.println("1.수정    2.삭제    3.뒤로");
			System.out.println("------------------------------");
			while(true) {
				System.out.print(">> 선택 : ");
				input = Pub.sc.nextInt();
				Pub.sc.nextLine();
				
				if(input == 1) {
					modNotice(noticeNo);
					break;
				}else if(input == 2) {
					removeNotice(noticeNo);
					break;
				}else if(input == 3) {
					noticeList();
					break;
				}else {
					System.out.println("잘못 입력하셨습니다.. 다시 입력해주세요.");
				}
			}
			
		}else {
			System.out.println("------------------------------");
			System.out.println("1.뒤로");
			System.out.println("------------------------------");
			System.out.print(">> 선택 : ");
			input = Pub.sc.nextInt();
			Pub.sc.nextLine();
			
			switch (input) {
			case 1:
				// 뒤로가기
				noticeList();
				break;

			default:
				System.out.println("번호를 잘못 입력하셨습니다. 다시 입력해주세요.");
			}
		}
		
	}
	
	public void createNotice() {
		NoticeVO nvo = new NoticeVO();
		
		System.out.println("-----------공지사항 글 작성----------------");
		System.out.print("제목 : ");
		String notiTitle = Pub.sc.nextLine();
		nvo.setTitle(notiTitle);
		System.out.print("게시글 내용 : ");
		String notiContent = Pub.sc.nextLine();
		nvo.setContent(notiContent);
		
		/*
		 * System.out.println("---------------------------");
		 * System.out.println("1.작성완료    2.뒤로가기");
		 */
		
		while(true) {
			System.out.print(">> 선택 (1: 작성완료, 2: 뒤로가기) : ");
			input = Pub.sc.nextInt();
			Pub.sc.nextLine();
			
			if(input == 1) {
				boolean result = ndao.insertNotice(nvo);
				if(result == true) {
					System.out.println("글 작성이 완료되었습니다.");
					System.out.println();
					noticeList();
				}
			}else if(input == 2) {
				noticeList();
			}else {
				System.out.println("잘못 입력하셨습니다.. 다시 입력해주세요.");
			}
		}
		
	}
	
	
	// ADM_015 공지사항 수정
	public void modNotice(int noticeNo) {
		System.out.println();
		NoticeVO nvo = ndao.select(noticeNo);
		
		System.out.println("----------------------공지사항 수정--------------------------");
		System.out.print("제목 : ");
		nvo.setTitle(Pub.sc.nextLine());
		System.out.print("내용 : ");
		nvo.setContent(Pub.sc.nextLine());
		System.out.println("--------------------------------------------------------");
		
		boolean result = ndao.update(nvo);
		if(result == true) {
			System.out.println("설문이 수정되었습니다.");
			noticeList();
		}else {
			System.out.println("오류가 발생하여, 리스트 목록으로 이동합니다.");		
			noticeList();
		}
		
	}
	// ADM_016 공지사항 삭제
	public void removeNotice(int noticeNo) {
		System.out.println("--------------------공지사항 삭제--------------------------");
		System.out.print(">> 정말로 삭제하시겠습니까? (Y/n) :");
		String yesNo = Pub.sc.nextLine();
		
		if(yesNo.equalsIgnoreCase("Y")) {
			boolean result = ndao.delete(noticeNo);
			if(result == true) {
				System.out.println(">> 공지가 삭제 되었습니다.");
				noticeList();
			}
		}else {
			System.out.print(">> 공지 삭제가 취소되었습니다.");
			noticeList();
		}
	}
	
	public static void main(String[] args) {
		new NoticeMain().noticeList();
	}
	
}
