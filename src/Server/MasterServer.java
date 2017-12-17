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
			System.err.print("서버연결 실패:" + e);
			System.exit(0);
		}
		System.out.println("서버가 시작되었습니다. ");
		System.out.println("접속대기중..");
		try {
			while(true) {
				Socket socket = ss.accept();
				System.out.println(socket.getInetAddress() + "가 접속하였습니다.");
				ChildServer csvr = new ChildServer(socket, dao);
				csvr.start();
				childList.add(csvr);
			} 
		} catch(IOException e){
			System.err.print("서버연결 실패:" + e);
			System.exit(-1);
		} finally{
			ss.close();
		}
	}
}
