package proj.dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proj.util.DBCon;
import proj.vo.BookVO;


public class BookDAO {
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<BookVO> list;
	private BookVO bvo;
	
	/**
	 * 전체 도서 목록
	 * @return : List<BookVO>
	 */
	public List<BookVO> selectAllBook() {
		sql = "SELECT * FROM t_book";
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();
			while(rs.next()) {
				bvo = new BookVO();
				bvo.setBookId(rs.getInt(1));
				bvo.setBookName(rs.getString(2));
				bvo.setAuthor(rs.getString(3));
				bvo.setPublisher(rs.getString(4));
				bvo.setRegDate(rs.getDate(5));
				bvo.setReserNum(rs.getInt(6));
				bvo.setRentAvail(rs.getString(7));
				list.add(bvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return list;
	}
	// - 도서 검색
	/**
	 * 도서명검색
	 * @param name : String
	 * @return List<BookVO>
	 */
	public List<BookVO> selectName(String name) {
		sql = "SELECT * FROM t_book "
				+ "WHERE book_name LIKE '%' || ? || '%'";
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();
			while(rs.next()) {
				bvo = new BookVO();
				bvo.setBookId(rs.getInt(1));
				bvo.setBookName(rs.getString(2));
				bvo.setAuthor(rs.getString(3));
				bvo.setPublisher(rs.getString(4));
				bvo.setRegDate(rs.getDate(5));
				bvo.setReserNum(rs.getInt(6));
				bvo.setRentAvail(rs.getString(7));
				list.add(bvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return list;
	}
	
	/**
	 * 저자명검색
	 * @param aut : String
	 * @return List<BookVO>
	 */
	public List<BookVO> selectAuthor(String aut) {
		sql = "SELECT * FROM t_book WHERE author LIKE '%' || ? || '%'";
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, aut);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();
			while(rs.next()) {
				bvo = new BookVO();
				bvo.setBookId(rs.getInt(1));
				bvo.setBookName(rs.getString(2));
				bvo.setAuthor(rs.getString(3));
				bvo.setPublisher(rs.getString(4));
				bvo.setRegDate(rs.getDate(5));
				bvo.setReserNum(rs.getInt(6));
				bvo.setRentAvail(rs.getString(7));
				list.add(bvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return list;
	}
	
	/**
	 * 출판사검색
	 * @param pub : String
	 * @return List<BookVO>
	 */
	public List<BookVO> selectPublisher(String pub) {
		sql = "SELECT * FROM t_book WHERE publisher LIKE '%' || ? || '%'";
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, pub);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();
			while(rs.next()) {
				bvo = new BookVO();
				bvo.setBookId(rs.getInt(1));
				bvo.setBookName(rs.getString(2));
				bvo.setAuthor(rs.getString(3));
				bvo.setPublisher(rs.getString(4));
				bvo.setRegDate(rs.getDate(5));
				bvo.setReserNum(rs.getInt(6));
				bvo.setRentAvail(rs.getString(7));
				list.add(bvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return list;
	}
	
	/**
	 * 도서 상세보기
	 * @param bookid : int
	 * @return BookVO
	 */
	public BookVO select(int bookid) {
		sql = "SELECT * FROM t_book WHERE book_id=?";
		bvo = new BookVO();
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setInt(1, bookid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				bvo.setBookId(rs.getInt(1));
				bvo.setBookName(rs.getString(2));
				bvo.setAuthor(rs.getString(3));
				bvo.setPublisher(rs.getString(4));
				bvo.setRegDate(rs.getDate(5));
				bvo.setReserNum(rs.getInt(6));
				bvo.setRentAvail(rs.getString(7));
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return bvo;
	}
	
	/**
	 * 도서 등록
	 * @param bvo : BookVO
	 * @return boolean
	 */
	public boolean insert(BookVO bvo) {
		sql = "INSERT INTO t_book (book_id, Book_name, author, publisher)"
				+ "VALUES (SEQ_T_BOOK_BOOK_ID.NEXTVAL, ?, ?, ?)";
		boolean result = false;
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, bvo.getBookName());
			pstmt.setString(2, bvo.getAuthor());
			pstmt.setString(3, bvo.getPublisher());
			if(pstmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return result;
	}
	/**
	 * 도서 수정 
	 * @param bvo : BookVO
	 * @return boolean
	 */
	public boolean update(BookVO bvo) {
		sql = "UPDATE t_book SET book_id=SEQ_T_BOOK_BOOK_ID.NEXTVAL, "
				+ "Book_name=?, author=?, publisher=? WHERE book_id=?";
		boolean result = false;
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, bvo.getBookName());
			pstmt.setString(2, bvo.getAuthor());
			pstmt.setString(3, bvo.getPublisher());
			pstmt.setInt(4, bvo.getBookId());
			if(pstmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return result;
	}	
	
	/**
	 * 도서 삭제 
	 * @param Bookid : int
	 * @return boolean
	 */
	public boolean delete (int Bookid) {
		sql = "DELETE FROM t_book WHERE book_id=?";
		boolean result = false;
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setInt(1, bvo.getBookId());
			if(pstmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return result;
	}
	
	
}
