package fr.pumpmybchat;

import fr.pumpmybchat.utils.InsufisantModificationPrefixException;

public class Prefix {

	private String uuid;
	private String prefix;
	private boolean active;
	private int modification;
	
	public Prefix(String uuid, String prefix, boolean active, int modification) {
		super();
		this.uuid = uuid;
		this.prefix = prefix;
		this.active = active;
		this.modification = modification;
	}
	
	public String getUuid() {
		return uuid;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) throws InsufisantModificationPrefixException {
		
		if(this.modification - 1 <= 0) {
			throw new InsufisantModificationPrefixException(this);
		}
		this.modification-=1;
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
