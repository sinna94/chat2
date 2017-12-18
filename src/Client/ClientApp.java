package Client;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientApp {
	public static void main(String[] args) throws UnknownHostException, IOException{
		Client client = new Client(); // 클라이언트 객체 생성
		client.Start(); // 클라이언트 시작
	}
}
