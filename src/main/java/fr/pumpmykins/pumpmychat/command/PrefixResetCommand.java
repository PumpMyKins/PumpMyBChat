package fr.pumpmykins.pumpmychat.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pumpmykins.pumpmychat.Main;
import fr.pumpmykins.pumpmychat.MySql;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixResetCommand extends QSubCommand {

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {

		if(sender instanceof ProxiedPlayer) {

			ProxiedPlayer p = (ProxiedPlayer) sender;
			try {
				MySql mySQL = Main.getMySQL();
				ResultSet rs = mySQL.getResult(Main.REQUEST_GET_USER_PREFIX +" WHERE `uuid` = '"+p.getUniqueId()+"'");

				if(!rs.next()) {

					sender.sendMessage(Main.getERROR_NO_PREFIX());

				} else {

					rs.first();
					String prefix = rs.getString("prefix");
					prefix = "";

					TextComponent desactive = new TextComponent("Préfix supprimer !");
					desactive.setColor(ChatColor.RED);
					sender.sendMessage(desactive);

					mySQL.update("UPDATE `PrefixPlayer` SET `prefix`= '"+prefix+"' WHERE `uuid`= '"+p.getUniqueId()+"'");

					ProxyServer.getInstance().getPluginManager().dispatchCommand(sender, "prefix reload");

				}
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

