package fr.pumpmybchat.command.prefix.admin;

import java.util.List;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.command.utils.ISubCommand;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.plugin.Command;

public class HelpPrefixAdminSubCommand implements ISubCommand{

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		TextComponent header = new TextComponent(Main.PLUGIN_PREFIX);
		TextComponent message = new TextComponent("----[HELP]Prefix-Admin[HELP]----");
		message.setColor(ChatColor.GOLD);
		message.setBold(true);
		header.addExtra(message);
		
		sender.sendMessage(header);
		
		TextComponent block = new TextComponent("/bprefix-admin block");
		block.setColor(ChatColor.DARK_BLUE);
		block.setBold(true);
		block.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/bprefix-admin block"));
		block.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder("§bExecuter la commande !").create()));
		TextComponent txt = new TextComponent(" pour bloquer ou débloquer le prefix d'un joueur.");
		txt.setColor(ChatColor.AQUA);
		block.addExtra(txt);
		
		sender.sendMessage(block);	

	}

	public void onSubCommand(Command motdCommandExecutor, CommandSender sender) {

		this.onSubCommand(motdCommandExecutor, sender, null);

	}


}
