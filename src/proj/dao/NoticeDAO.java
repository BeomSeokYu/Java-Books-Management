package proj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import proj.util.DBCon;
import proj.vo.NoticeVO;

public class NoticeDAO {
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<NoticeVO> list;
	private NoticeVO nvo;
	
	// 등록
	public boolean insertNotice(NoticeVO nvo) {
		sql = "INSERT INTO t_notice (notice_id, title, content, reg_date) "
				+ "VALUES((SELECT NVL(MAX(notice_id),0) + 1 FROM t_notice) " +
				", ?, ?, SYSDATE) ";
		
		
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, nvo.getTitle());
			pstmt.setString(2, nvo.getContent());
			
			int result = pstmt.executeUpdate();	//insert 쿼리를 실행하고 결과를 받아 성공 여부 확인
			if(result == 1) {	//1행이 삽입되면 데이터 추가 성공
				return true;
			}
		} catch (SQLException e) {		
			e.printStackTrace();	
		} finally { 
			DBCon.close(pstmt);	
		}
		return false;	
	}
	// 전체  목록
	public ArrayList<NoticeVO> selectAllNotice() {
		sql = "SELECT notice_id, title, content, reg_date, mod_date "
				+ "FROM t_notice ORDER BY notice_id ASC"; 
		
		ArrayList<NoticeVO> nvoList = new ArrayList<>();
		
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {	
				NoticeVO nvo = new NoticeVO(		
								rs.getInt("notice_id"),
								rs.getString("title"),
								rs.getString("content"),
								rs.getDate("reg_date"),
								rs.getDate("mod_date")								
				);
				nvoList.add(nvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {					DBCon.close(rs, pstmt);	}
		return nvoList;	//결과값이 없다면 0을 반환

	}
	// 선택 글 가져오기
	public NoticeVO select(int id) {
		sql = "SELECT notice_id, title, content, reg_date, mod_date "
                + "FROM t_notice WHERE NOTICE_ID=?";
        NoticeVO nvo = null;
        try {
            pstmt = proj.util.DBCon.getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);

            rs = pstmt.executeQuery();
            if(rs.next()) {
                nvo = new NoticeVO(    //등록일과 수정일을 제외한 모든 항목
                        rs.getInt("NOTICE_ID"),
                        rs.getString("TITLE"),
                        rs.getString("CONTENT"),
                        rs.getDate("REG_DATE"),
                        rs.getDate("MOD_DATE")
                );
            }//실행 결과가 있으면 로그인 성공
        } catch (SQLException e) {    e.printStackTrace();
        } finally {                    proj.util.DBCon.close(rs, pstmt);
            }
        return nvo;
	}
	// 정보 업데이트
	public boolean update(NoticeVO nvo) {
		sql = " UPDATE t_notice " +
				"SET title=?, content=?, mod_date=SYSDATE " + 
				"WHERE notice_id=?";
		
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, nvo.getTitle());
			pstmt.setString(2, nvo.getContent());
			pstmt.setInt(3, nvo.getNoticeId());
			
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
	// 제거
	public boolean delete (int id) {
		sql = "DELETE t_notice WHERE notice_id=?";								

		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setInt(1,  id);
			
			int result = pstmt.executeUpdate();	//delete 쿼리를 실행하고 결과를 받아 성공 여부 확인
			if(result == 1) {	//1행이 변경되면 설문 삭제 성공
				return true;
			}
		} catch (SQLException e) {		
			e.printStackTrace();	
		} finally { 
			DBCon.close(pstmt);	
		}
		return false;
	}
	
	
}
