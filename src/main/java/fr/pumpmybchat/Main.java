package fr.pumpmybchat;

import java.sql.SQLException;

import fr.pumpmybchat.MySql.MySQLCredentials;
import fr.pumpmybchat.command.nick.NicknameCommand;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Main  extends Plugin implements Listener{

	static Main sharedInstance = null;

	public final static String PLUGIN_PREFIX = "§l§6[§2Pump§eMy§3BShutdown§6]§r§f ";

	private ChatPlayer chatPlayer;
	private MySql mySQL;
	private ConfigManager configManager;

	public ChatPlayer getChatPlayer() {return this.chatPlayer;}
	public MySql getMySQL() {return this.mySQL;}
	public ConfigManager getConfigManager() {return this.configManager;}

	@Override
	public void onEnable() {

		try {

			configManager = new ConfigManager(this);
			this.getLogger().info("Configuration OK");

		} catch (Exception e) {

			e.printStackTrace();
			this.getLogger().severe("Configuration error, plugin disabled !");
			return;

		}

		try {
			MySQLCredentials credentials = new MySQLCredentials(this.configManager.getHost(),this.configManager.getPort(), this.configManager.getUser(), this.configManager.getPassword(), this.configManager.getDatabase());
			this.mySQL = new MySql(credentials);
			this.mySQL.openConnection();
			this.getLogger().info("MySQL OK");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			this.getLogger().severe("JDBC error, plugin disabled !");
			return;
		} catch (SQLException e) {
			e.printStackTrace();
			this.getLogger().severe("MySQL connection error, plugin disabled !");
			return;
		}

		/*mySQL.openConnection();
		if(mySQL.isConnected()) {

			String createprefixtable = "CREATE TABLE IF NOT EXISTS PrefixPlayer(`uuid` VARCHAR(191) NOT NULL UNIQUE, `prefix` VARCHAR(191), `active` TINYINT NOT NULL DEFAULT 0, `warn` INT NOT NULL DEFAULT 0, `modification` INT NOT NULL DEFAULT 0)";
			mySQL.update(createprefixtable);

		}*/

		this.chatPlayer = new ChatPlayer();
		
		PluginManager pm = ProxyServer.getInstance().getPluginManager();
		
		//START PREFIX COMMAND REGISTERING
		pm.registerCommand(this, new PrefixCommand("prefix"));
		
		PrefixCommand.addCommand(Arrays.asList("help", "h"), new PrefixHelpCommand());
		PrefixCommand.addCommand(Arrays.asList("color"), new PrefixColorCommand());
		
		//PREFIX COMMAND WHICH REQUIRE A RELOAD
		PrefixCommand.addCommand(Arrays.asList("set"), new PrefixSetCommand(this.chatPlayer));
		PrefixCommand.addCommand(Arrays.asList("activate", "a"), new PrefixActivationCommand(this.chatPlayer));
		PrefixCommand.addCommand(Arrays.asList("warn"), new PrefixWarnPrefixOwnerCommand(this.chatPlayer));
		PrefixCommand.addCommand(Arrays.asList("forcedelete"), new PrefixForceDeleteCommand(this.chatPlayer));
		PrefixCommand.addCommand(Arrays.asList("buy"), new PrefixBuyCommand(this.chatPlayer));
		//RELOAD COMMAND
		PrefixCommand.addCommand(Arrays.asList("reload", "r"), new PrefixReloadCommand(this.chatPlayer));

		//NICK COMMAND
		pm.registerCommand(this, new NicknameCommand("nick", this.chatPlayer));
		
		//CHAT FORMATAGE
		pm.registerListener(this, new MessageEventHandler(this.chatPlayer));
	}
	
	
	public static Main getSharedInstance() {
		return sharedInstance;
	}

	public static MySql getMySQL() throws SQLException {
		if(mySQL.isConnected()) {
			return mySQL;
		} else {
			mySQL.refreshConnection();
			if(mySQL.isConnected()) {
				return mySQL;
			}
		}
		return null;
	}
	
	public static void setSharedInstance(Main sharedInstance) {
		Main.sharedInstance = sharedInstance;
	}

	public static TextComponent getERROR_NO_PREFIX() {
		
		TextComponent ERROR_NO_PREFIX = new TextComponent(STRING_ERROR_NO_PREFIX);
		ERROR_NO_PREFIX.setColor(ChatColor.DARK_RED);
		ERROR_NO_PREFIX.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://store.pumpmykins.eu/"));
		
		return ERROR_NO_PREFIX;
	}
}
