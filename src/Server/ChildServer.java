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
				packet = (Packet) ois.readObject(); // ��Ŷ �ޱ�
				switch (packet.getCode()) { // ��Ŷ �ؼ�
				case "REQ_LOGIN":			// �α��� ��û
					checkLogin((Account)packet.getData());
					break;
					
				case "REQ_REGISTER":			// ȸ������ ��û
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

		if (result == true) { // DB üũ ���
			packet.setCode("LOGIN_SUC");
		} 
		else {
			packet.setCode("LOGIN_FAIL");
		}
		sendPacket(packet);
	}
	
	public void regist(Account account) throws SQLException{
		int result = dao.registDB(account);
		
		if (result == 0 ){
			packet.setCode("REGI_FAIL");
		}
		else{
			packet.setCode("REGI_SUCC");
		}
		
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
