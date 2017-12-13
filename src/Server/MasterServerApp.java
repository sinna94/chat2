package Server;

import java.io.IOException;

public class MasterServerApp {
	public static void main(String[] args) throws IOException {
		MasterServer msvr = new MasterServer();
		msvr.start();
	}
}
