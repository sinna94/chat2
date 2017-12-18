package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import DTO.Packet;

public class RecieveController extends Thread { // �����κ��� ��Ŷ�� �޴� Ŭ����(controller)
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
				packet = (Packet) ois.readObject(); // ��Ŷ�� �д´�
				System.out.println(packet.getCode());
				switch(packet.getCode()) { // �ڵ� �ؼ�
					case "LOGIN_SUC": // �α����� �����ϸ� ���� �� ����
						MainView mainView = new MainView();
						mainView.setVisible(true);
						vc.closeLoginView(); // �α��κ� �ݱ�
						break;
					case "LOGIN_FAIL": // �α��� ���н� �޽��� ���
						JOptionPane.showMessageDialog(null, "�α��� ����");
						break;
					case "REGI_SUC":  // ȸ������ ������ �޽��� ���
						JOptionPane.showMessageDialog(null, "ȸ������ ����");
						break;
					case "REGI_FAIL": // ȸ������ ���н� �޽��� ���
						JOptionPane.showMessageDialog(null, "ȸ������ ����");
						break;
					default : break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
