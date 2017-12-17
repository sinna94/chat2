package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

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
				case "REQ_LOGIN":			// 로그인 요청
					checkLogin(packet);
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

	public void checkLogin(Packet packet) throws SQLException, IOException {
		boolean result = dao.checkLoginDB(packet);
		
		if(result == true){ // DB 체크 결과
			packet.setCode("LOGIN_SUC");
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
