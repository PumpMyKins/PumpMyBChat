package fr.pumpmykins.pumpmyprefix;

import java.util.Arrays;

import fr.pumpmykins.pumpmyprefix.command.PrefixActivationCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixForceDeleteCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixHelpCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixReloadCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixResetCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixSetCommand;
import fr.pumpmykins.pumpmyprefix.command.PrefixWarnPrefixOwnerCommand;
import me.lucko.luckperms.LuckPerms;
import me.lucko.luckperms.api.LuckPermsApi;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Main  extends Plugin{

	static Main sharedInstance = null;
	
	public static TextComponent PREFIX = new TextComponent("[PumpMyPrefix]");

	public static LuckPermsApi api;
	
	
	@Override
	public void onEnable() {
		
		sharedInstance = this;
		
		PluginManager pm = ProxyServer.getInstance().getPluginManager();
		
		pm.registerCommand(this, new PrefixCommand("prefix"));
		PrefixCommand.addCommand(Arrays.asList("help", "h"), new PrefixHelpCommand());
		PrefixCommand.addCommand(Arrays.asList("set"), new PrefixSetCommand());
		PrefixCommand.addCommand(Arrays.asList("reload", "r"), new PrefixReloadCommand());
		PrefixCommand.addCommand(Arrays.asList("reset"), new PrefixResetCommand());
		PrefixCommand.addCommand(Arrays.asList("active", "a"), new PrefixActivationCommand());
		PrefixCommand.addCommand(Arrays.asList("forcedelete"), new PrefixForceDeleteCommand());
		PrefixCommand.addCommand(Arrays.asList("warn"), new PrefixWarnPrefixOwnerCommand());
		
		
		api = LuckPerms.getApi();
		
		
	}
}
