package Client;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientApp {
	public static void main(String[] args) throws UnknownHostException, IOException{
		ChattingView chatView = new ChattingView();
		chatView.setVisible(true);
		//Client client = new Client();
	}
}
