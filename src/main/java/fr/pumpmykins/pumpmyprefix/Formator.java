package fr.pumpmykins.pumpmyprefix;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Formator {

	public static TextComponent format(ProxiedPlayer pp, String n,String prefix, String message) {
		
		ChatColor.translateAlternateColorCodes('&', prefix);
		
		if(pp.hasPermission("rank.tier2") || pp.hasPermission("rank.tier3")) {
			ChatColor.translateAlternateColorCodes('&', message);
		}
		TextComponent name = new TextComponent(n);
		
		TextComponent bet = new TextComponent(" > ");
		bet.setColor(ChatColor.GOLD);
		
		if(!prefix.isEmpty()) {
			
			TextComponent tcStartPrefix = new TextComponent("[");
			TextComponent tcEndPrefix = new TextComponent("] ");
			TextComponent tcPrefix = new TextComponent(prefix);
			
			tcStartPrefix.addExtra(tcPrefix);
			tcStartPrefix.addExtra(tcEndPrefix);
			tcStartPrefix.addExtra(" ");
			tcStartPrefix.addExtra(name);
			tcStartPrefix.addExtra(bet);
			tcStartPrefix.addExtra(message);
			
			return tcStartPrefix;
		} else {
			
			name.addExtra(bet);
			name.addExtra(message);
			
			return name;
		}
	}
}
