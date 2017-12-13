package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import DTO.Packet;

public class RecieveController extends Thread { // 서버로부터 패킷을 받는 클래스(controller)
	private ObjectInputStream ois;
	
	public RecieveController(Socket socket) throws IOException {
		this.ois = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void run() {
		try {
			Packet packet = new Packet();

			while (true) {
				packet = (Packet) ois.readObject(); // 패킷을 읽는다

				switch(packet.getCode()) { // 코드 해석
					default : break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
