package Server;

import java.io.IOException;

public class MasterServerApp {
	public static void main(String[] args) throws IOException {
		MasterServer msvr = new MasterServer(); // 마스터서버 객체 생성
		msvr.start(); // 서버 시작
	}
}
