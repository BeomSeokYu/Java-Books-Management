package proj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import proj.util.DBCon;
import proj.vo.BookVO;
import proj.vo.MemberVO;
import proj.vo.RentVO;
import proj.vo.ReserVO;

public class ReserDAO {
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<ReserVO> list;
	private ReserVO revo;
	private BookVO bvo;
	private MemberVO mvo;

	// 예약
	public boolean insertReser(ReserVO revo) {
		sql = "SELECT RENT_AVAIL FROM t_book "
				+ "WHERE book_id=? ";
		BookDAO bdao = new BookDAO();
		BookVO bvo = bdao.select(revo.getBookid());
		if(bvo != null && bvo.getRentAvail().equals("N")) {
			sql = "INSERT INTO t_reservation (reser_id, reser_date, id, book_id) "
					+ "VALUES (SEQ_T_REservation_REser_ID.NEXTVAL, sysdate, ?, ?)";
			boolean result = false;
			try {
				DBCon.getConnection().setAutoCommit(false);
				pstmt = DBCon.getConnection().prepareStatement(sql);
				pstmt.setString(1, revo.getId());
				pstmt.setInt(2, revo.getBookid());
				if (pstmt.executeUpdate() == 1  && updateBookReser(revo.getBookid()) ) {
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
		return false;
	}

	public boolean updateBookReser(int bookId) {
		boolean result = false;
		sql = "update t_book set reser_num=reser_num+1 where book_id=?";
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setInt(1, bookId);
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

//	// 예약 목록
//	public ArrayList<ReserVO> selectAllReser() {
//		sql = "SELECT * FROM t_reservation where id=?";
//		try {
//			pstmt = DBCon.getConnection().prepareStatement(sql);
//			pstmt.setString(1, revo.getId());
//			rs = pstmt.executeQuery();
//			list = new ArrayList<>();
//			while (rs.next()) {
//				revo.setReserId(rs.getInt(1));
//				revo.setReserDate(rs.getDate(2));
//				revo.setId(rs.getString(3));
//				revo.setBookid(rs.getInt(4));
//				list.add(revo);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			DBCon.close(rs, pstmt);
//		}
//		return list;
//	}

	// 내 예약 목록
	public ArrayList<ReserVO> selectMyReser(String id) {
		sql = "SELECT reser_id, t_book.book_id, reser_date, rent_avail, author, book_name FROM t_reservation, t_book where t_reservation.id=? and t_reservation.book_id=t_book.book_id ";
		ArrayList<ReserVO> a = new ArrayList<>();
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ReserVO revo = new ReserVO();
				revo.setReserId(rs.getInt(1));
				revo.setBookId(rs.getInt(2));
				revo.setReserDate(rs.getDate(3));
				revo.setRentAvail(rs.getString(4));
				revo.setAuthor(rs.getString(5));
				revo.setBookName(rs.getString(6));
				a.add(revo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return a;
	}

	// 예약 취소
	public boolean delete(int reserId) {
		sql = "delete t_reservation where reser_id=?";

		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setInt(1, reserId);

			int result = pstmt.executeUpdate(); // delete 쿼리를 실행하고 결과를 받아 성공
			if (result == 1) { // 1행이 변경되면 설문 삭제
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return false;
	}
	
	// 예약번호 판별
	public ReserVO selectReser(int reserId) {
		sql = "select reser_date, id from t_reservation where reser_id=?";
		ReserVO revo = null;
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setInt(1, reserId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// 아이디|이름|이메일|사진|성별
				revo = new ReserVO();
				revo.setReserDate(rs.getDate(1));
				revo.setId(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return revo;
	}

}
