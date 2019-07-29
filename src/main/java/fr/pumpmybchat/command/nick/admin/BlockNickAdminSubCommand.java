package fr.pumpmybchat.command.nick.admin;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.chat.ChatProfile;
import fr.pumpmybchat.chat.Prefix;
import fr.pumpmybchat.command.utils.ISubCommand;
import fr.pumpmybchat.command.utils.ISubTabCompleter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BlockNickAdminSubCommand implements ISubCommand, ISubTabCompleter {

	private Main main;

	public BlockNickAdminSubCommand(Main main) {
		this.main = main;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		if(args.size() == 1 && Lists.newArrayList(this.onTabComplete(exec,sender, args)).contains(args.get(0))) {

			ProxiedPlayer player = this.main.getProxy().getPlayer(args.get(0));
			ChatProfile chatProfile = this.main.getChatManager().getPlayerChatProfile(player);
			Prefix prefix = chatProfile.getPrefix();
			
			boolean blocked;
			if(prefix.isBlocked()) {
				blocked = false;
				sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "§bSurnom débloqué pour le joueur " + player.getName() + "!"));
			}else {
				blocked = true;
				sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "§bSurnom bloqué pour le joueur " + player.getName() + " !"));			
			}
			
			try {
				this.main.getChatManager().updatePlayerPrefixBlocked(player, blocked);
			} catch (Exception e) {
				e.printStackTrace();
				sender.sendMessage(new TextComponent(e.getMessage()));
			}
			

		}else {

			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Joueur introuvable !");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			sender.sendMessage(txt);

		}

	}

	@Override
	public List<String> onTabComplete(Command command, CommandSender sender, List<String> args) {

		List<String> l = new ArrayList<>();

		for (ProxiedPlayer player : this.main.getProxy().getPlayers()) {
			l.add(player.getName());
		}

		return l;

	}

}
