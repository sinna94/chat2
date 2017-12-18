package Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket socket;
	private ClientModel clntModel;
	private LoginView logView;
	private ViewController vc;
	
	public Client() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 9090);
		clntModel = new ClientModel(); // Model객체 생성
		logView = new LoginView(); // view객체 생성(처음화면은 로그인화면)
		vc = new ViewController(socket, clntModel, logView);  // 뷰 Controller객체 생성
	}
	public void Start() {
		logView.setVisible(true); // 로그인화면 출력
		try {
			RecieveController recvCon = new RecieveController(socket, vc); // 서버로부터 패킷을받는 리시브컨트롤러 객체 생성
			recvCon.start(); // 스레드 실행
		} catch (Exception ie) {
			System.out.println(ie.getMessage());
		}
	}
}
