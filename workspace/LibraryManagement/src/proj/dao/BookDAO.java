package proj.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import proj.vo.AppVO;
import proj.vo.BookVO;


public class BookDAO {
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<BookVO> list;
	private BookVO bvo;
	
	// 전체 도서 목록
	public ArrayList<BookVO> selectAllBook() {
		return null;
	}
	// - 도서 검색
	//도서명검색
	public ArrayList<BookVO> selectName(String name) {
		return null;
	}
	//저자명검색
	public ArrayList<BookVO> selectAuthor(String aut) {
		return null;
	}
	//출판사검색
	public ArrayList<BookVO> selectPublisher(String pub) {
		return null;
	}
	//도서 상세보기
	public BookVO select(int bookid) {
		return null;
	}
	
	// 도서 등록
	public boolean insert(BookVO bvo) {
		return false;
	}
	
	// 도서 수정 
	public boolean update(BookVO bvo) {
		return false;
	}	
	
	// 도서 삭제 
	public boolean delete (int Bookid) {
		return false;
	}
	
	
}
