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
				System.out.println(packet.getCode());
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
						break;
					case "REGI_FAIL": // 회원가입 실패시 메시지 출력
						JOptionPane.showMessageDialog(null, "회원가입 실패");
						break;
					default : break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
