package fr.pumpmykins.pumpmychat.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fr.pumpmykins.pumpmychat.ChatPlayer;
import fr.pumpmykins.pumpmychat.Main;
import fr.pumpmykins.pumpmychat.MySql;

public class PrefixInitialisation {

	private ChatPlayer chatPlayer;

	public PrefixInitialisation(ChatPlayer cp) {

		this.chatPlayer = new ChatPlayer();

		Map<UUID, String> prefixList = new HashMap<UUID, String>();

		try {
			MySql mySQL = Main.getMySQL();

			ResultSet rs = mySQL.getResult(Main.REQUEST_GET_USER_PREFIX);

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
