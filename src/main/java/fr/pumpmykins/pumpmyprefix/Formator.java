package fr.pumpmykins.pumpmyprefix;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class Formator {

	public static TextComponent format(String prefix,String message) {
		
		ChatColor.translateAlternateColorCodes('&', prefix);
		ChatColor.translateAlternateColorCodes('&', message);
		
		TextComponent tc = new TextComponent(prefix);
		
		TextComponent bet = new TextComponent(">");
		bet.setColor(ChatColor.GOLD);
		tc.addExtra(bet);
		tc.addExtra(message);
		
		return tc;
	}
}
