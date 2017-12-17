package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;

import DTO.Account;
import DTO.Packet;

public class ChildServer extends Thread {
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	private DAO dao = null;
	private Packet packet;
	
	public ChildServer(Socket socket) throws IOException {
		this.socket = socket;
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
		this.dao = new DAO();
	}

	@Override
	public void run() {
		try {
			Packet packet;
			while (true) {
				packet = (Packet) ois.readObject(); // 패킷 받기
				switch (packet.getCode()) { // 패킷 해석
				case "REQ_LOGIN":			// 로그인 요청
					checkLogin((Account) packet.getData());
					break;

				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void checkLogin(Account account) throws SQLException, IOException {
		String id = account.getId();
		String pw = account.getPassword();
		
		ResultSet rs = dao.executeQuery("SELECT * FROM account WHERE id = '" + id + "' and '" + pw + "';");
		
		if(rs.next()){
			packet.setCode("LOGIN_SUCC");
		}
		else{
			packet.setCode("LOGIN_FAIL");
		}
		oos.writeObject(packet);
	}

	public void sendPacket(Packet packet) throws IOException {
		oos.writeObject(packet);
		oos.flush();
		oos.reset();
	}
}
