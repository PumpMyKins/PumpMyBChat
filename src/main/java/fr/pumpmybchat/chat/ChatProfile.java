package fr.pumpmybchat.chat;

import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public class ChatProfile {
	
	private Nickname nick;
	private Prefix prefix;
	private List<SimpleEntry<String, SimpleEntry<String, Boolean>>> prefixHistory;
	private List<SimpleEntry<String, SimpleEntry<String, Boolean>>> nickHistory;
	
	public ChatProfile(Prefix prefix, Nickname nick, List<SimpleEntry<String, SimpleEntry<String, Boolean>>> prefixHistory, List<SimpleEntry<String, SimpleEntry<String, Boolean>>> nickHistory) {
		this.nick = nick;
		this.prefix = prefix;
		this.prefixHistory = prefixHistory;
		this.nickHistory = nickHistory;
	}

	public List<SimpleEntry<String, SimpleEntry<String, Boolean>>> getPrefixHistory() {
		return prefixHistory;
	}

	public List<SimpleEntry<String, SimpleEntry<String, Boolean>>> getNickHistory() {
		return nickHistory;
	}

	public Nickname getNickname() {
		return this.nick;
	}

	public Prefix getPrefix() {
		return this.prefix;
	}
	
	public void setPrefix(Prefix prefix) throws Exception {
		
		if(this.prefix != null && prefix != null){			
			throw new Exception("Prefix already set in ChatProlies");			
		}
		
		this.prefix = prefix;
	}
	
	public void setNick(Nickname nick) throws Exception {
		
		if(this.nick != null && nick != null){			
			throw new Exception("Prefix already set in ChatProlies");			
		}
		
		this.nick = nick;
	}

}
