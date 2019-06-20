package fr.pumpmybchat.utils;

import java.util.List;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class SubCommandData {

	private String permissionNode = "none";
	private String subCommand;
	private ISubCommand iSubCommand;
	
	public SubCommandData(String s,String p, ISubCommand i) {
		
		this(s,i);
		this.permissionNode = p;
		
	}
	
	public SubCommandData(String s, ISubCommand i) {
		// TODO Auto-generated constructor stub
		this.subCommand = s;
		this.iSubCommand = i;
	}
	
	@Override
	public String toString() {
		
		return this.subCommand;
		
	}
	
	public void execute(Command islandCommandExecutor, CommandSender sender , List<String> args) {
		
		this.iSubCommand.onSubCommand(islandCommandExecutor,sender, args);
		
	}

	public String getPermissionNode() {
		return permissionNode;
	}
	public void setPermissionNode(String permissionNode) {
		this.permissionNode = permissionNode;
	}
	public String getSubCommand() {
		return subCommand;
	}
	public void setSubCommand(String subCommand) {
		this.subCommand = subCommand;
	}
	public ISubCommand getSubCommandExecutor() {
		return iSubCommand;
	}
	public void setSubCommandExecutor(ISubCommand iSubCommand) {
		this.iSubCommand = iSubCommand;
	}
	
}
