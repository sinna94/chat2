package DTO;

import java.io.Serializable;

public class Friend implements Serializable{
	private static final long serialVersionUID = 1L;
	private String friendId;

	public String getFriendId() {
		return friendId;
	}
	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}
}
