package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import DTO.Packet;

public class RecieveController extends Thread { // �����κ��� ��Ŷ�� �޴� Ŭ����(controller)
	private ObjectInputStream ois;
	private ClientModel clntModel;
	private ViewController vc;
	
	public RecieveController(Socket socket, ClientModel clntModel, ViewController vc) throws IOException {
		this.ois = new ObjectInputStream(socket.getInputStream());
		this.clntModel = clntModel;
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
						clntModel.setMyId((String) packet.getData()); // �𵨿� �ڽ��� ID�����͸� ����
						MainView mainView = new MainView(clntModel.getMyId()); // ���κ信 ID�� ���ڷ� �ѱ�
						mainView.setVisible(true);
						vc.setMainView(mainView); // ����Ʈ�ѷ��� ���κ� ����
						vc.addMainViewListener(); // ���κ信 ������ �޾��ֱ�
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
					case "ADD_SUC":  // ģ���߰� ������ �޽��� ���
						JOptionPane.showMessageDialog(null, "ģ���߰� ����");
						break;
					case "ADD_FAIL": // ģ���߰� ���н� �޽��� ���
						JOptionPane.showMessageDialog(null, "ģ���߰� ����");
						break;
					case "REMOVE_SUC":  // ģ������ ������ �޽��� ���
						JOptionPane.showMessageDialog(null, "ģ������ ����");
						break;
					case "REMOVE_FAIL": // ģ������ ���н� �޽��� ���
						JOptionPane.showMessageDialog(null, "ģ������ ����");
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
