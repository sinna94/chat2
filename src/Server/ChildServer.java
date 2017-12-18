package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import DTO.Account;
import DTO.Friend;
import DTO.Packet;

public class ChildServer extends Thread {
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;
	private Packet packet;
	private ArrayList<ChildServer> childList;
	protected DAO dao;
	private String myId;
	
	public ChildServer(Socket socket, MasterServer masterServer, DAO dao) throws IOException {
		this.socket = socket;
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
		this.packet = new Packet();
		this.childList = masterServer.getChildList(); // �����ͼ����κ��� child������ ����Ʈ�� �����޴´�
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
				
				case "REQ_LOGOUT": // �α׾ƿ� ��û
					checkLogout();
					break;
					
				case "REQ_REGISTER": // ȸ������ ��û
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
			System.out.println(socket.getInetAddress() + "������ �������ϴ�.");
		}
	}
	
	public void setMyId(String myId) {
		this.myId = myId;
	}
	
	public String getMyId() {
		return myId;
	}
	
	public void checkLogin(Account account) throws SQLException {
		boolean result = dao.checkLoginDB(account);
		// DB üũ ���
		if (result == true) {
			packet.setCode("LOGIN_SUC");
			packet.setData(account.getId()); // �α����� �����ϸ� ��û�� ID�� �ݳ�
			setMyId(account.getId()); // �����忡 ���̵� ����
		}
		else packet.setCode("LOGIN_FAIL");
		// ��Ŷ ����
		sendPacket(packet);

		// �α����Ҷ����� �������� ��� Ŭ���̾�Ʈ�� ģ������� ���ŵǵ��� �Ѵ�
		for(int i = 0; i < childList.size(); i++) {
			childList.get(i).sendFriendList();
		}
	}
	
	public void checkLogout() throws SQLException {
		// child����Ʈ���� �ڽ��� ����
		childList.remove(this);
		// �α׾ƿ��Ҷ����� �������� ��� Ŭ���̾�Ʈ�� ģ������� ���ŵǵ��� �Ѵ�
		for(int i = 0; i < childList.size(); i++) {
			childList.get(i).sendFriendList();
		}
	}
	
	public void sendFriendList() throws SQLException {
		ArrayList<Friend> friendList = dao.getFriendList(getMyId()); // DB�κ��� ģ������� �޴´�
		ArrayList<Friend> connectFriendList = new ArrayList<Friend>(); // �������� ģ������� ������ ArrayList�� �����Ѵ�
		// child������ ���̵𸮽�Ʈ�� DB���� ã�� ���̵𸮽�Ʈ�� ���ؼ� ���ӿ��θ� �Ǵ��Ѵ�
		for(int i = 0; i < childList.size(); i++) {
			String childId = childList.get(i).getMyId();
			for(int j = 0; j < friendList.size(); j++) {
				if(childId.equals(friendList.get(j).getFriendId())) {
					Friend friend = new Friend();
					friend.setFriendId(childId);
					connectFriendList.add(friend);
				}
			}
		}
		packet.setCode("FRIEND_LIST");
		// �������� ģ������� ��Ŷ�� ��´�
		packet.setData(connectFriendList);
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
