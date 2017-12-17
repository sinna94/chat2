package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DTO.Account;
import DTO.Packet;

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
		
	public boolean checkLoginDB(Packet packet) throws SQLException {
		Account account = (Account) packet.getData(); // 패킷으로부터 데이터(계정) 얻기
		String query = "SELECT * FROM Account WHERE ID = ?, Password = ?";
		
		pstmt = con.prepareStatement(query); // PreparedStatement 객체 생성
		pstmt.setString(1, account.getId()); // 아이디 세팅
		pstmt.setString(2, account.getPassword()); // 비밀번호 세팅
		ResultSet rs = pstmt.executeQuery(); // 데이터를 검색하므로 executeQuery를 사용한다
		
		while(rs.next()) {
			account.setId(rs.getString("ID")); // 데이터베이스로부터 아이디와 이름을 받는다
			account.setName(rs.getString("Name"));
		}
		rs.close();
		pstmt.close();
		
		if(account.getId() == null) // 검색된 내용이 없으면 false
			return false;
		else return true;
	}
}