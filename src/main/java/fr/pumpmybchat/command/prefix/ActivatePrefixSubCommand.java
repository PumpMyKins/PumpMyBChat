package fr.pumpmybchat.command.prefix;

import java.util.List;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.chat.ChatManager;
import fr.pumpmybchat.chat.ChatProfile;
import fr.pumpmybchat.chat.Prefix;
import fr.pumpmybchat.command.utils.ISubCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ActivatePrefixSubCommand implements ISubCommand {

	private ChatManager chatManager;

	public ActivatePrefixSubCommand(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {
		
		ProxiedPlayer player = (ProxiedPlayer) sender;
		ChatProfile chatProfile = this.chatManager.getPlayerChatProfile(player);		
		Prefix prefix = chatProfile.getPrefix();
		
		if(prefix == null) {
			
			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("ERREUR : Paramètre de prefix introuvable");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			player.sendMessage(txt);
			
			TextComponent txt2 = new TextComponent("Contactez le staff !");
			txt2.setColor(ChatColor.RED);
			
			player.sendMessage(txt2);
			return;
			
		}
		
		if(prefix.isBlocked()) {
			
			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Cette fonctionnalité vous a été retiré suite à un abus !");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			player.sendMessage(txt);
			
			TextComponent txt2 = new TextComponent("Contactez le staff !");
			txt2.setColor(ChatColor.RED);
			
			player.sendMessage(txt2);
			return;
			
		}
		
		boolean active;
		if(prefix.isActive()) {
			active = false;
			sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "§bPrefix désactivé !"));
		}else {
			active = true;
			sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "§bPrefix activé !"));			
		}
		
		try {
			this.chatManager.updatePlayerPrefixActive(player, active);
			
		} catch (Exception e) {
			e.printStackTrace();
			sender.sendMessage(new TextComponent(e.getMessage()));
		}
		
	}

}
