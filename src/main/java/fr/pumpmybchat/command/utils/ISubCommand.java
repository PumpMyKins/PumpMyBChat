package fr.pumpmybchat.command.utils;

import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public interface ISubCommand {
	
	public void onSubCommand(Command exec,CommandSender sender, List<String> args);
	
}
