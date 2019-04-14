package fr.pumpmykins.pumpmyprefix.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import fr.pumpmykins.pumpmyprefix.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixReloadCommand extends QSubCommand {

	private Map<UUID, String> prefix;
	
	public PrefixReloadCommand(Map<UUID, String> prefix) {
		
		this.prefix = prefix;
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return "prefix.hasone";
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(sender instanceof ProxiedPlayer) {

			ResultSet rs = Main.getMySQL().getResult(Main.REQUEST_GET_USER_PREFIX);
			try {
				
				while(rs.next()) {
					
					if(rs.getInt("warn") < 3) {
						
						String prefix = rs.getString("prefix");
						
						if(prefix != null) {
							
							UUID playerUuid = UUID.fromString(rs.getString("uuid"));
							
							this.prefix.put(playerUuid, prefix);
							
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
