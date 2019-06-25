package fr.pumpmybchat.chat;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import fr.pumpmybchat.ConfigManager;
import fr.pumpmybchat.Main;
import fr.pumpmybchat.utils.ChatColorUtils;
import fr.pumpmybchat.utils.DiscordWebhook;
import fr.pumpmybchat.utils.MySql;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
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

	private void addPlayerChatProfile(String uuid, ChatProfile chatProfile) {

		if(this.profiles.containsKey(uuid.toString())) {
			this.main.getLogger().warning("ChatProfile \"" + uuid.toString() + "\" already exists & will be replace !");
			this.deletePlayerChatProfile(uuid);
			this.addPlayerChatProfile(uuid, chatProfile);
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
		String createtable = "CREATE TABLE IF NOT EXISTS `prefixplayer` ( `uuid` VARCHAR(50) NOT NULL , `prefix` VARCHAR(50) NOT NULL DEFAULT '§dI-LOVE-PMK' , `active` BOOLEAN NOT NULL DEFAULT '1' , `blocked` BOOLEAN NOT NULL DEFAULT '0', `modification` INT NOT NULL , PRIMARY KEY (`uuid`), UNIQUE (`uuid`)) ENGINE = InnoDB;";
		this.mySQL.sendUpdate(createtable);	
		createtable = "CREATE TABLE IF NOT EXISTS `prefixhistory` ( `uuid` VARCHAR(50) NOT NULL , `prefix` VARCHAR(50) NOT NULL , `active` BOOLEAN NOT NULL , `date` DATETIME NOT NULL DEFAULT NOW() ) ENGINE = InnoDB;";
		this.mySQL.sendUpdate(createtable);
		createtable = "CREATE TABLE IF NOT EXISTS `nickhistory` ( `uuid` VARCHAR(50) NOT NULL , `nick` VARCHAR(50) NOT NULL , `date` DATETIME NOT NULL DEFAULT NOW() ) ENGINE = InnoDB;";
		this.mySQL.sendUpdate(createtable);
	}

	private List<SimpleEntry<String,String>> getMySqlNickHistory(String uuid) throws Exception {

		List<SimpleEntry<String,String>> l = new ArrayList<>();

		ResultSet rs = this.mySQL.sendQuery("SELECT `nick`, `date` FROM `nickhistory` WHERE `uuid`='" + uuid + "';");
		while (rs.next()) {

			SimpleEntry<String, String> simpleEntry = new SimpleEntry<String, String>(rs.getString("nick"),rs.getString("date"));
			l.add(simpleEntry);

		}

		return l;		

	}

	private void addPlayerNickInMySqlHistory(String uuid,String nick) throws Exception {

		this.mySQL.sendUpdate("INSERT INTO `nickhistory`(`uuid`, `nick`) VALUES ('" + uuid + "','" + nick + "');");

	}
	
	public void setPlayerNickname(ProxiedPlayer player, String string) throws Exception {

		String uuid = player.getUniqueId().toString();
		ChatProfile chatProfile = this.getPlayerChatProfile(player);
		chatProfile.getNickname().setNickname(string);
		this.addPlayerNickInMySqlHistory(uuid, string);
		
	}

	public void unsetPlayerNickname(ProxiedPlayer player) {
		
		ChatProfile chatProfile = this.getPlayerChatProfile(player);
		chatProfile.getNickname().unsetNickname();
		
	}

	public boolean playerHasNickname(ProxiedPlayer player) {
		
		return this.getPlayerChatProfile(player).getNickname().hasOne();
		
	}

	private List<SimpleEntry<String,SimpleEntry<String, Boolean>>> getMySqlPrefixHistory(String uuid) throws Exception {

		List<SimpleEntry<String,SimpleEntry<String, Boolean>>> l = new ArrayList<>();

		ResultSet rs = this.mySQL.sendQuery("SELECT `prefix`, `active`, `date` FROM `prefixhistory` WHERE `uuid`='" + uuid + "';");
		while (rs.next()) {

			SimpleEntry<String,SimpleEntry<String, Boolean>> simpleEntry = new SimpleEntry<String,SimpleEntry<String, Boolean>>(rs.getString("date"), new SimpleEntry<String, Boolean>(rs.getString("prefix"),rs.getBoolean("active")));
			l.add(simpleEntry);

		}

		return l;
	}
	
	public void initPlayerPrefix(ProxiedPlayer player, int modification) throws Exception {
		
		String uuid = player.getUniqueId().toString();
		this.initMySqlPrefix(uuid, modification);
		
		ChatProfile chatProfile = this.getPlayerChatProfile(player);
		
		Prefix prefix = this.getPlayerPrefix(player);
		if(prefix == null) {
			
			throw new Exception("Prefix unfound after mysql initialise !");
			
		}		
		chatProfile.setPrefix(this.getMySqlPrefix(uuid));		
		
	}
	
	private void initMySqlPrefix(String uuid, int modification) throws Exception {

		this.mySQL.sendUpdate("INSERT INTO `prefixplayer` (`uuid`, `modification`) VALUES ('" + uuid + "', '" + modification + "')");

	}
	
	public Prefix getPlayerPrefix(ProxiedPlayer player) {
		
		try {
			return this.getMySqlPrefix(player.getUniqueId().toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	private Prefix getMySqlPrefix(String uuid) throws Exception {
		
		ResultSet rs = this.mySQL.sendQuery("SELECT `prefix`, `active`, `blocked` , `modification` FROM `prefixplayer` WHERE `uuid`='" + uuid + "';");
		
		if(!rs.first()) {
			
			return null;
			
		}
		
		return new Prefix(uuid, rs.getString("prefix"), rs.getBoolean("active"),rs.getBoolean("blocked"), rs.getInt("modification"));
		
	}
	
	public void updatePlayerPrefixModification(ProxiedPlayer player,int modification) throws Exception {
		
		ChatProfile chatProfile = this.getPlayerChatProfile(player);
		Prefix prefix = chatProfile.getPrefix();
		
		prefix.setModification(modification);
		
		String uuid = player.getUniqueId().toString();
		
		this.updateMySqlPrefix(uuid, prefix);
		
	}
	
	public void updatePlayerPrefixContent(ProxiedPlayer player, String content,boolean modificated) throws Exception {
		
		ChatProfile chatProfile = this.getPlayerChatProfile(player);
		Prefix prefix = chatProfile.getPrefix();
			
		prefix.setPrefix(content,modificated);	
		
		String uuid = player.getUniqueId().toString();
		
		this.updateMySqlPrefix(uuid, prefix);
		this.addPrefixInMySqlHistory(uuid, prefix);
		
	}
	
	public void updatePlayerPrefixActive(ProxiedPlayer player, boolean active) throws Exception {
		
		ChatProfile chatProfile = this.getPlayerChatProfile(player);
		Prefix prefix = chatProfile.getPrefix();
		
		prefix.setActive(active);
		
		String uuid = player.getUniqueId().toString();
		
		this.updateMySqlPrefix(uuid, prefix);
		this.addPrefixInMySqlHistory(uuid, prefix);
		
	}
	
	public void updatePlayerPrefixBlocked(ProxiedPlayer player, boolean blocked) throws Exception {
		
		ChatProfile chatProfile = this.getPlayerChatProfile(player);
		Prefix prefix = chatProfile.getPrefix();
		
		prefix.setBlocked(blocked);
		
		String uuid = player.getUniqueId().toString();
		
		this.updateMySqlPrefix(uuid, prefix);
		
	}
	
	private void addPrefixInMySqlHistory(String uuid, Prefix prefix) throws Exception {

		this.mySQL.sendUpdate("INSERT INTO `prefixhistory`(`uuid`, `prefix`, `active`) VALUES ('" + uuid + "','" + prefix.getPrefix() + "','" + (prefix.isActive() ? 1 : 0) + "');");

	}

	private void updateMySqlPrefix(String uuid, Prefix prefix) throws Exception {

		this.mySQL.sendUpdate("UPDATE `prefixplayer` SET `prefix`='" + prefix.getPrefix() + "',`active`='" + (prefix.isActive() ? 1 : 0) + "',`blocked`='" + (prefix.isBlocked() ? 1 : 0) +"',`modification`='" + prefix.getModification() + "' WHERE `uuid`='" + uuid + "';");

	}
	
	public void deletePlayerPrefix(ProxiedPlayer player) throws Exception {
		
		String uuid = player.getUniqueId().toString();
		this.deleteMySqlPrefix(uuid);
		
		ChatProfile chatProfile = this.getPlayerChatProfile(player);
		chatProfile.setPrefix(null);		
		
	}

	private void deleteMySqlPrefix(String uuid) throws Exception {

		this.mySQL.sendUpdate("DELETE FROM `prefixplayer` WHERE `uuid`='" + uuid + "';");

	}

	@EventHandler
	public void onProxiedPlayerJoin(PostLoginEvent event) {

		ProxiedPlayer player = event.getPlayer();
		String uuid = player.getUniqueId().toString();
		
		if(this.profiles.containsKey(uuid)) {			
			this.deletePlayerChatProfile(uuid);
		}
		
		this.addPlayerChatProfile(uuid, new ChatProfile(null, null, null));		
		
		this.main.getProxy().getScheduler().runAsync(this.main, new Runnable() {
			
			@Override
			public void run() {
				
				List<SimpleEntry<String, SimpleEntry<String, Boolean>>> prefixHistory = new ArrayList<>();
				try {
					prefixHistory = getMySqlPrefixHistory(uuid);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				List<SimpleEntry<String, String>> nickHistory = new ArrayList<>();
				try {
					nickHistory = getMySqlNickHistory(uuid);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Prefix prefix = getPlayerPrefix(player);	

				addPlayerChatProfile(player.getUniqueId().toString(), new ChatProfile(prefix,prefixHistory,nickHistory));
				
			}
		});
		
	}

	@EventHandler
	public void onProxiedPlayerLeave(PlayerDisconnectEvent event) {

		this.deletePlayerChatProfile(event.getPlayer().getUniqueId().toString());

	}

	@EventHandler
	public void onMessage(ChatEvent event) throws Exception {

		if (event.isCommand()) return;
		if(event.getMessage() == null) return;
		if(event.getMessage().isEmpty()) return;
		if(event.isCancelled()) return;
		if (!(event.getSender() instanceof ProxiedPlayer)) return;

		ProxiedPlayer player = ((ProxiedPlayer) event.getSender());
		ChatProfile chatProfile = this.getPlayerChatProfile(player);
		
		if(chatProfile == null) {
			
			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("ChatProfile introuvable, contactez le staff !");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			player.sendMessage(txt);
			throw new Exception("Player ChatProfile not found ! " + player.getName() + "/" + player.getUniqueId().toString());		
			
		}

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
		if(p!= null && p.isActive() && !p.isBlocked()) {

			TextComponent tcEndPrefix = new TextComponent("] ");
			tcEndPrefix.setColor(ChatColor.GOLD);
			TextComponent tcPrefix = new TextComponent(p.getPrefix());

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
		
		nicknameText.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/msg " + player.getName()));
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

		ProxyServer.getInstance().getLogger().log(Level.INFO,messages.toLegacyText());
		
		
		for (ProxiedPlayer receiver : player.getServer().getInfo().getPlayers()) {
			receiver.sendMessage(messages);
		}

		event.setCancelled(true);
		
		String webhookUrl = configManager.getWebHookUrl(serverInfo.getName());
		
		if(!webhookUrl.equalsIgnoreCase("none")) {
			
			this.main.getProxy().getScheduler().runAsync(this.main, new Runnable() {
				
				@Override
				public void run() {			
					
					try {
						
						DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
						Date now = new Date(System.currentTimeMillis());
						
						DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
						webhook.setTts(false);
						webhook.setUsername("PumpMyBChat");
						
						webhook.addEmbed(new DiscordWebhook.EmbedObject().setTitle("" + shortDateFormat.format(now)).setDescription(messages.toPlainText()));
						
						webhook.execute();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			
		}

	}
	
}
