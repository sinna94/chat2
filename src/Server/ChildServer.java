package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import DTO.Account;
import DTO.Packet;

public class ChildServer extends Thread {
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	private Packet packet;
	protected DAO dao;
	
	public ChildServer(Socket socket, DAO dao) throws IOException {
		this.socket = socket;
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
		this.packet = new Packet();
		this.dao = dao;
	}

	@Override
	public void run() {
		try {
			while (true) {
				packet = (Packet) ois.readObject(); // 패킷 받기
				switch (packet.getCode()) { // 패킷 해석
				case "REQ_LOGIN": // 로그인 요청
					checkLogin((Account) packet.getData()); // 아이디와 비밀번호 체크
					break;
					
				case "REQ_REGISTER":			// 회원가입 요청
					regist((Account)packet.getData());
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

	public void checkLogin(Account account) throws SQLException{
		boolean result = dao.checkLoginDB(account);
		// DB 체크 결과
		if (result == true) packet.setCode("LOGIN_SUC");
		else packet.setCode("LOGIN_FAIL");
		// 패킷 전송
		sendPacket(packet);
	}
	
	public void regist(Account account) throws SQLException{
		boolean result = dao.registDB(account);
		// DB체크 결과
		if (result == true) packet.setCode("REGI_SUC");
		else packet.setCode("REGI_FAIL");
		// 패킷 전송
		sendPacket(packet);
	}

	public void sendPacket(Packet packet){
		try {
			oos.writeObject(packet);
			oos.flush();
			oos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
