package fr.pumpmykins.pumpmyprefix.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pumpmykins.pumpmyprefix.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixSetCommand extends QSubCommand {

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
		if(sender instanceof ProxiedPlayer) {
			
			if(sender.hasPermission("rank.tier1") || sender.hasPermission("rank.tier2") || sender.hasPermission("rank.tier3")) {
			
				ProxiedPlayer p = (ProxiedPlayer) sender;
				
				String prefix = args[1];
				
				TextComponent desactive = new TextComponent("Préfix set !");
				desactive.setColor(ChatColor.RED);
				sender.sendMessage(desactive);
				
				try {
					
					ResultSet rs = Main.getMySQL().getResult(Main.REQUEST_GET_USER_PREFIX+" WHERE `uuid` = '"+p.getUniqueId()+"'");
					
					if(rs.first()) {
						
						int mod = rs.getInt("modification");
						mod++;
						if(sender.hasPermission("rank.tier2") && mod < 3 || sender.hasPermission("rank.tier3"))
							Main.getMySQL().update("UPDATE `PrefixPlayer` SET `prefix`= '"+prefix+"',`modification`="+mod+" WHERE `uuid`= '"+p.getUniqueId()+"'");
					} else {
						
						Main.getMySQL().update("INSERT INTO `PrefixPlayer`(`uuid`, `prefix`, `active`, `warn`, `modification`) VALUES ('"+p.getUniqueId()+"','"+prefix+"',"+true+","+0+","+1+")");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ProxyServer.getInstance().getPluginManager().dispatchCommand(sender, "prefix reload");
			}
		}
	}

}
