package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import DTO.Account;
import DTO.Packet;

public class SendController { // 클라이언트에서 발생한 이벤트를 처리하고 서버에 보내는 클래스(controller)
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
		}	
	}
	// 레지스터 버튼 리스너(로그인 뷰)
	class RegisterListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			registerView = new RegisterView();
			registerView.addEnterListener(new EnterListener());
			registerView.addCancelListener(new CancelListener());
			registerView.setVisible(true);
		}	
	}
	// 확인 버튼 리스너(회원가입 뷰)
	class EnterListener implements ActionListener {
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
		}
	}
	// 취소 버튼 리스너(회원가입 뷰)
	class CancelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			registerView.dispose(); // 회원가입 뷰 닫기
		}
	}
}
