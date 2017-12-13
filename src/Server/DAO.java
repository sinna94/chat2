package Server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {
	/*  PreparedStatement ��� ����
		�ݺ��ؼ� ����Ǵ� ���� ������ �ӵ��� ������Ű�� ����
		�� ��ȯ�� �ڵ����� �ϱ� ����
		������ �ڵ带 ����
	*/
	private Connection con = null;
	public PreparedStatement pstmt = null;
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