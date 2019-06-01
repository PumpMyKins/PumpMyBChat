package fr.pumpmykins.pumpmychat.command;

import fr.pumpmykins.pumpmychat.ChatPlayer;
import fr.pumpmykins.pumpmychat.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixResetCommand extends QSubCommand {

	private ChatPlayer cp;

	public PrefixResetCommand(ChatPlayer chatPlayer) {

		this.cp = chatPlayer;
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {

		if(sender instanceof ProxiedPlayer) {

			ProxiedPlayer p = (ProxiedPlayer) sender;


			if(!this.cp.resetPrefix(p.getUniqueId())) {

				sender.sendMessage(Main.getERROR_NO_PREFIX());

			} else {

				TextComponent desactive = new TextComponent("Préfix supprimer !");
				desactive.setColor(ChatColor.RED);
				sender.sendMessage(desactive);

			}
		}
	}
}

