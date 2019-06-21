package fr.pumpmybchat.command.prefix;

import java.util.List;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.utils.ISubCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class HelpPrefixSubCommand implements ISubCommand{

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		TextComponent header = new TextComponent(Main.PLUGIN_PREFIX);
		TextComponent message = new TextComponent("----[HELP]Prefix[HELP]----");
		message.setColor(ChatColor.GOLD);
		message.setBold(true);
		header.addExtra(message);
		
		sender.sendMessage(header);
		
		TextComponent activation = new TextComponent("/prefix activate");
		activation.setColor(ChatColor.DARK_BLUE);
		activation.setBold(true);
		activation.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/prefix activate"));
		activation.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder("§bExecuter la commande !").create()));
		TextComponent txt = new TextComponent(" pour activer ou désactiver votre préfix");
		txt.setColor(ChatColor.AQUA);
		activation.addExtra(txt);
		
		sender.sendMessage(activation);
		
		TextComponent set = new TextComponent("/prefix set");
		set.setColor(ChatColor.DARK_BLUE);
		set.setBold(true);
		set.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/prefix set &dILOVEPMK"));
		set.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder("§bExecuter la commande !").create()));
		TextComponent txt1 = new TextComponent(" pour définir votre préfix");
		txt1.setColor(ChatColor.AQUA);
		set.addExtra(txt1);
		
		sender.sendMessage(set);		
		
		if(sender.hasPermission("pumpmybchat.prefix.colored")) {
			
			TextComponent color = new TextComponent("/prefix color");
			color.setColor(ChatColor.DARK_BLUE);
			color.setBold(true);
			color.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/prefix color"));
			color.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder("§bExecuter la commande !").create()));
			TextComponent txt2 = new TextComponent(" pour définir votre préfix");
			txt2.setColor(ChatColor.AQUA);
			color.addExtra(txt2);
			
			sender.sendMessage(color);
			
		}

	}

	public void onSubCommand(Command motdCommandExecutor, CommandSender sender) {

		this.onSubCommand(motdCommandExecutor, sender, null);

	}


}
