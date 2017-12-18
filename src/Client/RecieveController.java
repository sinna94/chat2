package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import DTO.Friend;
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
					case "LOGIN_SUC": // �α����� ����
						clntModel.setMyId((String) packet.getData()); // �𵨿� �ڽ��� ID�����͸� ����
						MainView mainView = new MainView(clntModel.getMyId()); // ���κ信 ID�� ���ڷ� �ѱ�
						mainView.setVisible(true); // ���� �� ���
						vc.setMainView(mainView); // ����Ʈ�ѷ��� ���κ� ����
						vc.addMainViewListener(); // ���κ信 ������ �޾��ֱ�
						break;
					case "LOGIN_FAIL": // �α��� ����
						JOptionPane.showMessageDialog(null, "�α��� ����");
						break;
					case "FRIEND_LIST":
						// Ŭ���̾�Ʈ �𵨿� ģ������� set
						clntModel.setMyFriends((ArrayList<Friend>) packet.getData());
						vc.setFriendList(); // ���κ信 ģ������� ����ϴ� ����Ʈ�ѷ��� �޼ҵ� ȣ��
						break;
					case "REGI_SUC":  // ȸ������ ����
						JOptionPane.showMessageDialog(null, "ȸ������ ����");
						break;
					case "REGI_FAIL": // ȸ������ ����
						JOptionPane.showMessageDialog(null, "ȸ������ ����");
						break;
					case "ADD_SUC":  // ģ���߰� ����
						JOptionPane.showMessageDialog(null, "ģ���߰� ����");
						break;
					case "ADD_FAIL": // ģ���߰� ����
						JOptionPane.showMessageDialog(null, "ģ���߰� ����");
						break;
					case "REMOVE_SUC":  // ģ������ ����
						JOptionPane.showMessageDialog(null, "ģ������ ����");
						break;
					case "REMOVE_FAIL": // ģ������ ����
						JOptionPane.showMessageDialog(null, "ģ������ ����");
						break;
					case "SEND_MSG": // �޽��� �ޱ�
						String msg = (String) packet.getData();
						JOptionPane.showMessageDialog(null, msg);
						break;

					default : break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
