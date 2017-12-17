package Client;

import java.io.IOException;
import java.net.UnknownHostException;

public class ClientApp {
	public static void main(String[] args) throws UnknownHostException, IOException{
		MainView mainView = new MainView();
		mainView.setVisible(true);
		//Client client = new Client();
	}
}
