package proj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import proj.vo.NoticeVO;
import proj.vo.RentVO;

public class RentDAO {
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<RentVO> list;
	private RentVO rvo;
	
	// 대여
	public boolean insertRent(RentVO rvo) {
		return false;
	}
	// 전체 목록
	public ArrayList<RentVO> selectAllRent() {
		return null;
	}
	// 대여 상세보기
	public RentVO select(int rentNo) {
		return null;
	}
	// 반납
	public boolean update(RentVO nvo) {
		return false;
	}
}
