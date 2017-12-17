package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MasterServer {
	private final int port = 9090;
	private ServerSocket ss;
	private ArrayList<ChildServer> childList;
	public DAO dao;
	
	public MasterServer() {
		this.childList = new ArrayList<ChildServer>();
		this.dao = new DAO();
	}
	
	public void start() throws IOException {
		try{	
			ss = new ServerSocket(port);
		} catch (IOException e) {
			System.err.print("�������� ����:" + e);
			System.exit(0);
		}
		System.out.println("������ ���۵Ǿ����ϴ�. ");
		System.out.println("���Ӵ����..");
		try {
			while(true) {
				Socket socket = ss.accept();
				System.out.println(socket.getInetAddress() + "�� �����Ͽ����ϴ�.");
				ChildServer csvr = new ChildServer(socket, dao);
				csvr.start();
				childList.add(csvr);
			} 
		} catch(IOException e){
			System.err.print("�������� ����:" + e);
			System.exit(-1);
		} finally{
			ss.close();
		}
	}
}
