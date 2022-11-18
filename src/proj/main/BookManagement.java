package proj.main;

import proj.dao.AppDAO;
import proj.dao.RentDAO;
import proj.dao.ReserDAO;

public class BookManagement {
	private AppDAO adao;
	private RentDAO renDao;
	private ReserDAO resDao;
	
	// 대여 실행
	public void borrow(int id) {
		
	};
	
	// 대여목록 (COM_003)
	public void borrowList() {
		
	};
	
	// 대여정보 상세보기 (COM_004)
	public void borrowDetail(int id) {
		
	};

	// 반납기한 연장 
	public void borrowExtend(int id) {

	};
	
	// 도서 예약 실행
	public void reserve(int id) {
		
	}
	
	// 도서 예약현황 (USR_006)
	public void reserStatus(int id) {
		
	}
	
	// 도서 예약취소
	public void reserCancel(int id) {

	}
	
	// 도서 반납 (USR_014)
	public void returnBook(int id) {
		
	}
	
	//USR_013 도서 신청
	public void appBook() {
		
	}
	
	//도서 신청 등록 또는 거절
	public void resAppBook(int id) {
		
	}
	
	//도서 신청 목록
	public void appBookListMember(String name) {
		
	}
	
	//도서 신청 목록
	public void appBookListAdmin() {
		
	}
}
