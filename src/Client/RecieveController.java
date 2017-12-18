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
						vc.closeRegisterView(); // ȸ������ �� �ݱ�
						break;
					case "REGI_FAIL": // ȸ������ ���н� �޽��� ���
						JOptionPane.showMessageDialog(null, "ȸ������ ����");
						vc.closeRegisterView(); // ȸ������ �� �ݱ�
						break;
					case "ADD_SUC":  // ģ���߰� ������ �޽��� ���
						JOptionPane.showMessageDialog(null, "ģ���߰� ����");
						vc.closeRegisterView(); // ȸ������ �� �ݱ�
						break;
					case "ADD_FAIL": // ģ���߰� ���н� �޽��� ���
						JOptionPane.showMessageDialog(null, "ģ���߰� ����");
						vc.closeRegisterView(); // ȸ������ �� �ݱ�
						break;
					case "REMOVE_SUC":  // ģ������ ������ �޽��� ���
						JOptionPane.showMessageDialog(null, "ģ������ ����");
						vc.closeRegisterView(); // ȸ������ �� �ݱ�
						break;
					case "REMOVE_FAIL": // ģ������ ���н� �޽��� ���
						JOptionPane.showMessageDialog(null, "ģ������ ����");
						vc.closeRegisterView(); // ȸ������ �� �ݱ�
						break;
					case "MSG_SUC": // �޽��� �����Ⱑ �����ϸ�
						JOptionPane.showMessageDialog(null, "�޽��� ���ۼ���");
						break;
					case "MSG_FAIL": // �޽��� �����Ⱑ �����ϸ�
						JOptionPane.showMessageDialog(null, "�޽��� ���۽���");
						break;
					case "CHAT_SUC": // ä�� �����ϸ� ä�� �� ����
						ChattingView chatView = new ChattingView();
						chatView.setVisible(true);
						break;
					case "CHAT_FAIL": // ä�� �����ϸ�
						JOptionPane.showMessageDialog(null, "ä�� ����");
						break;
					default : break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
