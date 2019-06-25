package fr.pumpmybchat;

import java.util.HashMap;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MsgManager {

	private HashMap<String, String> msg;
	
	public MsgManager() {
		msg = new HashMap<String, String>();
	}
	
	public void addLastMessageSender(ProxiedPlayer target, ProxiedPlayer sender) {
		
		String targetUuid = target.getUniqueId().toString();
		String senderName = sender.getName();
		
		if(msg.containsKey(targetUuid)) {
			msg.replace(targetUuid, senderName);
		}else {
			msg.put(targetUuid, senderName);
		}	
	}
	
	public String getLastMessageSenderName(ProxiedPlayer target) {
		
		String targetUuid = target.getUniqueId().toString();
		
		if(this.msg.containsKey(targetUuid)) {
			
			return this.msg.get(targetUuid);
			
		}else {
			
			throw new NullPointerException("Unfound");
			
		}
		
	}
	
	public void clearPlayerLastMessage(ProxiedPlayer player) {
		
		String playerUuid = player.getUniqueId().toString();
		msg.remove(playerUuid);
		
	}
	
}
