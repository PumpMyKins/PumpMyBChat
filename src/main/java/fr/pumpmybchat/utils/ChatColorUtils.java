package fr.pumpmybchat.utils;

import net.md_5.bungee.api.ChatColor;

public class ChatColorUtils {

	public static boolean containsChatColorCodes(String msg) {
		
		return msg.contains("&") && !msg.equals(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', msg)));
		
	}
	
	public static String getChatColorCodesTranslatedString(String msg) {
		
		return ChatColor.translateAlternateColorCodes('&', msg);
		
	}
	
	public static String getWithoutChatColorCodesString(String msg) {
		
		return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', msg));
		
	}
	
}
