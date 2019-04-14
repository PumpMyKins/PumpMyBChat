package fr.pumpmykins.pumpmyprefix.command;

import fr.pumpmykins.pumpmyprefix.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixSetCommand extends QSubCommand {

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return "prefix.hasone";
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			
			String prefix = args[0];
			
			TextComponent desactive = new TextComponent("Préfix supprimer !");
			desactive.setColor(ChatColor.RED);
			sender.sendMessage(desactive);
					
			Main.getMySQL().update("UPDATE `PrefixPlayer` SET `prefix`="+prefix+" WHERE `uuid`= `"+p.getUniqueId()+"`");
		}
	}

}
