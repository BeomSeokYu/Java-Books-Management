package proj.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import proj.util.DBCon;
import proj.vo.MemberVO;

public class MemberDAO {
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String query;
	
	// 회원 가입
	public boolean insertMember(MemberVO mvo) {
		query = "INSERT INTO t_member (id, pw, name, phone, address) VALUES(?, ?, ?, ?, ?)";								
		boolean res = false;
		try {
			pstmt = DBCon.getConnection().prepareStatement(query);
			pstmt.setString(1, mvo.getId());
			pstmt.setString(2, mvo.getPw());
			pstmt.setString(3, mvo.getName());
			pstmt.setString(4, mvo.getPhone());
			pstmt.setString(5, mvo.getAddress());
			
			int result = pstmt.executeUpdate();	//insert 쿼리를 실행하고 결과를 받아 성공 여부 확인
			if(result == 1) {	//1행이 삽입되면 데이터 추가 성공
				res =  true;
			}
		} catch (SQLException e) {		
			e.printStackTrace();	
		} finally { 
			DBCon.close(pstmt);	
		}
		return res;
	}
	
	// 전체 회원 목록
	public ArrayList<MemberVO> selectAllMember() {
		query = "SELECT * FROM t_member ORDER BY id";
		ArrayList<MemberVO> memberList = new ArrayList<>();
		
		try {
			pstmt = DBCon.getConnection().prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {	
//				/아이디|이름|이메일|가입일자
				MemberVO mvo = new MemberVO();
				mvo.setId(rs.getString(1));
				mvo.setPw(rs.getString(2));
				mvo.setName(rs.getString(3));
				mvo.setPhone(rs.getString(4));
				mvo.setAddress(rs.getString(5));
				mvo.setJoinDate(rs.getDate(6));
				mvo.setOverdue(rs.getString(7));
				memberList.add(mvo);
			}//실행 결과가 있으면 로그인 성공
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
		return memberList;	
	}
	
	// 회원 정보 조회
	public MemberVO select(String id) {
			query = "SELECT * FROM t_member WHERE id=?";
			MemberVO mvo = null;
			try {
				pstmt = DBCon.getConnection().prepareStatement(query);
				pstmt.setString(1, id);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {	
//					/아이디|이름|번호|주소|가입일자|연체여부|
					mvo = new MemberVO();
					mvo.setId(rs.getString("id"));
					mvo.setPw(rs.getString("pw"));
					mvo.setName(rs.getString("name"));
					mvo.setPhone(rs.getString("phone"));
					mvo.setAddress(rs.getString("address"));
					mvo.setOverdue(rs.getString("overdue"));
					mvo.setJoinDate(rs.getDate("join_date"));
					
				}//실행 결과가 있으면 로그인 성공
			} catch (SQLException e) {	e.printStackTrace();
			} finally {					DBCon.close(rs, pstmt);	}
			return mvo;
		}
	
	//비밀번호 변경
	public boolean changePw(String id, String pw) {
		query = "UPDATE t_member SET pw=? WHERE id=?";								
		try {
			pstmt = DBCon.getConnection().prepareStatement(query);
			pstmt.setString(1, pw);
			pstmt.setString(2, id);
			
			int result = pstmt.executeUpdate();	//update 쿼리를 실행하고 결과를 받아 성공 여부 확인
			if(result == 1) {	//1행이 변경되면 비밀번호 변경 성공
				return true;
			}
		} catch (SQLException e) {		
			e.printStackTrace();	
		} finally { 
			DBCon.close(pstmt);	
		}
		return false;
	}
	// 회원 정보 변경
	public boolean update(MemberVO mvo) {
		String sql = "UPDATE t_member SET pw=?, name=?, phone=?, address=?, overdue=? WHERE id=?";
		try {
			pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, mvo.getPw());
			pstmt.setString(2, mvo.getName());
			pstmt.setString(3, mvo.getPhone());
			pstmt.setString(4, mvo.getAddress());
			pstmt.setString(5, mvo.getOverdue());
			pstmt.setString(6, mvo.getId());
			if(pstmt.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(pstmt);
		}
		return false;
	}
	// 회원 탈퇴
	public boolean delete(String id) {		
		query = "DELETE t_member WHERE id=?";								

		try {
			pstmt = DBCon.getConnection().prepareStatement(query);
			pstmt.setString(1,  id);
			
			int result = pstmt.executeUpdate();	//delete 쿼리를 실행하고 결과를 받아 성공 여부 확인
			if(result == 1) {	//1행이 변경되면 회원 탈퇴 성공
				return true;
			}
		} catch (SQLException e) {		
			e.printStackTrace();	
		} finally { 
			DBCon.close(pstmt);	
		}
		return false;

	}
	// 로그인 체크
	public boolean loginChk(String id, String pw) {
	    String sql = "SELECT id, pw FROM t_member WHERE id = ? AND pw = ?";
	    try {
	    	pstmt = DBCon.getConnection().prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			rs = pstmt.executeQuery();
			if(rs.next()) 
				return true;	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCon.close(rs, pstmt);
		}
	    return false;
	}
	//아이디 비밀번호 찾기
		public MemberVO select(String name, String phone) {
			query = "SELECT * FROM t_member WHERE name=? and phone=?";
			MemberVO mvo = null;
			try {
				pstmt = DBCon.getConnection().prepareStatement(query);
				pstmt.setString(1, name);
				pstmt.setString(2, phone);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {	
//					/아이디|이름|번호|주소|가입일자|연체여부|
					mvo = new MemberVO();
					mvo.setId(rs.getString("id"));
					mvo.setName(rs.getString("name"));
					mvo.setPhone(rs.getString("phone"));
					mvo.setAddress(rs.getString("address"));
					mvo.setOverdue(rs.getString("overdue"));
					mvo.setJoinDate(rs.getDate("join_date"));
				
				}//실행 결과가 있으면 로그인 성공
			} catch (SQLException e) {	e.printStackTrace();
			} finally {					DBCon.close(rs, pstmt);	}
			return mvo;
		}
		public MemberVO select(String id, String name, String phone) {
			query = "SELECT * FROM t_member WHERE id=? and name=? and phone=?";
			MemberVO mvo = null;
			try {
				pstmt = DBCon.getConnection().prepareStatement(query);
				pstmt.setString(1, id);
				pstmt.setString(2, name);
				pstmt.setString(3, phone);
				
				rs = pstmt.executeQuery();
				if(rs.next()) {	
//					/아이디|이름|번호|주소|가입일자|연체여부|
					mvo = new MemberVO();
					mvo.setId(rs.getString("id"));
					mvo.setPw(rs.getString("pw"));
					mvo.setName(rs.getString("name"));
					mvo.setPhone(rs.getString("phone"));
					mvo.setAddress(rs.getString("address"));
					mvo.setOverdue(rs.getString("overdue"));
					mvo.setJoinDate(rs.getDate("join_date"));
				
				}//실행 결과가 있으면 로그인 성공
			} catch (SQLException e) {	e.printStackTrace();
			} finally {					DBCon.close(rs, pstmt);	}
			return mvo;
		}
}
