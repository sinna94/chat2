package Client;

import java.net.Socket;

public class ClientApp {
	public static void main(String[] args) {
		LoginView loginView = new LoginView();
		loginView.setVisible(true);
		/*
		try {
			Socket socket = new Socket("localhost", 9090);
			RecieveController clnt = new RecieveController(socket); // Ŭ���̾�Ʈ ������ ����
			clnt.start(); // ������ ����
		} catch (Exception ie) {
			System.out.println(ie.getMessage());
		}
		*/
	}
}
