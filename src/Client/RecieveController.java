package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import DTO.Packet;

public class RecieveController extends Thread { // 서버로부터 패킷을 받는 클래스(controller)
	private ObjectInputStream ois;
	private ViewController vc;
	
	public RecieveController(Socket socket, ViewController vc) throws IOException {
		this.ois = new ObjectInputStream(socket.getInputStream());
		this.vc = vc;
	}
	
	@Override
	public void run() {
		try {
			Packet packet = new Packet();

			while (true) {
				packet = (Packet) ois.readObject(); // 패킷을 읽는다

				switch(packet.getCode()) { // 코드 해석
					case "LOGIN_SUC": // 로그인이 성공하면 메인 뷰 생성
						MainView mainView = new MainView();
						mainView.setVisible(true);
						vc.closeLoginView(); // 로그인뷰 닫기
						break;
					case "LOGIN_FAIL": // 로그인 실패시 메시지 출력
						JOptionPane.showMessageDialog(null, "로그인 실패");
						break;
					case "REGI_SUC":  // 회원가입 성공시 메시지 출력
						JOptionPane.showMessageDialog(null, "회원가입 성공");
						vc.closeRegisterView(); // 회원가입 뷰 닫기
						break;
					case "REGI_FAIL": // 회원가입 실패시 메시지 출력
						JOptionPane.showMessageDialog(null, "회원가입 실패");
						vc.closeRegisterView(); // 회원가입 뷰 닫기
						break;
					case "ADD_SUC":  // 친구추가 성공시 메시지 출력
						JOptionPane.showMessageDialog(null, "친구추가 성공");
						vc.closeRegisterView(); // 회원가입 뷰 닫기
						break;
					case "ADD_FAIL": // 친구추가 실패시 메시지 출력
						JOptionPane.showMessageDialog(null, "친구추가 실패");
						vc.closeRegisterView(); // 회원가입 뷰 닫기
						break;
					case "REMOVE_SUC":  // 친구삭제 성공시 메시지 출력
						JOptionPane.showMessageDialog(null, "친구삭제 성공");
						vc.closeRegisterView(); // 회원가입 뷰 닫기
						break;
					case "REMOVE_FAIL": // 친구삭제 실패시 메시지 출력
						JOptionPane.showMessageDialog(null, "친구삭제 실패");
						vc.closeRegisterView(); // 회원가입 뷰 닫기
						break;
					case "MSG_SUC": // 메시지 보내기가 성공하면
						JOptionPane.showMessageDialog(null, "메시지 전송성공");
						break;
					case "MSG_FAIL": // 메시지 보내기가 실패하면
						JOptionPane.showMessageDialog(null, "메시지 전송실패");
						break;
					case "CHAT_SUC": // 채팅 성공하면 채팅 뷰 생성
						ChattingView chatView = new ChattingView();
						chatView.setVisible(true);
						break;
					case "CHAT_FAIL": // 채팅 실패하면
						JOptionPane.showMessageDialog(null, "채팅 실패");
						break;
					default : break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
