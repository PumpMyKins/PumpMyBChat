package fr.pumpmybchat.command.nick;

import java.util.List;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.chat.ChatManager;
import fr.pumpmybchat.chat.ChatProfile;
import fr.pumpmybchat.chat.Nickname;
import fr.pumpmybchat.command.utils.ISubCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ActivateNickSubCommand implements ISubCommand {

	private ChatManager chatManager;

	public ActivateNickSubCommand(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {
		
		ProxiedPlayer player = (ProxiedPlayer) sender;
		ChatProfile chatProfile = this.chatManager.getPlayerChatProfile(player);	
		
		if(chatProfile == null) {

			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("ERREUR : Paramètre ChatProfile introuvable");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			player.sendMessage(txt);
			
			TextComponent txt3 = new TextComponent("Contactez le staff !");
			txt3.setColor(ChatColor.RED);
			
			player.sendMessage(txt3);
			return;

		}
		
		Nickname nick = chatProfile.getNickname();
		
		if(nick == null) {
			
			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("ERREUR : Paramètre de surnom introuvable");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			player.sendMessage(txt);
			
			TextComponent txt2 = new TextComponent("Si vous venez d'acheter la fonctionnalité en boutique, patientez quelques minutes.");
			txt2.setColor(ChatColor.RED);		
			player.sendMessage(txt2);
			
			TextComponent txt3 = new TextComponent("Sinon, contactez le staff !");
			txt3.setColor(ChatColor.RED);
			
			player.sendMessage(txt3);
			return;
			
		}
		
		if(nick.isBlocked()) {
			
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
		if(nick.isActive()) {
			active = false;
			sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "§bSurnom désactivé !"));
		}else {
			active = true;
			sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "§bSurnom activé !"));			
		}
		
		try {
			this.chatManager.updatePlayerNickActive(player, active);
			
		} catch (Exception e) {
			e.printStackTrace();
			sender.sendMessage(new TextComponent(e.getMessage()));
		}
		
	}

}
