package proj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import proj.util.DBCon;
import proj.vo.AppVO;
import proj.vo.NoticeVO;


public class AppDAO {
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<AppVO> list;
	private AppVO avo;
	
	// 전체 목록
	public ArrayList<AppVO> selectAllApp() {
		sql = "SELECT app_id, approval, book_name, author, publisher, app_date, id "
				+ "FROM t_app ORDER BY app_id ASC"; 
		
		ArrayList<AppVO> avoList = new ArrayList<>();
		
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {	
					AppVO nvo = new AppVO(		
							rs.getInt("app_id"),
							rs.getString("approval"),
							rs.getString("book_name"),
							rs.getString("author"),
							rs.getString("publisher"),
							rs.getDate("app_date"),
							rs.getString("id")
				); 
				avoList.add(nvo);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {					DBCon.close(rs, pstmt);	}
		return avoList;	//결과값이 없다면 0을 반환

	}
	
	// 등록 거절
	public boolean refuBook(AppVO avo) {
		sql = " UPDATE t_app " +
				"SET approval=? " + 
				"WHERE app_id=?";
		
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, "N");
			pstmt.setInt(2, avo.getAppId());
			
			int result = pstmt.executeUpdate();
			if(result == 1) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		
		return false;
	}

	// 등록
		public boolean insertApp(AppVO avo) {
			sql = "INSERT INTO t_app (app_id, book_name, author, publisher, id)"
			+ "VALUES(SEQ_T_APP_APP_ID.NEXTVAL, ?,?,?,?)";
			boolean result = false;
			try {
				pstmt = DBCon.getConnection().prepareStatement(sql);
				pstmt.setString(1, avo.getBookName());
				pstmt.setString(2, avo.getAuthor());
				pstmt.setString(3, avo.getPublisher());
				pstmt.setString(4, avo.getId());
				
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

		// 자기꺼 전체  목록
		public ArrayList<AppVO> selectMyApp(String id) {
			sql = "SELECT * FROM t_app WHERE id =?";
			AppVO avo = null;
			try {
				pstmt = DBCon.getConnection().prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				list = new ArrayList<>();
				while (rs.next()) {
					avo = new AppVO();
					avo.setAppId(rs.getInt(1));
					avo.setApproval(rs.getString(2));
					avo.setBookName(rs.getString(3));
					avo.setAuthor(rs.getString(4));
					avo.setPublisher(rs.getString(5));
					avo.setAppDate(rs.getDate(6));
					avo.setId(rs.getString(7));
					list.add(avo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBCon.close(rs, pstmt);
			}
			return list;
		}

			
		// 선택 글 가져오기
		public AppVO select(int id) {
			sql = "SELECT * FROM t_app where app_id = ?";
			avo = new AppVO();
			try {
				pstmt = DBCon.getConnection().prepareStatement(sql);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					avo.setAppId(rs.getInt(1));
					avo.setApproval(rs.getString(2));
					avo.setBookName(rs.getString(3));
					avo.setAuthor(rs.getString(4));
					avo.setPublisher(rs.getString(5));
					avo.setAppDate(rs.getDate(6));
					avo.setId(rs.getString(7));
				}else {
					return null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				DBCon.close(rs, pstmt);
			}
			return avo;
		}
		// 정보 업데이트
		public boolean update(AppVO avo) {
			sql = "UPDATE t_app set SEQ_T_APP_APP_ID.NEXTVAL,"
				+ "bookname=?, author=?, publisher=?,id=?, approval=?, appdate=?";
			boolean result = false;
			try {
				DBCon.getConnection().setAutoCommit(false);
				pstmt = DBCon.getConnection().prepareStatement(sql);
				pstmt.setString(1, avo.getBookName());
				pstmt.setString(2, avo.getAuthor());
				pstmt.setString(3, avo.getPublisher());
				pstmt.setString(4, avo.getId());
				pstmt.setString(5, avo.getApproval());
				pstmt.setDate(6, (java.sql.Date)avo.getAppDate());
				
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
		
		// 제거
		public boolean delete (int id) {
			sql = "DELETE FROM t_app WHERE app_id=?";
			boolean result = false;
			try {
				pstmt = DBCon.getConnection().prepareStatement(sql);
				pstmt.setInt(1, id);
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
