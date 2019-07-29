package fr.pumpmybchat.chat;

import fr.pumpmybchat.utils.InsufisantModificationException;

public class Nickname {

	private String uuid;
	private String nick;
	private boolean active;
	private int modification;
	private boolean blocked;
	
	public Nickname(String uuid, String nick, boolean active, boolean blocked, int modification) {
		super();
		this.uuid = uuid;
		this.nick = nick;
		this.active = active;
		this.blocked = blocked;
		this.modification = modification;
	}
	
	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public String getUuid() {
		return uuid;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String prefix, boolean m) throws InsufisantModificationException {
		
		if(m) {
			
			if(this.modification <= 0) {
				throw new InsufisantModificationException(this);
			}
			this.modification-=1;
			
		}
		this.nick = prefix;
		
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getModification() {
		return modification;
	}
	public void setModification(int modification) {
		this.modification = modification;
	}	
}
