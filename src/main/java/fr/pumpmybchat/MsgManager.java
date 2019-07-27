package fr.pumpmybchat;

import java.util.HashMap;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MsgManager implements Listener {

	private HashMap<String,CommandSender> msg;
	
	public MsgManager() {
		this.msg = new HashMap<String, CommandSender>();
	}
	
	public void addLastMessageSender(CommandSender target, CommandSender sender) {
		
		if(msg.containsKey(target.getName())) {
			msg.replace(target.getName(), sender);
		}else {
			msg.put(target.getName(), sender);
		}	
		
	}
	
	public CommandSender getLastMessageSenderName(CommandSender target) throws Exception {
		
		if(!this.msg.containsKey(target.getName())) {
			
			throw new Exception("Unfound");
			
		}else {
			
			return this.msg.get(target.getName());
			
		}
		
	}
	
	public void clearPlayerLastMessage(CommandSender player) {
		
		msg.remove(player.getName());
		
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerDisconnectEvent event) {
		
		this.clearPlayerLastMessage(event.getPlayer());
		
	}
	
}
