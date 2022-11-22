package proj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import proj.util.DBCon;
import proj.vo.RentVO;
import proj.vo.ReserVO;
import proj.vo.SuggesVO;

public class SuggesDAO {
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<SuggesVO> list;
	private SuggesVO svo;
    
	// 등록
	public boolean insertSugges(SuggesVO svo) {
		sql = "INSERT INTO t_suggest (sugges_id, title, content, id) "
				+ "VALUES (SEQ_T_SUGGEST_SUGGES_ID.NEXTVAL, ?, ?, ?)";
		
		boolean result = false;
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, svo.getTitle());
			pstmt.setString(2, svo.getContent());
			pstmt.setString(3, svo.getId());
			
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
	// 전체  목록
	public ArrayList<SuggesVO> selectAllSugges() {
		sql = "SELECT * FROM t_suggest";
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			list = new ArrayList<>();
			while(rs.next()) {
				svo = new SuggesVO();
				svo.setSuggesId(rs.getInt(1)); 
				svo.setTitle(rs.getString(2));
				svo.setContent(rs.getString(3));
				svo.setRegDate(rs.getDate(4));
				svo.setModDate(rs.getDate(5));
				svo.setReply(rs.getString(6));
				svo.setChecked(rs.getString(7));
				svo.setId(rs.getString(8));
				list.add(svo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return list;
	}
	
	// 내 글 목록 가져오기
	public List<SuggesVO> selectMySugges(String id) {
		sql = "SELECT * FROM t_suggest WHERE id = ?"; //이게 맞나...
		List<SuggesVO> slist = new ArrayList<>();
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1,id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				SuggesVO svo = new SuggesVO();
				svo.setSuggesId(rs.getInt(1)); 
				svo.setTitle(rs.getString(2));
				svo.setContent(rs.getString(3));
				svo.setRegDate(rs.getDate(4));
				svo.setModDate(rs.getDate(5));
				svo.setReply(rs.getString(6));
				svo.setChecked(rs.getString(7));
				svo.setId(rs.getString(8));
				slist.add(svo);
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return slist;
	}
	// 선택 글 가져오기
	public SuggesVO select(int suggesId) {
		sql = "SELECT * FROM t_suggest WHERE sugges_id = ?"; //이게 맞나...
		svo = new SuggesVO();
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setInt(1,suggesId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				svo.setSuggesId(rs.getInt(1)); 
				svo.setTitle(rs.getString(2));
				svo.setContent(rs.getString(3));
				svo.setRegDate(rs.getDate(4));
				svo.setModDate(rs.getDate(5));
				svo.setReply(rs.getString(6));
				svo.setChecked(rs.getString(7));
				svo.setId(rs.getString(8));
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return svo;
	}
	
	public SuggesVO selectId(String id) {
		sql = "SELECT * FROM t_suggest WHERE sugges_id = ?"; //이게 맞나...
		svo = new SuggesVO();
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1,id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				svo.setSuggesId(rs.getInt(1)); 
				svo.setTitle(rs.getString(2));
				svo.setContent(rs.getString(3));
				svo.setRegDate(rs.getDate(4));
				svo.setModDate(rs.getDate(5));
				svo.setReply(rs.getString(6));
				svo.setChecked(rs.getString(7));
				svo.setId(rs.getString(8));
			}else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return svo;
	}
	
	// 정보 업데이트
	public boolean update(SuggesVO svo) {
		sql = "UPDATE t_suggest set " 
				+ "title=?, content=?, mod_date=SYSDATE, reply=?, checked=?, id=?"
				+ "WHERE sugges_id = ?";
		boolean result = false;
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, svo.getTitle());
			pstmt.setString(2, svo.getContent());
			pstmt.setString(3, svo.getReply());
			pstmt.setString(4,svo.getChecked());
			pstmt.setString(5, svo.getId());
			pstmt.setInt(6, svo.getSuggesId());
			
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
		sql = "DELETE FROM t_suggest WHERE sugges_id=?";
		boolean result = false;
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setInt(1, svo.getSuggesId());
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
