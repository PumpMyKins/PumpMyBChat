package fr.pumpmykins.pumpmychat.command;

import java.sql.SQLException;

import fr.pumpmykins.pumpmychat.Main;
import fr.pumpmykins.pumpmychat.MySql;
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

			if(sender.hasPermission("rank.staff.responsable"))

				if(args.length >= 2) {

					ProxiedPlayer deletescope = ProxyServer.getInstance().getPlayer(args[1]);
					if(deletescope == null) {

						for(ProxiedPlayer i : ProxyServer.getInstance().getPlayers()) {

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

						try {
							MySql mySQL = Main.getMySQL();
							
							mySQL.update("DELETE FROM PrefixPlayer WHERE `uuid` = '"+deletescope.getUniqueId()+"'");
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {

						TextComponent activate = new TextComponent("Connection à la base de donnée impossible !");
						activate.setColor(ChatColor.RED);
						sender.sendMessage(activate);
					}
				}
		}
	}
}