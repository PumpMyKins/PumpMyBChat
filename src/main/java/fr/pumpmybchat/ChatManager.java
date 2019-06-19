package fr.pumpmybchat;

import java.sql.ResultSet;
import java.util.AbstractMap.SimpleEntry;
import fr.pumpmybchat.logging.CustomLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
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
	private Main main;

	public ChatManager(Main m,ConfigManager configManager, MySql mySQL) throws Exception {
		this.profiles = new HashMap<String, ChatProfile>();
		this.configManager = configManager;
		this.mySQL = mySQL;
		this.main = m;

		this.initMySql();

	}

	private void addProfile(String uuid, ChatProfile chatProfile) throws Exception {

		if(this.profiles.containsKey(uuid.toString())) {
			throw new Exception("ChatProfile \"" + uuid.toString() + "\" already exists");
		}else {
			this.profiles.put(uuid.toString(), chatProfile);
		}

	}

	private ChatProfile getProfile(String uuid) {
		return this.profiles.get(uuid.toString());		
	}

	private void deleteProfile(String uuid) {		
		this.profiles.remove(uuid.toString());
	}

	private void initMySql() throws Exception {
		String createtable = "CREATE TABLE IF NOT EXISTS `prefixplayer` ( `uuid` VARCHAR(50) NOT NULL , `prefix` VARCHAR(50) NULL DEFAULT NULL , `active` BOOLEAN NOT NULL , `modification` INT NOT NULL , PRIMARY KEY (`uuid`)) ENGINE = InnoDB;";
		this.mySQL.sendUpdate(createtable);	
		createtable = "CREATE TABLE IF NOT EXISTS `prefixhistory` ( `uuid` VARCHAR(50) NOT NULL , `prefix` VARCHAR(50) NOT NULL , `date` DATETIME NOT NULL DEFAULT NOW() ) ENGINE = InnoDB;";
		this.mySQL.sendUpdate(createtable);
		createtable = "CREATE TABLE IF NOT EXISTS `nickhistory` ( `uuid` VARCHAR(50) NOT NULL , `nick` VARCHAR(50) NOT NULL , `date` DATETIME NOT NULL DEFAULT NOW() ) ENGINE = InnoDB;";
		this.mySQL.sendUpdate(createtable);
	}

	private List<SimpleEntry<String,String>> getMySqlNickHistory(String uuid) throws Exception {

		List<SimpleEntry<String,String>> l = new ArrayList<>();

		ResultSet rs = this.mySQL.sendQuery("SELECT `nick`, `date` FROM `nickhistory` WHERE `uuid`=\"" + uuid + "\";");
		while (rs.next()) {

			SimpleEntry<String, String> simpleEntry = new SimpleEntry<String, String>(rs.getString("nick"),rs.getString("date"));
			l.add(simpleEntry);

		}

		return l;		

	}

	private void addNickInMySqlHistory(String uuid,String nick) throws Exception {

		this.mySQL.sendUpdate("INSERT INTO `nickhistory`(`uuid`, `nick`) VALUES (\"" + uuid + "\",\"" + nick + "\");");

	}
	
	public void setNickname(ProxiedPlayer player, String string) throws Exception {

		String uuid = player.getUniqueId().toString();
		ChatProfile chatProfile = this.getProfile(uuid);
		chatProfile.getNickname().setNickname(string);
		this.addNickInMySqlHistory(uuid, string);
		
	}

	public void unsetNickname(ProxiedPlayer player) {
		
		ChatProfile chatProfile = this.getProfile(player.getUniqueId().toString());
		chatProfile.getNickname().unsetNickname();
		
	}

	public boolean hasNickname(ProxiedPlayer player) {
		
		return this.getProfile(player.getUniqueId().toString()).getNickname().hasOne();
		
	}

	private List<SimpleEntry<String,String>> getMySqlPrefixHistory(String uuid) throws Exception {

		List<SimpleEntry<String,String>> l = new ArrayList<>();

		ResultSet rs = this.mySQL.sendQuery("SELECT `prefix`, `date` FROM `prefixhistory` WHERE `uuid`=\"" + uuid + "\";");
		while (rs.next()) {

			SimpleEntry<String, String> simpleEntry = new SimpleEntry<String, String>(rs.getString("prefix"),rs.getString("date"));
			l.add(simpleEntry);

		}

		return l;
	}

	/*private void addPrefixInMySqlHistory(String uuid, String prefix) throws Exception {

		this.mySQL.sendUpdate("INSERT INTO `prefixhistory`(`uuid`, `prefix`) VALUES (\"" + uuid + "\",\"" + prefix + "\");");

	}*/

	private Prefix getMySqlPrefix(String uuid) {
		return new Prefix(null, null, false, false, 0, 0);
	}

	/*private void setMySqlPrefix(String uuid, Prefix prefix) throws Exception {

		this.mySQL.sendUpdate("");

	}

	private void unsetMySqlPrefix(String uuid) throws Exception {

		this.mySQL.sendUpdate("");

	}*/

	@EventHandler
	public void onProxiedPlayerJoin(PostLoginEvent event) {

		ProxiedPlayer player = event.getPlayer();
		String uuid = player.getUniqueId().toString();

		List<SimpleEntry<String, String>> prefixHistory = new ArrayList<>();
		try {
			prefixHistory = this.getMySqlPrefixHistory(uuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<SimpleEntry<String, String>> nickHistory = new ArrayList<>();
		try {
			nickHistory = this.getMySqlNickHistory(uuid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Prefix prefix = this.getMySqlPrefix(uuid);	

		try {
			this.addProfile(event.getPlayer().getUniqueId().toString(), new ChatProfile(prefix,prefixHistory,nickHistory));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onProxiedPlayerLeave(PlayerDisconnectEvent event) {

		this.deleteProfile(event.getPlayer().getUniqueId().toString());

	}

	@EventHandler
	public void onMessage(ChatEvent event) {

		if (event.isCommand()) return;
		if(event.getMessage() == null) return;
		if(event.getMessage().isEmpty()) return;
		if(event.isCancelled()) return;
		if (!(event.getSender() instanceof ProxiedPlayer)) return;

		ProxiedPlayer player = ((ProxiedPlayer) event.getSender());
		ChatProfile chatProfile = this.getProfile(player.getUniqueId().toString());

		TextComponent messages = new TextComponent();
		TextComponent tcStartPrefix = new TextComponent("[");
		tcStartPrefix.setColor(ChatColor.GOLD);
		
		if(player.hasPermission("pumpmybchat.prefix.staff")) {
			TextComponent staff = new TextComponent("۞");
			staff.setBold(true);
			staff.setColor(ChatColor.DARK_RED);
			messages.addExtra(staff);
		}
		
		messages.addExtra(tcStartPrefix);

		Prefix p = chatProfile.getPrefix();
		if(p!= null && p.isActive()) {

			TextComponent tcEndPrefix = new TextComponent("] §r");
			tcEndPrefix.setColor(ChatColor.GOLD);
			TextComponent tcPrefix = new TextComponent("§r" + p.getPrefix() + "§r");

			messages.addExtra(tcPrefix);
			messages.addExtra(tcEndPrefix);

		}
		
		Nickname nickname = chatProfile.getNickname();
		if(nickname.hasOne()) {
			
			messages.addExtra(new TextComponent(nickname.getUnSafeNickname()));
			
		}else {
			
			messages.addExtra(new TextComponent(player.getDisplayName()));
			
		}

		TextComponent bet = new TextComponent(" > ");
		bet.setColor(ChatColor.GOLD);
		bet.setBold(true);
		
		messages.addExtra(bet);
		
		messages.addExtra(new TextComponent(event.getMessage()));

		/*String prefix = new String();
		String nickname = new String();

		if(this.chatPlayer.hasPrefix(player.getUniqueId())) {

			Prefix p = this.chatPlayer.getPrefix().get(player.getUniqueId());
			if(p.getWarn() < 3 && p.isActive()) {

				prefix = p.getPrefix();
			}
		}

		if(this.chatPlayer.hasNickname(player.getUniqueId())) {
			nickname = this.chatPlayer.getNickname().get(player.getUniqueId());
			if(nickname == null)
				nickname = player.getDisplayName();
		} else {
			nickname = player.getDisplayName();
		}


		String message = event.getMessage();

		prefix = ChatColor.translateAlternateColorCodes('&', prefix);

		if(player.hasPermission("pumpmykins.vip.tier2") || player.hasPermission("pumpmykins.vip.tier3")) {
			message = ChatColor.translateAlternateColorCodes('&', message);
		}
		if(player.hasPermission("pumpmykins.vip.tier3"))
			nickname = ChatColor.translateAlternateColorCodes('&', nickname);

		TextComponent messages = new TextComponent();

		TextComponent name = new TextComponent(nickname);
		name.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Pseudo d'origine : "+player.getName()).create()));
		name.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg "+player.getDisplayName()));

		TextComponent bet = new TextComponent(" > ");
		bet.setColor(ChatColor.GOLD);
		bet.setBold(true);

		if(!prefix.isEmpty()) {

			TextComponent tcStartPrefix = new TextComponent("[");
			TextComponent tcEndPrefix = new TextComponent("]");
			tcStartPrefix.setColor(ChatColor.GOLD);
			tcEndPrefix.setColor(ChatColor.GOLD);
			TextComponent tcPrefix = new TextComponent(prefix);

			messages.addExtra(tcStartPrefix);
			messages.addExtra(tcPrefix);
			messages.addExtra(tcEndPrefix);
			messages.addExtra(name);
			messages.addExtra(bet);
			messages.addExtra(message);

		} else {

			name.addExtra(bet);
			name.addExtra(message);

			messages = name;
		}
		 */

		for (ProxiedPlayer receiver : player.getServer().getInfo().getPlayers()) {
			receiver.sendMessage(messages);
		}

		event.setCancelled(true);

	}

	public void setNickname(ProxiedPlayer player, String string) throws Exception {

	}

	public void unsetNickname(ProxiedPlayer player) {
		// TODO Auto-generated method stub

	}

	public boolean hasNickname(ProxiedPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setPrefix(ProxiedPlayer player, String string) throws Exception {



	}

	public void unsetPrefix(ProxiedPlayer player) {
		// TODO Auto-generated method stub

	}

	public boolean hasPrefix(ProxiedPlayer player) {
		// TODO Auto-generated method stub
		return false;
	}

}
