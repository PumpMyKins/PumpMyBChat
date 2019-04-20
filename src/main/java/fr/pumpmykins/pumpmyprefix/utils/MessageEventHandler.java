package fr.pumpmykins.pumpmyprefix.utils;

import fr.pumpmykins.pumpmyprefix.ChatPlayer;
import fr.pumpmykins.pumpmyprefix.Formator;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MessageEventHandler implements Listener {

	
	private ChatPlayer chatPlayer;
	
	public MessageEventHandler(ChatPlayer cp) {
		
		this.chatPlayer = cp;
	}

	@EventHandler
	public void onMessage(ChatEvent event) {
		
		if(!event.isCommand()) {

			event.setCancelled(true);
			
			ProxiedPlayer pp = null;
			
			for(ProxiedPlayer pa : ProxyServer.getInstance().getPlayers()) {
				
				if(event.getSender() == pa.getAddress())
				{
					pp = pa;
					break;
				}
			}
			String prefix = new String();
			String nickname = new String();
			
			if(!this.chatPlayer.getPrefix().isEmpty()) {
				prefix = this.chatPlayer.getPrefix().get(pp.getUniqueId());
			}
			if(!this.chatPlayer.getNickname().isEmpty()) {
				nickname = this.chatPlayer.getNickname().get(pp.getUniqueId());
			}
			
			if(nickname == null)
				nickname = pp.getDisplayName();	
			
			TextComponent message = Formator.format(pp, nickname ,prefix , event.getMessage());
			
			ProxyServer.getInstance().broadcast(message);
		}
	}
}
