package fr.pumpmykins.pumpmychat.utils;

import fr.pumpmykins.pumpmychat.ChatPlayer;
import fr.pumpmykins.pumpmychat.Prefix;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
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
        if(event.getMessage().isEmpty()) return;
		if(event.getMessage() == null) return;
		if(event.isCancelled()) return;
        if (!(event.getSender() instanceof ProxiedPlayer)) return;

        ProxiedPlayer player = ((ProxiedPlayer) event.getSender());
		
		String prefix = new String();
		String nickname = new String();
		
		if(this.chatPlayer.hasPrefix(player.getUniqueId())) {
			
			Prefix p = this.chatPlayer.getPrefix().get(player.getUniqueId());
			if(p.getWarn() < 3 && p.isActive()) {
				
				prefix = p.getPrefix();
			}
		}
		
		if(this.chatPlayer.hasNickname(player.getUniqueId())) {
			nickname = this.chatPlayer.getNickname().get(player.getUniqueId());
			if(nickname == null)
				nickname = player.getDisplayName();
		} else {
			nickname = player.getDisplayName();
		}
			
			
		String message = event.getMessage();
		
		prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		
		if(player.hasPermission("pumpmykins.vip.tier2") || player.hasPermission("pumpmykins.vip.tier3")) {
			message = ChatColor.translateAlternateColorCodes('&', message);
		}
		if(player.hasPermission("pumpmykins.vip.tier3"))
			nickname = ChatColor.translateAlternateColorCodes('&', nickname);
		
		TextComponent messages = new TextComponent();
		
		TextComponent name = new TextComponent(nickname);
		name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Pseudo d'origine : "+player.getName()).create()));
		name.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg "+player.getDisplayName()));
		
		TextComponent bet = new TextComponent(" > ");
		bet.setColor(ChatColor.GOLD);
		bet.setBold(true);
				
		if(!prefix.isEmpty()) {
			
			TextComponent tcStartPrefix = new TextComponent("[");
			TextComponent tcEndPrefix = new TextComponent("]");
			tcStartPrefix.setColor(ChatColor.GOLD);
			tcEndPrefix.setColor(ChatColor.GOLD);
			TextComponent tcPrefix = new TextComponent(prefix);
			
			messages.addExtra(tcStartPrefix);
			messages.addExtra(tcPrefix);
			messages.addExtra(tcEndPrefix);
			messages.addExtra(name);
			messages.addExtra(bet);
			messages.addExtra(message);
			
		} else {
			
			name.addExtra(bet);
			name.addExtra(message);
			
			messages = name;
		}
		
		
		for (ProxiedPlayer receiver : player.getServer().getInfo().getPlayers()) {
			receiver.sendMessage(messages);
		}
		
		event.setCancelled(true);
		
	}
}
