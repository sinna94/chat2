package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import DTO.Friend;
import DTO.Packet;

public class RecieveController extends Thread { // 서버로부터 패킷을 받는 클래스(controller)
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
				packet = (Packet) ois.readObject(); // 패킷을 읽는다

				switch(packet.getCode()) { // 코드 해석
					case "LOGIN_SUC": // 로그인이 성공
						clntModel.setMyId((String) packet.getData()); // 모델에 자신의 ID데이터를 삽입
						MainView mainView = new MainView(clntModel.getMyId()); // 메인뷰에 ID를 인자로 넘김
						mainView.setVisible(true); // 메인 뷰 출력
						vc.setMainView(mainView); // 뷰컨트롤러에 메인뷰 참조
						vc.addMainViewListener(); // 메인뷰에 리스너 달아주기
						break;
					case "LOGIN_FAIL": // 로그인 실패
						JOptionPane.showMessageDialog(null, "로그인 실패");
						break;
					case "FRIEND_LIST":
						// 클라이언트 모델에 친구목록을 set
						clntModel.setMyFriends((ArrayList<Friend>) packet.getData());
						vc.setFriendList(); // 메인뷰에 친구목록을 출력하는 뷰컨트롤러의 메소드 호출
						break;
					case "REGI_SUC":  // 회원가입 성공
						JOptionPane.showMessageDialog(null, "회원가입 성공");
						break;
					case "REGI_FAIL": // 회원가입 실패
						JOptionPane.showMessageDialog(null, "회원가입 실패");
						break;
					case "ADD_SUC":  // 친구추가 성공
						JOptionPane.showMessageDialog(null, "친구추가 성공");
						break;
					case "ADD_FAIL": // 친구추가 실패
						JOptionPane.showMessageDialog(null, "친구추가 실패");
						break;
					case "REMOVE_SUC":  // 친구삭제 성공
						JOptionPane.showMessageDialog(null, "친구삭제 성공");
						break;
					case "REMOVE_FAIL": // 친구삭제 실패
						JOptionPane.showMessageDialog(null, "친구삭제 실패");
						break;
					case "SEND_MSG": // 메시지 받기
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
