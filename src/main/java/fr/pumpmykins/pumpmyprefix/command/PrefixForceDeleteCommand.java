package fr.pumpmykins.pumpmyprefix.command;

import fr.pumpmykins.pumpmyprefix.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixForceDeleteCommand extends QSubCommand {

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return "rank.staff.responsable";
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(sender instanceof ProxiedPlayer) {
			
			if(args.length >= 2) {
				
				ProxiedPlayer deletescope = ProxyServer.getInstance().getPlayer(args[1]);
				if(deletescope == null) {
					
					for(ProxiedPlayer i :ProxyServer.getInstance().getPlayers()) {
						
						if(i.getDisplayName().toLowerCase() == args[1].toLowerCase()) {
							
							deletescope = ProxyServer.getInstance().getPlayer(i.getName());
							break;
						} else {
							continue;
						}
					}
					
					if(deletescope == null) {
						
						TextComponent playerunknown = new TextComponent("Joueur inconnue !");
						playerunknown.setColor(ChatColor.DARK_RED);
						sender.sendMessage(playerunknown);
						
					}
				}
				if(deletescope != null) {	
					
					Main.getMySQL().update("DELETE * FROM PrefixPlayer WHERE `uuid` ="+deletescope.getUniqueId());
					
				}
			}
		}
	}

}
