package fr.pumpmykins.pumpmyprefix.command;

import java.sql.ResultSet;
import java.sql.SQLException;

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
		return "prefix.responsable";
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;
			
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
					ResultSet rs = Main.getMySQL().getResult(Main.REQUEST_GET_USER_PREFIX+deletescope.getUniqueId());
					try {
						if(rs.next()) {
							
							TextComponent error = new TextComponent("Erreur de la suppression contacter le responsable !");
							error.setColor(ChatColor.DARK_RED);
							sender.sendMessage(error);
						} else {
							
							TextComponent success = new TextComponent("Prefix Supprimer avec succès !");
							success.setColor(ChatColor.DARK_GREEN);
							sender.sendMessage(success);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

}
