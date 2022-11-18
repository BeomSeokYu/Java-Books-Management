package proj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import proj.vo.MemberVO;
import proj.vo.NoticeVO;

public class NoticeDAO {
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<NoticeVO> list;
	private NoticeVO nvo;
	
	// 등록
	public boolean insertNotice(NoticeVO nvo) {
		return false;
	}
	// 전체  목록
	public ArrayList<NoticeVO> selectAllNotice() {
		return null;
	}
	// 선택 글 가져오기
	public NoticeVO select(int id) {
		return null;
	}
	// 정보 업데이트
	public boolean update(NoticeVO nvo) {
		return false;
	}
	// 제거
	public boolean delete (int id) {
		return false;
	}
}
