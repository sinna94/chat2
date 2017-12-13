package Client;

import java.net.Socket;

public class ClientApp {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 9997);
			ClientController clnt = new ClientController(socket); // 클라이언트 스레드 생성
			clnt.start(); // 스레드 실행
		} catch (Exception ie) {
			System.out.println(ie.getMessage());
		}
	}
}
