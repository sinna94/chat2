package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import DTO.Account;
import DTO.Packet;

public class SendController { // Ŭ���̾�Ʈ���� �߻��� �̺�Ʈ�� ó���ϰ� ������ ������ Ŭ����(controller)
	private ObjectOutputStream oos;
	private ClientModel clientModel;
	private LoginView loginView;
	private RegisterView registerView;
	private MainView mainView;
	private MessageView messageView;
	private ChattingView chattingView;
	private Packet packet;
	
	public SendController(Socket socket, ClientModel clientModel, LoginView loginView) throws IOException {
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.clientModel = clientModel;
		this.loginView = loginView;
		loginView.addLoginListener(new LoginListener());
		loginView.addRegisterListener(new RegisterListener());
		this.packet = new Packet();
	}
	
	public void sendPacket(Packet packet) throws IOException
	{
		oos.writeObject(packet);
		oos.flush();
		oos.reset();
	}
	// �α��� ��ư ������(�α��� ��)
	class LoginListener implements ActionListener { // �α��� ���� �α��ι�ư ������
		@Override
		public void actionPerformed(ActionEvent e) { // �Է¹��� ���̵�� ��й�ȣ�� ������ ����
			String id = loginView.getId(); // ��κ��� ���̵�� ��й�ȣ�� get�Ѵ�
			String pwd = loginView.getPwd();
			Account account = new Account(); // ������ �����Ͱ� id�� pwd�̹Ƿ� account��ü�� �ϳ� ����
			account.setId(id); // account��ü�� set
			account.setPassword(pwd);
			Object data = account; // account��ü�� object�� ��ĳ����(��Ŷ�� �����ʹ� object����)
			packet.setCode("REQ_LOGIN"); // ��Ŷ�� �ڵ���� �Է�
			packet.setData(data); // data set
			try {
				sendPacket(packet); // ��Ŷ ����
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}	
	}
	// �������� ��ư ������(�α��� ��)
	class RegisterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			registerView = new RegisterView();
			registerView.addEnterListener(new EnterListener());
			registerView.addCancelListener(new CancelListener());
			registerView.setVisible(true);
		}	
	}
	// Ȯ�� ��ư ������(ȸ������ ��)
	class EnterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String id = registerView.getId(); // ��κ��� �Էµ� ������ get
			String pwd = registerView.getPwd();
			String name = registerView.getName();
			String phone = registerView.getPhone();
			String addr = registerView.getAddr();
			Account account = new Account(); // account��ü ����
			account.setId(id); // account��ü�� set
			account.setPassword(pwd);
			account.setName(name);
			account.setPhone(phone);
			account.setAddress(addr);
			Object data = account; // account��ü�� object�� ��ĳ����(��Ŷ�� �����ʹ� object����)
			packet.setCode("REQ_REGISTER"); // ��Ŷ�� �ڵ���� �Է�
			packet.setData(data); // data set
			try {
				sendPacket(packet); // ��Ŷ ����
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	// ��� ��ư ������(ȸ������ ��)
	class CancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			registerView.dispose(); // ȸ������ �� �ݱ�
		}
	}
}
