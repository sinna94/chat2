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
				packet = (Packet) ois.readObject(); // 패킷 받기
				switch (packet.getCode()) { // 패킷 해석
				case "REQ_LOGIN": // 로그인 요청
					checkLogin((Account) packet.getData()); // 아이디와 비밀번호 체크
					break;
					
				case "REQ_REGISTER":			// 회원가입 요청
					regist((Account)packet.getData());
					break;
				
				case "REQ_ADD": // 친구 추가 요청
					addFriend((String) packet.getData());
					break;
					
				case "REQ_REMOVE": // 친구 삭제 요청
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
		// DB 체크 결과
		if (result == true) {
			packet.setCode("LOGIN_SUC");
			packet.setData(account.getId()); // 로그인이 성공하면 요청한 ID를 반납
		}
		else packet.setCode("LOGIN_FAIL");
		// 패킷 전송
		sendPacket(packet);
	}
	
	public void regist(Account account) throws SQLException {
		boolean result = dao.registDB(account);
		// DB체크 결과
		if (result == true) packet.setCode("REGI_SUC");
		else packet.setCode("REGI_FAIL");
		// 패킷 전송
		sendPacket(packet);
	}
	
	public void addFriend(String data) throws SQLException {
		StringTokenizer st = new StringTokenizer(data, "#"); // #으로 구분된 문자열을 분리
		String myId = st.nextToken(); // 첫번째 토큰은 자신의 ID
		String friendId = st.nextToken(); // 두번째 토큰은 추가할 친구의 ID
		
		boolean result = dao.addFriendDB(myId, friendId); // DB에 친구추가 요청
		// DB체크 결과
		if (result == true) packet.setCode("ADD_SUC");
		else packet.setCode("ADD_FAIL");
		// 패킷 전송
		sendPacket(packet);
	}
	
	public void removeFriend(String data) throws SQLException {
		StringTokenizer st = new StringTokenizer(data, "#"); // #으로 구분된 문자열을 분리
		String myId = st.nextToken(); // 첫번째 토큰은 자신의 ID
		String friendId = st.nextToken(); // 두번째 토큰은 추가할 친구의 ID
		
		boolean result = dao.removeFriendDB(myId, friendId); // DB에 친구삭제 요청
		// DB체크 결과
		if (result == true) packet.setCode("REMOVE_SUC");
		else packet.setCode("REMOVE_FAIL");
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
