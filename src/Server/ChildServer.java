package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import DTO.Packet;

public class ChildServer extends Thread {
	private Socket socket = null;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;

	public ChildServer(Socket socket) throws IOException {
		this.socket = socket;
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
	}
	@Override
	public void run() {
		try {
			Packet packet;
			while (true) {
				packet = (Packet) ois.readObject(); // 패킷 받기
				switch(packet.code) { // 패킷 해석
					default : break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void sendPacket(Packet packet) throws IOException {
		oos.writeObject(packet);
		oos.flush();
		oos.reset();
	}
}
