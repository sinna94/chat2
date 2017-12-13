package Client;

import java.net.Socket;

public class ClientApp {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 9997);
			ClientController clnt = new ClientController(socket); // Ŭ���̾�Ʈ ������ ����
			clnt.start(); // ������ ����
		} catch (Exception ie) {
			System.out.println(ie.getMessage());
		}
	}
}
