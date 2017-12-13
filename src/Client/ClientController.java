package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import DTO.Packet;

public class ClientController extends Thread {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	Scanner sc;
	
	public ClientController(Socket socket) throws IOException {
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
		sc = new Scanner(System.in);
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
	
	public void sendPacket(Packet packet) throws IOException {
		oos.writeObject(packet);
		oos.flush();
		oos.reset();
	}
}
