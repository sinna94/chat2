package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import DTO.Account;
import DTO.Packet;

public class ViewController { // �信�� �߻��� �̺�Ʈ�� ó���ϰ� ������ ������ Ŭ����(controller)
	private ObjectOutputStream oos;
	private ClientModel clientModel;
	private LoginView loginView;
	private RegisterView registerView;
	private MainView mainView;
	private InputView inputView;
	private MessageView messageView;
	private ChattingView chattingView;
	private Packet packet;
	
	public ViewController(Socket socket, ClientModel clientModel, LoginView loginView) throws IOException {
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.clientModel = clientModel;
		this.loginView = loginView;
		loginView.addLoginListener(new LoginListener()); // �α��ι�ư ������(�α��� ��)
		loginView.addRegisterListener(new RegisterListener()); // ȸ�����Թ�ư ������(�α��� ��)
		mainView.addAddListener(new InputListener("REQ_ADD")); // ģ���߰���ư ������ (���� ��)
		mainView.addRemoveListener(new InputListener("REQ_REMOVE")); // ģ��������ư ������ (���� ��)
		mainView.addMsgListener(new MsgListener()); // �����������ư ������ (���� ��)
		mainView.addChatListener(new ChatListener()); // ä���ϱ��ư ������ (���� ��)
		mainView.addExitListener(new ExitListener()); // �����ư ������ (���� ��)
		this.packet = new Packet();
	}
	
	public void sendPacket(Packet packet) throws IOException
	{
		oos.writeObject(packet);
		oos.flush();
		oos.reset();
	}
	
	public void closeLoginView() { // �α��κ並 �ݴ� �޼ҵ�
		loginView.dispose();
	}
	
	public void closeRegisterView() { // ȸ�����Ժ並 �ݴ� �޼ҵ�
		registerView.dispose();
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
	// ȸ������ ��ư ������(�α��� ��)
	class RegisterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			registerView = new RegisterView();
			registerView.addEnterListener(new RegisterEnterListener());
			registerView.addCancelListener(new RegisterCancelListener());
			registerView.setVisible(true);
		}	
	}
	// Ȯ�� ��ư ������(ȸ������ ��)
	class RegisterEnterListener implements ActionListener {
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
	class RegisterCancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			registerView.dispose(); // ȸ������ �� �ݱ�
		}
	}
	// �Է� ��(ģ���߰�, ����) ������(���� ��)
	class InputListener implements ActionListener {
		private String code;
		public InputListener(String code) {
			this.code = code;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			inputView = new InputView(code);
			// �Է¹�ư�� �����ʿ� code�� �־��ش�(�߰�, ������ �����ϰ�����)
			inputView.addEnterListener(new InputEnterListener(code)); 
			inputView.addCancelListener(new InputCancelListener());
			inputView.setVisible(true);
		}
	}
	// �Է� ��ư ������(�Է� ��)
	class InputEnterListener implements ActionListener {
		private String code;
		public InputEnterListener(String code) {
			this.code = code;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			Object data = inputView.getText(); // ��Ʈ����ü�� object�� ��ĳ����(��Ŷ�� �����ʹ� object����)
			packet.setCode(code); // ��Ŷ�� �ڵ���� �Է�(ģ���߰� �Ǵ� ����)
			packet.setData(data); // data set
			try {
				sendPacket(packet); // ��Ŷ ����
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	// ��� ��ư ������(�Է� ��)
	class InputCancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			inputView.dispose(); // �Է� �� �ݱ�
		}
	}
	// ���������� ��ư ������(���� ��)
	class MsgListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			inputView.dispose(); // �Է� �� �ݱ�
		}
	}

	// ä���ϱ� ��ư ������(���� ��)
	class ChatListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			inputView.dispose(); // �Է� �� �ݱ�
		}
	}
	// ���� ��ư ������(���� ��)
	class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			mainView.dispose(); // �Է� �� �ݱ�
		}
	}
}
