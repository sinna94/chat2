package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {
	/*  PreparedStatement 사용 이유
		반복해서 실행되는 동일 쿼리의 속도를 증가시키기 위해
		값 변환을 자동으로 하기 위해
		간결한 코드를 위해
	*/
	private Connection con = null;
	public PreparedStatement pstmt = null;
	private final String DB_URL = "jdbc:mysql://localhost/bodybody?useSSL=false";
	
	public DAO(){
		String id = "root";
		String password = "201310835";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("드라이버 적재 성공");
			con = DriverManager.getConnection(DB_URL, id, password);
			System.out.println("데이터베이스 연결 성공");
		} catch (ClassNotFoundException e){
			System.out.println("드라이버를 찾을 수 없습니다.");
		} catch (SQLException e){
			System.out.println("연결에 실패했습니다.");
		}
	}

	public ResultSet executeQuery(String query) {
		ResultSet rs = null;
		try {
			pstmt = con.prepareStatement(query);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	public int updateQuery(String query) {
		int result = 0;
		try {
			pstmt = con.prepareStatement(query);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}