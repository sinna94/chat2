package Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket socket;
	
	public Client() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 9090);
		ClientModel clntModel = new ClientModel(); // Model객체 생성
		LoginView logView = new LoginView(); // View객체 생성(처음화면은 로그인화면)
		SendController sendController = new SendController(socket, clntModel, logView); // Controller객체 생성
		logView.setVisible(true); // 로그인화면 출력
	}
	public void Start() {
		try {
			RecieveController recvCon = new RecieveController(socket); // 서버로부터 패킷을받는 리시브컨트롤러 객체 생성
			recvCon.start(); // 스레드 실행
		} catch (Exception ie) {
			System.out.println(ie.getMessage());
		}
	}
}
