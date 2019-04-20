package fr.pumpmykins.pumpmyprefix.command;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public abstract class QSubCommand {

	public abstract String getPermission();
		
    public abstract void onCommand(CommandSender sender, String[] args);
	
	public boolean runCommand(CommandSender p, String[] args) {
		
		if(!p.hasPermission(getPermission())) {
			
			p.sendMessage(new TextComponent("Vous n'avez pas la permission de faire ceci"));
			return false;
		}
		else {
			
			onCommand(p, args);
		}
		return true;
	}
}
