package proj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import proj.vo.RentVO;
import proj.vo.ReserVO;

public class ReserDAO {
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<ReserVO> list;
	private ReserVO rvo;
	
	// 예약
	public boolean insertReser(ReserVO rvo) {
		return false;
	}
	// 예약 목록
	public ArrayList<ReserVO> selectAllReser() {
		return null;
	}
	// 내 예약 목록
	public ArrayList<ReserVO> selectMyReser(String id) {
		return null;
	}
	// 예약 상세보기
	public ReserVO select(int rentNo) {
		return null;
	}
	// 예약 취소
	public boolean delete (int reservNo) {
		return false;
	}

}
