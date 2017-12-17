package Client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private Socket socket;
	
	public Client() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 9090);
		ClientModel clntModel = new ClientModel(); // Model��ü ����
		LoginView logView = new LoginView(); // View��ü ����(ó��ȭ���� �α���ȭ��)
		SendController sendController = new SendController(socket, clntModel, logView); // Controller��ü ����
		logView.setVisible(true); // �α���ȭ�� ���
	}
	public void Start() {
		try {
			RecieveController recvCon = new RecieveController(socket); // �����κ��� ��Ŷ���޴� ���ú���Ʈ�ѷ� ��ü ����
			recvCon.start(); // ������ ����
		} catch (Exception ie) {
			System.out.println(ie.getMessage());
		}
	}
}
