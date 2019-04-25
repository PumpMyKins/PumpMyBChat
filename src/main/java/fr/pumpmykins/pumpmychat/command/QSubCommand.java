package fr.pumpmykins.pumpmychat.command;

import net.md_5.bungee.api.CommandSender;

public abstract class QSubCommand {

	public abstract String getPermission();
		
    public abstract void onCommand(CommandSender sender, String[] args);
	
	public boolean runCommand(CommandSender p, String[] args) {
		
			onCommand(p, args);
		return true;
	}
}
