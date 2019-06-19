package fr.pumpmybchat.command.prefix;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PrefixHelpCommand extends QSubCommand {

	@Override
	public String getPermission() {
		
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
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
		TextComponent color = new TextComponent("/prefix color pour voir la liste des couleur ainsi que comment les utiliser");
		color.setColor(ChatColor.YELLOW);
		color.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/prefix color"));
		
		tosend.add(message1);
		tosend.add(activation);
		tosend.add(reload);
		tosend.add(reset);
		tosend.add(set);
		tosend.add(color);
		
		if(sender.hasPermission("pumpmykins.staff.moderateur") || sender.hasPermission("pumpmykins.staff.admin") || sender.hasPermission("pumpmykins.staff.responsable")) {
			
			TextComponent staff = new TextComponent("---[Staff]---");
			staff.setColor(ChatColor.DARK_RED);
			TextComponent warn = new TextComponent("/prefix warn <player> Au bout de 3 Warn le prefix du joueurs est bloqué !");
			warn.setColor(ChatColor.RED);
			warn.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/prefix warn "));
			TextComponent forcedelete = new TextComponent("/prefix forcedelete <player> Supprime le prefix de manière irréversible !");
			forcedelete.setColor(ChatColor.RED);
			forcedelete.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/prefix forcedelete"));
			
			tosend.add(staff);
			tosend.add(warn);
			tosend.add(forcedelete);
			
		}
		
		for(int i = 0; i < tosend.size(); i++) {
			
			sender.sendMessage(tosend.get(i));
		}
		
	}

}
