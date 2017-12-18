package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.Account;
import DTO.Friend;

public class DAO {
	/*  PreparedStatement 사용 이유
		반복해서 실행되는 동일 쿼리의 속도를 증가시키기 위해
		값 변환을 자동으로 하기 위해
		간결한 코드를 위해
	*/
	private Connection con = null;
	private PreparedStatement pstmt = null;
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

	public boolean checkLoginDB(Account account) throws SQLException {
		String query = "SELECT * FROM Account WHERE ID = ? AND Password = ?";
		
		pstmt = con.prepareStatement(query); // PreparedStatement 객체 생성
		pstmt.setString(1, account.getId()); // 아이디 세팅
		pstmt.setString(2, account.getPassword()); // 비밀번호 세팅
		ResultSet rs = pstmt.executeQuery(); // 데이터를 검색하므로 executeQuery를 사용한다

		while(rs.next()) {
			account.setId(rs.getString("ID")); // 데이터베이스로부터 아이디와 이름을 받는다
			account.setName(rs.getString("Name"));
			return true; // rs가 있으면 true
		}
		return false; // rs가 없으면 false
	}
	
	public ArrayList<Friend> getFriendList(String myId) throws SQLException {
		ArrayList<Friend> friendList = new ArrayList<Friend>(); // 친구목록을 담을 ArrayList 생성
		String query = "SELECT * FROM Friends WHERE UserID = ?";
		
		pstmt = con.prepareStatement(query); // PreparedStatement 객체 생성
		pstmt.setString(1, myId); // 자신의 아이디 세팅
		ResultSet rs = pstmt.executeQuery(); // 데이터를 검색하므로 executeQuery를 사용한다

		while(rs.next()) {
			Friend friend = new Friend(); // friend 객체 생성
			friend.setFriendId(rs.getString("FriendID")); // 데이터베이스로부터 친구 아이디를 받는다
			friendList.add(friend); // ArrayList에 담는다
		}
		return friendList; // 친구목록 반환
	}
	
	public boolean registDB(Account account) throws SQLException {
		String id = account.getId();
		String pw = account.getPassword();
		String name = account.getName();
		String phone = account.getPhone();
		String addr = account.getAddress();
		
		String query = "INSERT INTO Account(ID, Password, Name, Address, Phone) VALUES(?, ?, ?, ?, ?)";
		
		pstmt = con.prepareStatement(query);
		pstmt.setString(1, id);
		pstmt.setString(2, pw);
		pstmt.setString(3, name);
		pstmt.setString(4, phone);
		pstmt.setString(5, addr);
		// 리턴값이 없으면(정상적으로 등록되지 않은경우) false
		if(pstmt.executeUpdate() == 0) return false;
		else return true;
	}
	
	public boolean addFriendDB(String myId, String friendId) throws SQLException {
		// 친구등록할 사용자가 존재하는지 먼저 검색한다
		String query = "SELECT * FROM Account WHERE ID = ?";
		pstmt = con.prepareStatement(query);
		pstmt.setString(1, friendId);
		ResultSet rs = pstmt.executeQuery(); // 데이터를 검색하므로 executeQuery를 사용한다
		// rs가 존재하면 친구등록 쿼리를 실행한다
		if(rs.next()) {
			query = "INSERT INTO Friends(UserID, FriendID) VALUES(?, ?)";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, myId);
			pstmt.setString(2, friendId);
			// 리턴값이 없으면(정상적으로 등록되지 않은경우) false
			if(pstmt.executeUpdate() == 0) return false;
			else return true;
		}
		return false; // rs가 없으면 false
	}
	
	public boolean removeFriendDB(String myId, String friendId) throws SQLException {
		String query = "DELETE FROM Friends WHERE UserID = ? AND FriendID = ?";
		
		pstmt = con.prepareStatement(query);
		pstmt.setString(1, myId);
		pstmt.setString(2, friendId);

		// 리턴값이 없으면(정상적으로 등록되지 않은경우) false
		if(pstmt.executeUpdate() == 0) return false;
		else return true;
	}
}