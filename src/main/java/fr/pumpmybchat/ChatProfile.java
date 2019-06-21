package fr.pumpmybchat;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public class ChatProfile {
	
	private Nickname nickname;
	private Prefix prefix;
	private List<SimpleEntry<String, SimpleEntry<String, Boolean>>> prefixHistory;
	private List<SimpleEntry<String, String>> nickHistory;
	
	public ChatProfile(Prefix prefix, List<SimpleEntry<String, SimpleEntry<String, Boolean>>> prefixHistory, List<SimpleEntry<String, String>> nickHistory) {
		this.nickname = new Nickname();
		this.prefix = prefix;
		this.prefixHistory = prefixHistory;
		this.nickHistory = nickHistory;
	}

	public List<SimpleEntry<String, SimpleEntry<String, Boolean>>> getPrefixHistory() {
		return prefixHistory;
	}

	public List<SimpleEntry<String, String>> getNickHistory() {
		return nickHistory;
	}

	public Nickname getNickname() {
		return this.nickname;
	}

	public Prefix getPrefix() {
		return this.prefix;
	}

}
