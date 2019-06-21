package fr.pumpmybchat.command.prefix;

import java.util.List;

import fr.pumpmybchat.ChatManager;
import fr.pumpmybchat.utils.ISubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class SetPrefixSubCommand implements ISubCommand {

	private ChatManager chatManager;

	public SetPrefixSubCommand(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {
		
		

	}

}
