package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DTO.Account;
import DTO.Packet;

public class DAO {
	/*  PreparedStatement ��� ����
		�ݺ��ؼ� ����Ǵ� ���� ������ �ӵ��� ������Ű�� ����
		�� ��ȯ�� �ڵ����� �ϱ� ����
		������ �ڵ带 ����
	*/
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private final String DB_URL = "jdbc:mysql://localhost/bodybody?useSSL=false";
	
	public DAO(){
		String id = "root";
		String password = "201310835";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("����̹� ���� ����");
			con = DriverManager.getConnection(DB_URL, id, password);
			System.out.println("�����ͺ��̽� ���� ����");
		} catch (ClassNotFoundException e){
			System.out.println("����̹��� ã�� �� �����ϴ�.");
		} catch (SQLException e){
			System.out.println("���ῡ �����߽��ϴ�.");
		}
	}

	public boolean checkLoginDB(Account account) throws SQLException {
		String query = "SELECT * FROM Account WHERE ID = ? AND Password = ?";
		
		pstmt = con.prepareStatement(query); // PreparedStatement ��ü ����
		pstmt.setString(1, account.getId()); // ���̵� ����
		pstmt.setString(2, account.getPassword()); // ��й�ȣ ����
		ResultSet rs = pstmt.executeQuery(); // �����͸� �˻��ϹǷ� executeQuery�� ����Ѵ�

		while(rs.next()) {
			account.setId(rs.getString("ID")); // �����ͺ��̽��κ��� ���̵�� �̸��� �޴´�
			account.setName(rs.getString("Name"));
			return true; // rs�� ������ true
		}
		return false; // rs�� ������ false
	}

	public int registDB(Account account) throws SQLException {
		String id = account.getId();
		String pw = account.getPassword();
		String name = account.getName();
		String phone = account.getPhone();
		String addr = account.getAddress();
		
		String query = "INSERT INTO Account ('" + id + "','" + pw + "','" + name + "','" + phone + "','" + addr + "')"
				+ "VALUES(?, ?, ?, ?, ?, ?)";
		
		pstmt = con.prepareStatement(query);
		pstmt.setString(1, id);
		pstmt.setString(2, pw);
		pstmt.setString(3, name);
		pstmt.setString(4, phone);
		pstmt.setString(5, addr);
		
		int result = pstmt.executeUpdate(query);
		
		return result;
	}
}