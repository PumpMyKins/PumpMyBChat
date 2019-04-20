package fr.pumpmykins.pumpmyprefix.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

import fr.pumpmykins.pumpmyprefix.ChatPlayer;
import fr.pumpmykins.pumpmyprefix.Main;
import fr.pumpmykins.pumpmyprefix.MySql;

public class PrefixInitialisation {
	
	private ChatPlayer chatPlayer;
	
	public PrefixInitialisation(ChatPlayer cp) {
		
		this.chatPlayer = cp;
		
		Map<UUID, String> prefixList = chatPlayer.getPrefix();
		
		MySql mySQL = Main.getMySQL();
		
		if(mySQL.isConnected()) {
			
			ResultSet rs = Main.getMySQL().getResult(Main.REQUEST_GET_USER_PREFIX);
			try {
				
				while(rs.next()) {
					
					if(rs.getInt("warn") < 3) {
						
						String prefix = rs.getString("prefix");
						
						if(prefix != null) {
							
							UUID playerUuid = UUID.fromString(rs.getString("uuid"));
							
							prefixList.put(playerUuid, prefix);
							
						}
					}
				}
			this.chatPlayer.setPrefix(prefixList);
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}