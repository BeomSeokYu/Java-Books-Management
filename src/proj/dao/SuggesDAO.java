package proj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
		return false;
	}
	// 전체  목록
	public ArrayList<SuggesVO> selectAllSugges() {
		return null;
	}
	// 내 글 목록 가져오기
	public ArrayList<SuggesVO> selectMySugges(String id) {
		return null;
	}
	// 선택 글 가져오기
	public SuggesVO select(int id) {
		return null;
	}
	// 정보 업데이트
	public boolean update(SuggesVO svo) {
		return false;
	}
	// 제거
	public boolean delete (int id) {
		return false;
	}
}
