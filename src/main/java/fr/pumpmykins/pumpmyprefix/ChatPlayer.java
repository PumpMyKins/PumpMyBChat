package fr.pumpmykins.pumpmyprefix;

import java.util.Map;
import java.util.UUID;

public class ChatPlayer {

	private Map<UUID , String> prefix;
	private Map<UUID , String> nickname;	
	
	public Map<UUID, String> getPrefix() {
		return prefix;
	}
	public void setPrefix(Map<UUID, String> prefix) {
		this.prefix = prefix;
	}
	public Map<UUID, String> getNickname() {
		return nickname;
	}
	public void setNickname(Map<UUID, String> nickname) {
		this.nickname = nickname;
	}
	
	
	
}
