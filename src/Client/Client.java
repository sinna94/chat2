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
		clntModel = new ClientModel(); // Model��ü ����
		logView = new LoginView(); // view��ü ����(ó��ȭ���� �α���ȭ��)
		vc = new ViewController(socket, clntModel, logView);  // �� Controller��ü ����
	}
	public void Start() {
		logView.setVisible(true); // �α���ȭ�� ���
		try {
			// �����κ��� ��Ŷ���޴� ���ú���Ʈ�ѷ� ��ü ����
			RecieveController recvCon = new RecieveController(socket, clntModel, vc);
			recvCon.start(); // ������ ����
		} catch (Exception ie) {
			System.out.println(ie.getMessage());
		}
	}
}
