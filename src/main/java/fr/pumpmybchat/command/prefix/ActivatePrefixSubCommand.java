package fr.pumpmybchat.command.prefix;

import java.util.List;

import fr.pumpmybchat.chat.ChatManager;
import fr.pumpmybchat.command.utils.ISubCommand;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class ActivatePrefixSubCommand implements ISubCommand {

	private ChatManager chatManager;

	public ActivatePrefixSubCommand(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

	}

}
