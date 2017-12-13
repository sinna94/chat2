package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import DTO.Packet;

public class RecieveController extends Thread { // �����κ��� ��Ŷ�� �޴� Ŭ����(controller)
	private ObjectInputStream ois;
	
	public RecieveController(Socket socket) throws IOException {
		this.ois = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void run() {
		try {
			Packet packet = new Packet();

			while (true) {
				packet = (Packet) ois.readObject(); // ��Ŷ�� �д´�

				switch(packet.getCode()) { // �ڵ� �ؼ�
					default : break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
