package fr.pumpmykins.pumpmyprefix.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import fr.pumpmykins.pumpmyprefix.ChatPlayer;
import fr.pumpmykins.pumpmyprefix.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixReloadCommand extends QSubCommand {

	private ChatPlayer chatPlayer;
	
	public PrefixReloadCommand(ChatPlayer cp) {
		
		this.chatPlayer = cp;
	}

	@Override
	public String getPermission() {
		
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(sender instanceof ProxiedPlayer) {

			if(sender.hasPermission("rank.tier1") || sender.hasPermission("rank.tier2") || sender.hasPermission("rank.tier3")) {
			
				ResultSet rs = Main.getMySQL().getResult(Main.REQUEST_GET_USER_PREFIX);
				try {
					
					while(rs.next()) {
						
						if(rs.getInt("warn") < 3) {
							
							String prefix = rs.getString("prefix");
							
							if(prefix != null) {
								
								UUID playerUuid = UUID.fromString(rs.getString("uuid"));
								
								Map<UUID, String> prefixList = this.chatPlayer.getPrefix();
								
								prefixList.remove(playerUuid);
								prefixList.put(playerUuid, prefix);
								
								this.chatPlayer.setPrefix(prefixList);
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
