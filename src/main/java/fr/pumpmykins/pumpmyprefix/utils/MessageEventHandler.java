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
		
        if (event.isCommand()) return;
        if (!(event.getSender() instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = ((ProxiedPlayer) event.getSender());
		
		String prefix = new String();
		String nickname = new String();
			
		if(!this.chatPlayer.getPrefix().isEmpty()) {
			prefix = this.chatPlayer.getPrefix().get(player.getUniqueId());
		}
		if(!this.chatPlayer.getNickname().isEmpty()) {
			nickname = this.chatPlayer.getNickname().get(player.getUniqueId());
		}
			
		if(nickname == null)
			nickname = player.getDisplayName();	
			
		TextComponent message = Formator.format(player, nickname ,prefix , event.getMessage());
			
		for (ProxiedPlayer receiver : player.getServer().getInfo().getPlayers()) {
			receiver.sendMessage(message);
		}
		
	}
}
