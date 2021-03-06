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

public class ViewController { // 뷰에서 발생한 이벤트를 처리하고 서버에 보내는 클래스(controller)
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
		loginView.addLoginListener(new LoginListener()); // 로그인버튼 리스너(로그인 뷰)
		loginView.addRegisterListener(new RegisterListener()); // 회원가입버튼 리스너(로그인 뷰)
		this.packet = new Packet();
	}
	
	public void sendPacket(Packet packet) throws IOException
	{
		oos.writeObject(packet);
		oos.flush();
		oos.reset();
	}
	
	public void setMainView(MainView mainView) { // 메인뷰를 참조하는 메소드
		this.mainView = mainView;
	}
	
	public void setFriendList() {
		String friendState = "접속 중";
		ArrayList<Friend> friendList = clntModel.getMyFriends(); // 모델로부터 친구목록을 가져온다
		DefaultTableModel tableModel = mainView.getFriendTabelModel(); // 친구목록 테이블의 모델을 참조
		tableModel.setNumRows(0); // 친구 목록 초기화
		for(int i = 0; i < friendList.size(); i++) {
			tableModel.insertRow(0, new Object[] {
					friendList.get(i).getFriendId(), // 친구 아이디
					friendState, // 친구 상태
				}); 
		}
	}
	
	public void addMainViewListener() { // 메인뷰에 리스너를 달아주는 메소드
		mainView.addAddListener(new InputListener("REQ_ADD")); // 친구추가버튼 리스너 (메인 뷰)
		mainView.addRemoveListener(new InputListener("REQ_REMOVE")); // 친구삭제버튼 리스너 (메인 뷰)
		mainView.addMsgListener(new MsgListener()); // 쪽지보내기버튼 리스너 (메인 뷰)
		mainView.addChatListener(new ChatListener()); // 채팅하기버튼 리스너 (메인 뷰)
		mainView.addExitListener(new ExitListener()); // 종료버튼 리스너 (메인 뷰)
	}
	// 로그인 버튼 리스너(로그인 뷰)
	class LoginListener implements ActionListener { // 로그인 뷰의 로그인버튼 리스너
		@Override
		public void actionPerformed(ActionEvent e) { // 입력받은 아이디와 비밀번호를 서버로 전송
			String id = loginView.getId(); // 뷰로부터 아이디와 비밀번호를 get한다
			String pwd = loginView.getPwd();
			Account account = new Account(); // 전송할 데이터가 id와 pwd이므로 account객체를 하나 생성
			account.setId(id); // account객체에 set
			account.setPassword(pwd);
			Object data = account; // account객체를 object로 업캐스팅(패킷의 데이터는 object형식)
			packet.setCode("REQ_LOGIN"); // 패킷의 코드명을 입력
			packet.setData(data); // data set
			try {
				sendPacket(packet); // 패킷 전송
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			loginView.dispose(); // 로그인 뷰 닫기
		}	
	}
	// 회원가입 버튼 리스너(로그인 뷰)
	class RegisterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			registerView = new RegisterView();
			registerView.addEnterListener(new RegisterEnterListener());
			registerView.addCancelListener(new RegisterCancelListener());
			registerView.setVisible(true);
		}	
	}
	// 확인 버튼 리스너(회원가입 뷰)
	class RegisterEnterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String id = registerView.getId(); // 뷰로부터 입력된 정보를 get
			String pwd = registerView.getPwd();
			String name = registerView.getName();
			String phone = registerView.getPhone();
			String addr = registerView.getAddr();
			Account account = new Account(); // account객체 생성
			account.setId(id); // account객체에 set
			account.setPassword(pwd);
			account.setName(name);
			account.setPhone(phone);
			account.setAddress(addr);
			Object data = account; // account객체를 object로 업캐스팅(패킷의 데이터는 object형식)
			packet.setCode("REQ_REGISTER"); // 패킷의 코드명을 입력
			packet.setData(data); // data set
			try {
				sendPacket(packet); // 패킷 전송
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			registerView.dispose(); // 회원가입 뷰 닫기
		}
	}
	// 취소 버튼 리스너(회원가입 뷰)
	class RegisterCancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			registerView.dispose(); // 회원가입 뷰 닫기
		}
	}
	// 입력 뷰(친구추가, 삭제) 리스너(메인 뷰)
	class InputListener implements ActionListener {
		private String code;
		public InputListener(String code) {
			this.code = code;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			inputView = new InputView();
			// 입력버튼의 리스너에 code를 넣어준다(추가, 삭제를 구분하가위함)
			inputView.addEnterListener(new InputEnterListener(code)); 
			inputView.addCancelListener(new InputCancelListener());
			inputView.setVisible(true);
		}
	}
	// 입력 버튼 리스너(입력 뷰)
	class InputEnterListener implements ActionListener {
		private String code;
		public InputEnterListener(String code) {
			this.code = code;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// 모델에서 자신의 아이디와 추가할 아이디를 합친다.(#로 구분)
			String str = clntModel.getMyId() + "#" + inputView.getText();
			Object data = str; // 스트링객체를 object로 업캐스팅(패킷의 데이터는 object형식)
			packet.setCode(code); // 패킷의 코드명을 입력(친구추가 또는 삭제)
			packet.setData(data); // data set
			try {
				sendPacket(packet); // 패킷 전송
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			inputView.dispose(); // 입력 뷰 닫기
		}
	}
	// 취소 버튼 리스너(입력 뷰)
	class InputCancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			inputView.dispose(); // 입력 뷰 닫기
		}
	}
	
	// 쪽지보내기 버튼 리스너(메인 뷰)
	class MsgListener implements ActionListener {
		private int selectRow = -1;
		private String selectId;
		private JTable friendTable;
		public MsgListener() {
			friendTable = mainView.getFriendTabel();
			friendTable.addMouseListener(new MyMouseListener()); // 메인뷰의 친구목록 테이블에 마우스 리스너 추가
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(selectRow == -1) JOptionPane.showMessageDialog(null, "메시지를 보낼 대상을 선택하세요.");
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
			    	selectRow = friendTable.getSelectedRow(); // 선택한 레코드의 행에서
					selectId = (String) friendTable.getValueAt(selectRow, 0); // 첫번째 칼럼(아이디)를 가져옴
		    	}
		    }
		}
	}
	// 입력 버튼 리스너(메시지 뷰)
	class MsgEnterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 자신의아이디와 보낼 아이디, 메시지를 합친다.(#로 구분)
			String str = clntModel.getMyId() + "#" + messageView.getSelectId() + "#" + messageView.getMsg();
			Object data = str; // 스트링객체를 object로 업캐스팅(패킷의 데이터는 object형식)
			packet.setCode("REQ_MSG"); // 패킷의 코드명을 입력(친구추가 또는 삭제)
			packet.setData(data); // data set
			try {
				sendPacket(packet); // 패킷 전송
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			messageView.dispose(); // 메시지 뷰 닫기
		}
	}

	// 취소 버튼 리스너(입력 뷰)
	class MsgExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			messageView.dispose(); // 메시지 뷰 닫기
		}
	}
	// 채팅하기 버튼 리스너(메인 뷰)
	class ChatListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			chattingView = new ChattingView();
			chattingView.setVisible(true);
		}
	}
	// 종료 버튼 리스너(메인 뷰)
	class ExitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			packet.setCode("REQ_LOGOUT"); // 패킷의 코드명을 입력
			try {
				sendPacket(packet); // 패킷 전송
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			mainView.dispose(); // 메인 뷰 닫기
			System.exit(0);
		}
	}
}
