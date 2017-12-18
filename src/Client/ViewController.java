package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DTO.Account;
import DTO.Friend;
import DTO.Packet;

public class ViewController { // �信�� �߻��� �̺�Ʈ�� ó���ϰ� ������ ������ Ŭ����(controller)
	private ObjectOutputStream oos;
	private ClientModel clntModel;
	private LoginView loginView;
	private RegisterView registerView;
	private MainView mainView;
	private InputView inputView;
	private MessageView messageView;
	private ChattingView chattingView;
	private Packet packet;
	
	public ViewController(Socket socket, ClientModel clientModel, LoginView loginView) throws IOException {
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.clntModel = clientModel;
		this.loginView = loginView;
		loginView.addLoginListener(new LoginListener()); // �α��ι�ư ������(�α��� ��)
		loginView.addRegisterListener(new RegisterListener()); // ȸ�����Թ�ư ������(�α��� ��)
		this.packet = new Packet();
	}
	
	public void sendPacket(Packet packet) throws IOException
	{
		oos.writeObject(packet);
		oos.flush();
		oos.reset();
	}
	
	public void setMainView(MainView mainView) { // ���κ並 �����ϴ� �޼ҵ�
		this.mainView = mainView;
	}
	
	public void setFriendList() {
		String friendState = "���� ��";
		ArrayList<Friend> friendList = clntModel.getMyFriends(); // �𵨷κ��� ģ������� �����´�
		DefaultTableModel tableModel = mainView.getFriendTabelModel(); // ģ����� ���̺��� ���� ����
		tableModel.setNumRows(0); // ģ�� ��� �ʱ�ȭ
		for(int i = 0; i < friendList.size(); i++) {
			tableModel.insertRow(0, new Object[] {
					friendList.get(i).getFriendId(), // ģ�� ���̵�
					friendState, // ģ�� ����
				}); 
		}
	}
	
	public void addMainViewListener() { // ���κ信 �����ʸ� �޾��ִ� �޼ҵ�
		mainView.addAddListener(new InputListener("REQ_ADD")); // ģ���߰���ư ������ (���� ��)
		mainView.addRemoveListener(new InputListener("REQ_REMOVE")); // ģ��������ư ������ (���� ��)
		mainView.addMsgListener(new MsgListener()); // �����������ư ������ (���� ��)
		mainView.addChatListener(new ChatListener()); // ä���ϱ��ư ������ (���� ��)
		mainView.addExitListener(new ExitListener()); // �����ư ������ (���� ��)
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
			loginView.dispose(); // �α��� �� �ݱ�
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
			registerView.dispose(); // ȸ������ �� �ݱ�
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
			inputView = new InputView();
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
			// �𵨿��� �ڽ��� ���̵�� �߰��� ���̵� ��ģ��.(#�� ����)
			String str = clntModel.getMyId() + "#" + inputView.getText();
			Object data = str; // ��Ʈ����ü�� object�� ��ĳ����(��Ŷ�� �����ʹ� object����)
			packet.setCode(code); // ��Ŷ�� �ڵ���� �Է�(ģ���߰� �Ǵ� ����)
			packet.setData(data); // data set
			try {
				sendPacket(packet); // ��Ŷ ����
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			inputView.dispose(); // �Է� �� �ݱ�
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
		private int selectRow = -1;
		private String selectId;
		private JTable friendTable;
		public MsgListener() {
			friendTable = mainView.getFriendTabel();
			friendTable.addMouseListener(new MyMouseListener()); // ���κ��� ģ����� ���̺� ���콺 ������ �߰�
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(selectRow == -1) JOptionPane.showMessageDialog(null, "�޽����� ���� ����� �����ϼ���.");
			else {
				messageView = new MessageView(selectId);
				messageView.addEnterListener(new MsgEnterListener());
				messageView.addExitListener(new MsgExitListener());
				messageView.setVisible(true);
			}
		}
		class MyMouseListener extends MouseAdapter {
		    @Override
		    public void mouseClicked(MouseEvent e) {
			    if (e.getButton() == 1) {
			    	selectRow = friendTable.getSelectedRow(); // ������ ���ڵ��� �࿡��
					selectId = (String) friendTable.getValueAt(selectRow, 0); // ù��° Į��(���̵�)�� ������
		    	}
		    }
		}
	}
	// �Է� ��ư ������(�޽��� ��)
	class MsgEnterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// ���� ���̵�� �޽����� ��ģ��.(#�� ����)
			String str = messageView.getSelectId() + "#" + messageView.getMsg();
			Object data = str; // ��Ʈ����ü�� object�� ��ĳ����(��Ŷ�� �����ʹ� object����)
			packet.setCode("REQ_MSG"); // ��Ŷ�� �ڵ���� �Է�(ģ���߰� �Ǵ� ����)
			packet.setData(data); // data set
			try {
				sendPacket(packet); // ��Ŷ ����
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			messageView.dispose(); // �޽��� �� �ݱ�
		}
	}

	// ��� ��ư ������(�Է� ��)
	class MsgExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			messageView.dispose(); // �޽��� �� �ݱ�
		}
	}
	// ä���ϱ� ��ư ������(���� ��)
	class ChatListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			chattingView = new ChattingView();
			chattingView.setVisible(true);
		}
	}
	// ���� ��ư ������(���� ��)
	class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			packet.setCode("REQ_LOGOUT"); // ��Ŷ�� �ڵ���� �Է�
			try {
				sendPacket(packet); // ��Ŷ ����
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			mainView.dispose(); // ���� �� �ݱ�
			System.exit(0);
		}
	}
}
