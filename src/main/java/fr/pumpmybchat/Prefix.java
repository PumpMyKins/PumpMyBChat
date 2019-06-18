package fr.pumpmybchat;

import java.util.UUID;

public class Prefix {

	private UUID uuid;
	private String prefix;
	private boolean active;
	private int warn;
	private int modification;
	
	public Prefix(UUID uuid, String prefix, boolean staff, boolean active, int warn, int modification) {
		super();
		this.uuid = uuid;
		this.prefix = prefix;
		this.active = active;
		this.warn = warn;
		this.modification = modification;
	}
	
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getWarn() {
		return warn;
	}
	public void setWarn(int warn) {
		this.warn = warn;
	}
	public int getModification() {
		return modification;
	}
	public void setModification(int modification) {
		this.modification = modification;
	}	
}
