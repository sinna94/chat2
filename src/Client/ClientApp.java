package Client;

import java.net.Socket;

public class ClientApp {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 9997);
			Client clnt = new Client(socket); // Ŭ���̾�Ʈ ������ ����
			clnt.start(); // ������ ����
		} catch (Exception ie) {
			System.out.println(ie.getMessage());
		}
	}
}
