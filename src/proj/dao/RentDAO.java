package proj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.sql.Date;

import proj.main.BookMain;
import proj.util.Constant;
import proj.util.DBCon;
import proj.util.Pub;
import proj.vo.BookVO;
import proj.vo.MemberVO;
import proj.vo.NoticeVO;
import proj.vo.RentVO;
import proj.vo.ReserVO;

public class RentDAO {
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private List<RentVO> list;
	private BookVO bvo;
	private ReserVO revo;
	
	// 대여 END
	public boolean insertRent(RentVO rvo) {
		sql = "INSERT INTO t_rent (rent_id, rent_date, return_deadline, book_id, id) "
				+ "VALUES (SEQ_T_RENT_RENT_ID.NEXTVAL, sysdate, SYSDATE+14, ?, ?)";
		boolean result = false;
		try {
			DBCon.getConnection().setAutoCommit(false);
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setInt(1, rvo.getBookId());
			pstmt.setString(2, rvo.getId());
			if (pstmt.executeUpdate() == 1 && updateBook(rvo.getBookId(), "N")) {
				result = true;
			} else {
				DBCon.getConnection().rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				DBCon.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBCon.close(pstmt);
		}
		return result;
	}

	// 전체 목록
	public List<RentVO> selectAllRent(String id) {
		sql = "select t_book.book_id, return_deadline, return_date, author, book_name, rent_id from t_rent, t_book where t_rent.book_id=t_book.book_id AND id=?";
		List<RentVO> rlist = new ArrayList<>();
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				RentVO rvo = new RentVO();
				rvo.setBookId(rs.getInt(1));
				rvo.setReturnDeadline(rs.getDate(2));
				rvo.setReturnDate(rs.getDate(3));
				rvo.setAuthor(rs.getString(4));
				rvo.setBookName(rs.getString(5));
				rvo.setRentId(rs.getInt(6));
				rlist.add(rvo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return rlist;
	}

	// 대여 상세보기
	public RentVO select(int rentId) {
		sql = "select t_book.book_id, book_name, author, rent_date, return_deadline, rent_avail, rent_id from t_rent, t_book where t_book.book_id=t_rent.book_id AND rent_id=?";
		RentVO rvo = null;
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setInt(1, rentId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 아이디|이름|이메일|사진|성별
				rvo = new RentVO();
				rvo.setBookId(rs.getInt(1));
				rvo.setBookName(rs.getString(2));
				rvo.setAuthor(rs.getString(3));
				rvo.setRentDate(rs.getDate(4));
				rvo.setReturnDeadline(rs.getDate(5));
				rvo.setRentAvail(rs.getString(6));
				rvo.setRentId(rs.getInt(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rvo;
	}

	public RentVO selectBookId(int bookid) {
		sql = "select rent_date, return_deadline, rent_id, id from t_rent WHERE book_id=?";
		RentVO rvo = null;
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setInt(1, bookid);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				rvo = new RentVO();
				rvo.setBookId(bookid);
				rvo.setRentDate(rs.getDate("rent_date"));
				rvo.setReturnDeadline(rs.getDate("return_deadline"));
				rvo.setRentId(rs.getInt("rent_id"));
				rvo.setId(rs.getString("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rvo;
	}
	
	
	// 반납
	public boolean updateRent(RentVO rvo) {
		sql = "update t_rent set return_date=sysdate where rent_id=?";
		boolean result = false;
		try {
			DBCon.getConnection().setAutoCommit(false);
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setInt(1, rvo.getRentId());
			if (pstmt.executeUpdate() == 1 && updateBook(rvo.getBookId(), "Y")) {
				result = true;
			} else {
				DBCon.getConnection().rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				DBCon.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			DBCon.close(pstmt);
		}
		return result;
	}

	public boolean updateBook(int bookId, String rentAvail) {
		sql = "update t_book set rent_avail = ? where book_id = ?";
		boolean result = false;
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, rentAvail);
			pstmt.setInt(2, bookId);
			if (pstmt.executeUpdate() == 1) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return result;
	}
	
	
// 반납기한 연장
	
	public boolean update(RentVO rvo) {
		long d = rvo.getReturnDeadline().getTime();
		long r = rvo.getRentDate().getTime();
		if ((d - r) / (24 * 1000 * 60 * 60) < 28) {
			sql = "update t_rent set return_deadline=? where book_id=?";
			Calendar c = Calendar.getInstance();
			c.setTime(rvo.getReturnDeadline());
			c.add(Calendar.DATE, 7);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        String formattedDate = simpleDateFormat.format(c.getTime());
	        java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
	        
			boolean result = false;
			try {
				pstmt = DBCon.getConnection().prepareStatement(sql);
				
				pstmt.setDate(1, date1);
				pstmt.setInt(2, rvo.getBookId());
				if (pstmt.executeUpdate() == 1) {
					result = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBCon.close(pstmt);
			}
			return result;
		} else {
			return false;
		}
	}
	
	// 대여 END
	public boolean checkOverdue(String id) {
		sql = "SELECT rent_id FROM t_rent WHERE id=? AND return_deadline < SYSDATE";
		boolean checked = false;
		List<RentVO> rlist = new ArrayList<>();
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				RentVO rvo = new RentVO();
				rvo.setRentId(rs.getInt(1));
				rlist.add(rvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		MemberVO mvo = new MemberDAO().select(id);
		if (!rlist.isEmpty()) {
			System.out.println("있음");
			checked = true;
			mvo.setOverdue("Y");
			if(new MemberDAO().update(mvo)) {
				System.out.println("됐음");
			}
		} else {
			mvo.setOverdue("N");
			new MemberDAO().update(mvo);
		}
		return checked;
	}
}