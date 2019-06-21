package fr.pumpmybchat.command.prefix;

import java.util.List;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.chat.ChatManager;
import fr.pumpmybchat.chat.ChatProfile;
import fr.pumpmybchat.chat.Prefix;
import fr.pumpmybchat.command.utils.ISubCommand;
import fr.pumpmybchat.utils.ChatColorUtils;
import fr.pumpmybchat.utils.InsufisantModificationPrefixException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SetPrefixSubCommand implements ISubCommand {

	private ChatManager chatManager;

	public SetPrefixSubCommand(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		if(args.size() != 1) {

			new HelpPrefixSubCommand().onSubCommand(exec, sender);
			return;

		}

		String stringPrefix = args.get(0).trim();

		if(stringPrefix.isEmpty()) {

			new HelpPrefixSubCommand().onSubCommand(exec, sender);
			return;

		}
		
		

	}

}
