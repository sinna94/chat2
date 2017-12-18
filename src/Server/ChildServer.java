package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.StringTokenizer;

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
				case "REQ_LOGIN": // �α��� ��û
					checkLogin((Account) packet.getData()); // ���̵�� ��й�ȣ üũ
					break;
					
				case "REQ_REGISTER":			// ȸ������ ��û
					regist((Account)packet.getData());
					break;
				
				case "REQ_ADD": // ģ�� �߰� ��û
					addFriend((String) packet.getData());
					break;
					
				case "REQ_REMOVE": // ģ�� ���� ��û
					removeFriend((String) packet.getData());
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

	public void checkLogin(Account account) throws SQLException {
		boolean result = dao.checkLoginDB(account);
		// DB üũ ���
		if (result == true) {
			packet.setCode("LOGIN_SUC");
			packet.setData(account.getId()); // �α����� �����ϸ� ��û�� ID�� �ݳ�
		}
		else packet.setCode("LOGIN_FAIL");
		// ��Ŷ ����
		sendPacket(packet);
	}
	
	public void regist(Account account) throws SQLException {
		boolean result = dao.registDB(account);
		// DBüũ ���
		if (result == true) packet.setCode("REGI_SUC");
		else packet.setCode("REGI_FAIL");
		// ��Ŷ ����
		sendPacket(packet);
	}
	
	public void addFriend(String data) throws SQLException {
		StringTokenizer st = new StringTokenizer(data, "#"); // #���� ���е� ���ڿ��� �и�
		String myId = st.nextToken(); // ù��° ��ū�� �ڽ��� ID
		String friendId = st.nextToken(); // �ι�° ��ū�� �߰��� ģ���� ID
		
		boolean result = dao.addFriendDB(myId, friendId); // DB�� ģ���߰� ��û
		// DBüũ ���
		if (result == true) packet.setCode("ADD_SUC");
		else packet.setCode("ADD_FAIL");
		// ��Ŷ ����
		sendPacket(packet);
	}
	
	public void removeFriend(String data) throws SQLException {
		StringTokenizer st = new StringTokenizer(data, "#"); // #���� ���е� ���ڿ��� �и�
		String myId = st.nextToken(); // ù��° ��ū�� �ڽ��� ID
		String friendId = st.nextToken(); // �ι�° ��ū�� �߰��� ģ���� ID
		
		boolean result = dao.removeFriendDB(myId, friendId); // DB�� ģ������ ��û
		// DBüũ ���
		if (result == true) packet.setCode("REMOVE_SUC");
		else packet.setCode("REMOVE_FAIL");
		// ��Ŷ ����
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
