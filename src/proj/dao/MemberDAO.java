package proj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import proj.vo.BookVO;
import proj.vo.MemberVO;

public class MemberDAO {
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private ArrayList<MemberVO> list;
	private MemberVO mvo;
	
	// 회원 가입
	public boolean insertMember(MemberVO mvo) {
		return false;

	}
	// 로그인 체크
	public boolean checkLogin(String id, String pw) {
		return false;
	}
	
	// 전체 회원 목록
	public ArrayList<MemberVO> selectAllMember() {
		return null;

	}
	// 회원 정보 조회
	public MemberVO select(String id) {
		return null;

	}
	// 회원 정보 변경
	public boolean update(MemberVO mvo) {
		return false;

	}
	// 회원 탈퇴
	public boolean delete (String id) {
		return false;

	}
}
