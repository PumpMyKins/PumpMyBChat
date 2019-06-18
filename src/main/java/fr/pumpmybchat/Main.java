package fr.pumpmybchat;

import java.util.Arrays;

import fr.pumpmybchat.MySql.MySQLCredentials;
import fr.pumpmybchat.command.NicknameCommand;
import fr.pumpmybchat.command.PrefixActivationCommand;
import fr.pumpmybchat.command.PrefixBuyCommand;
import fr.pumpmybchat.command.PrefixColorCommand;
import fr.pumpmybchat.command.PrefixCommand;
import fr.pumpmybchat.command.PrefixForceDeleteCommand;
import fr.pumpmybchat.command.PrefixHelpCommand;
import fr.pumpmybchat.command.PrefixReloadCommand;
import fr.pumpmybchat.command.PrefixSetCommand;
import fr.pumpmybchat.command.PrefixWarnPrefixOwnerCommand;
import fr.pumpmybchat.utils.MessageEventHandler;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Main  extends Plugin implements Listener{

	static Main sharedInstance = null;

	public static TextComponent PREFIX = new TextComponent("[PumpMyPrefix]");

	public static String REQUEST_GET_USER_PREFIX = "SELECT * FROM PrefixPlayer ";

	public static String STRING_ERROR_NO_PREFIX = "Pour avoir ces acces : http://store.pumpmykins.eu/ ";

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
			configManager.init();
			
		} catch (Throwable e) {
			
			e.printStackTrace();
			this.getLogger().severe("Configuration error, plugin disabled !");
			return;
			
		}

		MySQLCredentials credentials = new MySQLCredentials(this.configManager.getHost(),this.configManager.getPort(), this.configManager.getUser(), this.configManager.getPassword(), this.configManager.getDatabase());
		this.mySQL = new MySql(credentials);

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
