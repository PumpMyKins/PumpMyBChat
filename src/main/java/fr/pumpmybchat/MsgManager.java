package fr.pumpmybchat;

import java.util.HashMap;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MsgManager implements Listener {

	private HashMap<CommandSender,CommandSender> msg;
	
	public MsgManager() {
		msg = new HashMap<CommandSender, CommandSender>();
	}
	
	public void addLastMessageSender(CommandSender target, CommandSender sender) {
		
		
		if(msg.containsKey(target)) {
			msg.replace(target, sender);
		}else {
			msg.put(target, sender);
		}	
	}
	
	public CommandSender getLastMessageSenderName(CommandSender target) throws Exception {
		
		if(!this.msg.containsKey(target)) {
			
			throw new Exception("Unfound");
			
		}else {
			
			return this.msg.get(target);
			
		}
		
	}
	
	public void clearPlayerLastMessage(CommandSender player) {
		
		msg.remove(player);
		
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerDisconnectEvent event) {
		
		this.clearPlayerLastMessage(event.getPlayer());
		
	}
	
}
