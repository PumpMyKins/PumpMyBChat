package fr.pumpmybchat.chat;

import fr.pumpmybchat.utils.InsufisantModificationPrefixException;

public class Prefix {

	private String uuid;
	private String prefix;
	private boolean active;
	private int modification;
	private boolean blocked;
	
	public Prefix(String uuid, String prefix, boolean active, boolean blocked, int modification) {
		super();
		this.uuid = uuid;
		this.prefix = prefix;
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
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix, boolean m) throws InsufisantModificationPrefixException {
		
		if(m) {
			
			if(this.modification <= 0) {
				throw new InsufisantModificationPrefixException(this);
			}
			this.modification-=1;
			
		}
		this.prefix = prefix;
		
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
