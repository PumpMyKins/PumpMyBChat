package fr.pumpmykins.pumpmyprefix;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import fr.pumpmykins.pumpmyprefix.command.PrefixActivationCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixColorCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixForceDeleteCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixHelpCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixReloadCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixResetCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixSetCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixWarnPrefixOwnerCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Main  extends Plugin{

	static Main sharedInstance = null;
	
	public static TextComponent PREFIX = new TextComponent("[PumpMyPrefix]");
	
	static MySql mySQL;
	public static String host = "";
	public static String username = "";
	public static String password = "";
	public static String database = "";
	public static int port = 3306;
	
	public static ConfigManager configManager;
	
	public static String REQUEST_GET_USER_PREFIX = "SELECT * FROM PrefixPlayer ";
	
	public static String STRING_ERROR_NO_PREFIX = "Vous n'avez pas de préfix !";
	
	public static TextComponent ERROR_NO_PREFIX = new TextComponent();
	
	private Map<UUID , String> prefix;
	
	@Override
	public void onEnable() {
		
		sharedInstance = this;
		
		PluginManager pm = ProxyServer.getInstance().getPluginManager();
		
		pm.registerCommand(this, new PrefixCommand("prefix"));
		PrefixCommand.addCommand(Arrays.asList("help", "h"), new PrefixHelpCommand());
		PrefixCommand.addCommand(Arrays.asList("set"), new PrefixSetCommand());
		PrefixCommand.addCommand(Arrays.asList("reload", "r"), new PrefixReloadCommand(this.prefix));
		PrefixCommand.addCommand(Arrays.asList("reset"), new PrefixResetCommand());
		PrefixCommand.addCommand(Arrays.asList("active", "a"), new PrefixActivationCommand());
		PrefixCommand.addCommand(Arrays.asList("forcedelete"), new PrefixForceDeleteCommand());
		PrefixCommand.addCommand(Arrays.asList("warn"), new PrefixWarnPrefixOwnerCommand());
		PrefixCommand.addCommand(Arrays.asList("color"), new PrefixColorCommand());
		
		mySQL.openConnection();
		if(mySQL.isConnected()) {
			
			String createprefixtable = "CREATE TABLE IF NOT EXISTS PrefixPlayer (`uuid` VARCHAR(191) NOT NULL UNIQUE, `prefix` VARCHAR(191), `active` TINYINT NOT NULL DEFAULT '0', `warn` INT NOT NULL DEFAULT `0`, `modification` INT NOT NULL DEFAULT `0`";
			mySQL.update(createprefixtable);
			
		}
		
		TextComponent ERROR_NO_PREFIX = new TextComponent(STRING_ERROR_NO_PREFIX);
		ERROR_NO_PREFIX.setColor(ChatColor.DARK_RED);
		ERROR_NO_PREFIX.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://pumpmykins.buycraft.net/"));
	}


	public static Main getSharedInstance() {
		return sharedInstance;
	}


	public static void setSharedInstance(Main sharedInstance) {
		Main.sharedInstance = sharedInstance;
	}

	public static MySql getMySQL() {
		return mySQL;
	}


	public static TextComponent getERROR_NO_PREFIX() {
		return ERROR_NO_PREFIX;
	}


	public static void setERROR_NO_PREFIX(TextComponent eRROR_NO_PREFIX) {
		ERROR_NO_PREFIX = eRROR_NO_PREFIX;
	}

	
	
}
