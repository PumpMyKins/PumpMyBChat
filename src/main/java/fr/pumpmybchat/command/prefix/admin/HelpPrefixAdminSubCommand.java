package fr.pumpmybchat.command.prefix.admin;

import java.util.ArrayList;
import java.util.List;

import fr.pumpmybchat.utils.ISubCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class HelpPrefixAdminSubCommand implements ISubCommand{

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		List<TextComponent> tosend = new ArrayList<TextComponent>();

		TextComponent message1 = new TextComponent("----[HELP]Prefix[HELP]----");
		message1.setColor(ChatColor.GOLD);
		message1.setBold(true);
		
		TextComponent activation = new TextComponent("/prefix activate pour activer ou désactiver votre préfix");
		activation.setColor(ChatColor.YELLOW);
		activation.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/prefix activate"));
		
		TextComponent reload = new TextComponent("/prefix reload pour actualiser votre préfix !");
		reload.setColor(ChatColor.YELLOW);
		reload.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/prefix reload"));
		
		TextComponent reset = new TextComponent("/prefix reset pour remettre à zéro votre préfix (Le désactive aussi)");
		reset.setColor(ChatColor.YELLOW);
		reset.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/prefix reset"));
		
		TextComponent set = new TextComponent("/prefix set <prefix> pour mettre en place celui ci");
		set.setColor(ChatColor.YELLOW);
		set.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/prefix set"));
		
		if(sender.hasPermission("pumpmybchat.prefix.colored")) {
			
			TextComponent color = new TextComponent("/prefix color pour voir la liste des couleur disponible pour colorer vos prefix");
			color.setColor(ChatColor.YELLOW);
			color.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/prefix color"));
			tosend.add(color);
			
		}

		tosend.add(message1);
		tosend.add(activation);
		tosend.add(reload);
		tosend.add(reset);
		tosend.add(set);

		for(int i = 0; i < tosend.size(); i++) {

			sender.sendMessage(tosend.get(i));
		}

	}

	public void onSubCommand(Command motdCommandExecutor, CommandSender sender) {

		this.onSubCommand(motdCommandExecutor, sender, null);

	}


}
