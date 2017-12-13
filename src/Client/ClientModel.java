package Client;

import java.util.ArrayList;

import DTO.Friend;

public class ClientModel { // 클라이언트의 데이터를 가지고있는 클래스(model)
	private String myId;
	private ArrayList<Friend> myFriends;
	
	public String getMyId() {
		return myId;
	}

	public void setMyId(String myId) {
		this.myId = myId;
	}

	public ArrayList<Friend> getMyFriends() {
		return myFriends;
	}

	public void setMyFriends(ArrayList<Friend> myFriends) {
		this.myFriends = myFriends;
	}
}
