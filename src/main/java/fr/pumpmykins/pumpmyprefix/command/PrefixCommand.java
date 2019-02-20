package fr.pumpmykins.pumpmyprefix.command;

import java.util.HashMap;
import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class PrefixCommand extends Command {

	public PrefixCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

    private static HashMap<List<String>, QSubCommand> commands = new HashMap<List<String>, QSubCommand>();
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub

			if(args.length >= 1) {
				
				boolean match = false;
				
				for(List<String> s : commands.keySet()) {
					
					if(s.contains(args[0])) {
						
						commands.get(s).runCommand(sender, args);
						match = true;
					}
				}
				if(!match) {
					
					sender.sendMessage(new TextComponent("Commande Inconnue"));
				}
			} else {
				
				ProxyServer.getInstance().getPluginManager().dispatchCommand(sender, "prefix help");
			}
		}
		
	public static void addCommand(List<String> cmds, QSubCommand s) {
		
		commands.put(cmds, s);
	}
}
