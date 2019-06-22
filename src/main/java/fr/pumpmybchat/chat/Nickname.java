package fr.pumpmybchat.chat;

public class Nickname {

	private boolean active;
	private String nickname;
	
	public Nickname() {
		this.active = false;
		this.nickname = "";
	}

	public boolean hasOne() {
		return active;
	}

	public String getUnSafeNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
		this.active = true;
	}
	
	public void unsetNickname() {
		this.nickname = "";
		this.active = false;		
	}
	
	
	
}
