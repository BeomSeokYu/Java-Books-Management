package proj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import proj.vo.AppVO;


public class AppDAO {
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<AppVO> list;
	private AppVO avo;
	
	// 등록
	public boolean insertApp(AppVO avo) {
		return false;
	}
	// 전체  목록
	public ArrayList<AppVO> selectAllApp() {
		return null;
	}
	// 자기꺼 전체  목록
	public ArrayList<AppVO> selectMyApp(String id) {
		return null;
	}
	// 선택 글 가져오기
	public AppVO select(int id) {
		return null;
	}
	// 정보 업데이트
	public boolean update(AppVO avo) {
		return false;
	}
	// 제거
	public boolean delete (int id) {
		return false;
	}
}
