package fr.pumpmybchat;

import java.sql.SQLException;

import fr.pumpmybchat.chat.ChatManager;
import fr.pumpmybchat.command.MsgCommand;
import fr.pumpmybchat.command.NicknameCommand;
import fr.pumpmybchat.command.prefix.ActivatePrefixSubCommand;
import fr.pumpmybchat.command.prefix.ColorPrefixSubCommand;
import fr.pumpmybchat.command.prefix.HelpPrefixSubCommand;
import fr.pumpmybchat.command.prefix.PrefixCommandExecutor;
import fr.pumpmybchat.command.prefix.SetPrefixSubCommand;
import fr.pumpmybchat.command.prefix.admin.BlockPrefixAdminSubCommand;
import fr.pumpmybchat.command.prefix.admin.DeletePrefixAdminSubCommand;
import fr.pumpmybchat.command.prefix.admin.InitPrefixAdminSubCommand;
import fr.pumpmybchat.command.prefix.admin.PrefixAdminCommandExecutor;
import fr.pumpmybchat.utils.MySql;
import fr.pumpmybchat.utils.MySql.MySQLCredentials;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Main  extends Plugin implements Listener{

	static Main sharedInstance = null;

	public final static String PLUGIN_PREFIX = "§l§6[§2Pump§eMy§3BChat§6]§r§f ";

	private ChatManager chatManager;
	private MySql mySQL;
	private ConfigManager configManager;

	private MsgManager msgManager;

	public ChatManager getChatManager() {return this.chatManager;}
	public MySql getMySQL() {return this.mySQL;}
	public ConfigManager getConfigManager() {return this.configManager;}
	public MsgManager getMsgManager() { return this.msgManager; }

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
		
		PluginManager pm = this.getProxy().getPluginManager();
		
		try {
			this.chatManager = new ChatManager(this,this.configManager,this.mySQL);
			pm.registerListener(this, this.chatManager);
		} catch (Exception e) {
			e.printStackTrace();
			this.getLogger().severe("MySQL error, plugin disabled !");
			return;
		}

		this.msgManager = new MsgManager();	
		
		// COMMANDS
		
		pm.registerCommand(this, new NicknameCommand("nick",this.chatManager));
		
		pm.registerCommand(this, new MsgCommand(this,"msg","w","tell"));
		
		PrefixCommandExecutor prefixCommandExecutor = new PrefixCommandExecutor(this);
		prefixCommandExecutor.addSubCommand("help", new HelpPrefixSubCommand());
		prefixCommandExecutor.addSubCommand("activate", new ActivatePrefixSubCommand(this.chatManager));
		prefixCommandExecutor.addSubCommand("set", new SetPrefixSubCommand(this.chatManager));
		prefixCommandExecutor.addSubCommand("color", "pumpmybchat.command.prefix.color", new ColorPrefixSubCommand());
		
		pm.registerCommand(this, prefixCommandExecutor);
		
		
		PrefixAdminCommandExecutor prefixAdminCommandExecutor = new PrefixAdminCommandExecutor(this);
		prefixAdminCommandExecutor.addSubCommand("init", new InitPrefixAdminSubCommand(this));
		prefixAdminCommandExecutor.addSubCommand("block", new BlockPrefixAdminSubCommand(this));
		prefixAdminCommandExecutor.addSubCommand("delete", new DeletePrefixAdminSubCommand(this));
		
		pm.registerCommand(this, prefixAdminCommandExecutor);
		
		/*this.chatPlayer = new ChatPlayer();



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


		//CHAT FORMATAGE
		pm.registerListener(this, new MessageEventHandler(this.chatPlayer));*/
	}
	
}
