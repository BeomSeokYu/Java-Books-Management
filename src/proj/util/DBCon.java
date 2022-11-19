package proj.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBCon {
	private static Connection conection = null;
	
	// 생성자 외부클래스 접근 불가 처리
	private DBCon() {
	}
	
	// Connection 객체를 반환하는 공유 메소드
		// jdbc.properties 파일을 읽어서 Properties 클래스의 load()로 드라이버 로딩 및 DB 연결 객체 생성
	public static Connection getConnection() {
		Properties prop = new Properties();
		if (conection == null) {
			try {
				prop.load(new FileInputStream("src\\proj\\util\\jdbc.properties"));
				// 드라이버 로딩
				Class.forName(prop.getProperty("driver"));
				System.out.println("JDBA 로드 완료");
				//DB 연결
				conection = DriverManager.getConnection(prop.getProperty("url")
													, prop.getProperty("username")
													, prop.getProperty("password"));
				//conection.setAutoCommit(false); -- 자동 커밋 비활성
				System.out.println("DB 연결 성공");
			} catch (IOException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			return conection;
		} else {
			return conection;
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		try {
			if(pstmt != null) pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void close(ResultSet rs, PreparedStatement pstmt) {
		try {
			if(rs != null) rs.close();
			if(pstmt != null) pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeConnection() {
		try {
			if(conection != null) conection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
