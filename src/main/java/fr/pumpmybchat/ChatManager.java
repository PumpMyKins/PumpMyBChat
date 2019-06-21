package fr.pumpmybchat;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.AbstractMap.SimpleEntry;
import fr.pumpmybchat.logging.CustomLevel;
import fr.pumpmybchat.utils.ChatColorUtils;
import fr.pumpmybchat.utils.DiscordWebhook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
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

	private void addPlayerChatProfile(String uuid, ChatProfile chatProfile) throws Exception {

		if(this.profiles.containsKey(uuid.toString())) {
			throw new Exception("ChatProfile \"" + uuid.toString() + "\" already exists");
		}else {
			this.profiles.put(uuid.toString(), chatProfile);
		}

	}

	public ChatProfile getPlayerChatProfile(ProxiedPlayer player) {
		return this.profiles.get(player.getUniqueId().toString());		
	}

	private void deletePlayerChatProfile(String uuid) {		
		this.profiles.remove(uuid.toString());
	}

	private void initMySql() throws Exception {
		String createtable = "CREATE TABLE IF NOT EXISTS `prefixplayer` ( `uuid` VARCHAR(50) NOT NULL , `prefix` VARCHAR(50) NOT NULL DEFAULT 'I-LOVE-PMK' , `active` BOOLEAN NOT NULL DEFAULT '1' , `modification` INT NOT NULL , PRIMARY KEY (`uuid`)) ENGINE = InnoDB;";
		this.mySQL.sendUpdate(createtable);	
		createtable = "CREATE TABLE IF NOT EXISTS `prefixhistory` ( `uuid` VARCHAR(50) NOT NULL , `prefix` VARCHAR(50) NOT NULL , `active` BOOLEAN NOT NULL , `date` DATETIME NOT NULL DEFAULT NOW() ) ENGINE = InnoDB;";
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

	private void addPlayerNickInMySqlHistory(String uuid,String nick) throws Exception {

		this.mySQL.sendUpdate("INSERT INTO `nickhistory`(`uuid`, `nick`) VALUES (\"" + uuid + "\",\"" + nick + "\");");

	}
	
	public void setPlayerNickname(ProxiedPlayer player, String string) throws Exception {

		String uuid = player.getUniqueId().toString();
		ChatProfile chatProfile = this.getPlayerChatProfile(uuid);
		chatProfile.getNickname().setNickname(string);
		this.addPlayerNickInMySqlHistory(uuid, string);
		
	}

	public void unsetPlayerNickname(ProxiedPlayer player) {
		
		ChatProfile chatProfile = this.getPlayerChatProfile(player.getUniqueId().toString());
		chatProfile.getNickname().unsetNickname();
		
	}

	public boolean playerHasNickname(ProxiedPlayer player) {
		
		return this.getPlayerChatProfile(player.getUniqueId().toString()).getNickname().hasOne();
		
	}

	private List<SimpleEntry<String,SimpleEntry<String, Boolean>>> getMySqlPrefixHistory(String uuid) throws Exception {

		List<SimpleEntry<String,SimpleEntry<String, Boolean>>> l = new ArrayList<>();

		ResultSet rs = this.mySQL.sendQuery("SELECT `prefix`, `àctive`, `date` FROM `prefixhistory` WHERE `uuid`=\"" + uuid + "\";");
		while (rs.next()) {

			SimpleEntry<String,SimpleEntry<String, Boolean>> simpleEntry = new SimpleEntry<String,SimpleEntry<String, Boolean>>(rs.getString("date"), new SimpleEntry<String, Boolean>(rs.getString("prefix"),rs.getBoolean("active")));
			l.add(simpleEntry);

		}

		return l;
	}

	private void addPrefixInMySqlHistory(String uuid, String prefix, boolean active) throws Exception {

		this.mySQL.sendUpdate("INSERT INTO `prefixhistory`(`uuid`, `prefix`, `active`) VALUES (\"" + uuid + "\",\"" + prefix + "\",\"" + active + "\");");

	}
	
	public void initPlayerPrefix(ProxiedPlayer player, int modification) {
		
				
		
	}
	
	private void initMySqlPrefix(String uuid) throws Exception {

		this.mySQL.sendUpdate("");

	}
	
	public void deletePlayerPrefix(String uuid) {
		// lors de la fin de l'achat
		
		
	}
	
	public Prefix getPlayerPrefix(String uuid) {
		
		return null;
		
	}
	
	public boolean isPlayerActivatePrefix(String uuid) {
		
		return false;
		
	}

	private Prefix getMySqlPrefix(String uuid) {
		return new Prefix(null, null, false, false, 0, 0);
	}

	private void setMySqlPrefix(String uuid, Prefix prefix) throws Exception {

		this.mySQL.sendUpdate("");

	}

	private void deleteMySqlPrefix(String uuid) throws Exception {

		this.mySQL.sendUpdate("");

	}

	@EventHandler
	public void onProxiedPlayerJoin(PostLoginEvent event) {

		ProxiedPlayer player = event.getPlayer();
		String uuid = player.getUniqueId().toString();

		List<SimpleEntry<String, SimpleEntry<String, Boolean>>> prefixHistory = new ArrayList<>();
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
			this.addPlayerChatProfile(event.getPlayer().getUniqueId().toString(), new ChatProfile(prefix,prefixHistory,nickHistory));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void onProxiedPlayerLeave(PlayerDisconnectEvent event) {

		this.deletePlayerChatProfile(event.getPlayer().getUniqueId().toString());

	}

	@EventHandler
	public void onMessage(ChatEvent event) {

		if (event.isCommand()) return;
		if(event.getMessage() == null) return;
		if(event.getMessage().isEmpty()) return;
		if(event.isCancelled()) return;
		if (!(event.getSender() instanceof ProxiedPlayer)) return;

		ProxiedPlayer player = ((ProxiedPlayer) event.getSender());
		ChatProfile chatProfile = this.getPlayerChatProfile(player.getUniqueId().toString());

		String message = event.getMessage();
		
		if(ChatColorUtils.containsChatColorCodes(message) && !player.hasPermission("pumpmybchat.msg.colored")) {

			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Impossible de colorer votre surnom");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			player.sendMessage(txt);
			
			TextComponent txt2 = new TextComponent("Fonctionnalité réservé aux tiers supérieurs");
			txt2.setColor(ChatColor.RED);	
			player.sendMessage(txt2);

			TextComponent link = new TextComponent("http://store.pumpmykins.eu/");
			link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://store.pumpmykins.eu/"));
			link.setBold(true);
			link.setColor(ChatColor.DARK_BLUE);
			TextComponent txt3 = new TextComponent("Voir : ");
			txt3.setColor(ChatColor.RED);
			txt3.addExtra(link);
			player.sendMessage(txt3);

			return;
			
		}
		
		message = ChatColorUtils.getChatColorCodesTranslatedString(message);
		
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
		TextComponent nicknameText;
		if(nickname.hasOne()) {
			
			nicknameText = new TextComponent(nickname.getUnSafeNickname());
			nicknameText.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder("§bPseudo d'origine : §1" + player.getName() + "\n§bCliquez pour envoyer un message privée !").create()));
			
		}else {
			
			nicknameText = new TextComponent(player.getDisplayName());
			nicknameText.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder("§bCliquez pour envoyer un message privée !").create()));
			
		}
		
		nicknameText.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "msg " + player.getName()));
		messages.addExtra(nicknameText);

		TextComponent bet = new TextComponent(" > ");
		bet.setColor(ChatColor.GOLD);
		bet.setBold(true);
		
		messages.addExtra(bet);
		
		messages.addExtra(new TextComponent(message));

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

		
		
		ServerInfo serverInfo = player.getServer().getInfo();

		ProxyServer.getInstance().getLogger().log(CustomLevel.CHAT(serverInfo.getName()), messages.toPlainText());
		
		
		for (ProxiedPlayer receiver : player.getServer().getInfo().getPlayers()) {
			receiver.sendMessage(messages);
		}

		event.setCancelled(true);
		
		this.main.getProxy().getScheduler().runAsync(this.main, new Runnable() {
			
			@Override
			public void run() {			
				
				try {
					DiscordWebhook webhook = new DiscordWebhook(configManager.getWebHookUrl());
					webhook.setTts(false);
					webhook.setUsername("PumpMyBChat");
					
					webhook.addEmbed(new DiscordWebhook.EmbedObject().setTitle("**Serveur :** " + serverInfo.getName()).setDescription(messages.toPlainText()));
					
					webhook.execute();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});

	}
	
}
