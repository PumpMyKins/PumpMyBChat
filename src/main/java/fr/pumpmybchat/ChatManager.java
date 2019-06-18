package fr.pumpmybchat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatManager implements Listener {

	private Map<String , ChatProfile> profiles;
	private ConfigManager configManager;
	private MySql mySQL;

	public ChatManager(ConfigManager configManager, MySql mySQL) {
		this.profiles = new HashMap<String, ChatProfile>();
		this.configManager = configManager;
		this.mySQL = mySQL;
	}

	private void addProfile(UUID uuid, ChatProfile chatProfile) throws Exception {

		if(this.profiles.containsKey(uuid.toString())) {
			throw new Exception("ChatProfile \"" + uuid.toString() + "\" already exists");
		}else {
			this.profiles.put(uuid.toString(), chatProfile);
		}

	}

	private ChatProfile getProfile(UUID uuid) {
		return this.profiles.get(uuid.toString());		
	}
	
	@EventHandler
	public void onProxiedPlayerJoin(PostLoginEvent event) {
		
		
		
	}
	
	public void onProxiedPlayerLeave(PlayerDisconnectEvent event) {
		
			
		
	}

	public void initSQL() throws Exception {
		String createprefixtable = "CREATE TABLE IF NOT EXISTS PrefixPlayer(`uuid` VARCHAR(191) NOT NULL UNIQUE, `prefix` VARCHAR(191), `active` TINYINT NOT NULL DEFAULT 0, `warn` INT NOT NULL DEFAULT 0, `modification` INT NOT NULL DEFAULT 0)";
		this.mySQL.sendUpdate(createprefixtable);		
	}
	
}
