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
	private Packet packet;
	
	public SendController(Socket socket, ClientModel clientModel, LoginView loginView, RegisterView registerView,
			MainView mainView, MessageView messageView) throws IOException {
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.clientModel = clientModel;
		this.loginView = loginView;
		loginView.addLoginListener(new LoginListener());
		loginView.addRegisterListener(new RegisterListener());
		this.registerView = registerView;
		registerView.addEnterListener(new EnterListener());
		registerView.addCancelListener(new CancelListener());
		this.mainView = mainView;
		this.messageView = messageView;
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
		String id;
		String pwd;
		@Override
		public void actionPerformed(ActionEvent e) { // �Է¹��� ���̵�� ��й�ȣ�� ������ ����
			id = loginView.getId(); // ��κ��� ���̵�� ��й�ȣ�� get�Ѵ�
			pwd = loginView.getPwd();
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
			RegisterView registerView = new RegisterView();
			registerView.setVisible(true);
		}	
	}
	// Ȯ�� ��ư ������(ȸ������ ��)
	class EnterListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	// ��� ��ư ������(ȸ������ ��)
	class CancelListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
}
