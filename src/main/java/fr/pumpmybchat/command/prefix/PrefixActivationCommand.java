package fr.pumpmybchat.command;

import fr.pumpmybchat.ChatPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixActivationCommand extends QSubCommand {

	private ChatPlayer cp;

	public PrefixActivationCommand(ChatPlayer chatPlayer) {
		this.cp = chatPlayer;
	}

	@Override
	public String getPermission() {
		
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
	/*	if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) sender;

			if(this.cp.changeActivation(p.getUniqueId())) {

				if(this.cp.getPrefix().get(p.getUniqueId()).isActive()) {

					TextComponent activate = new TextComponent("Préfix activer !");
					activate.setColor(ChatColor.GREEN);
					sender.sendMessage(activate);

				} else {

					TextComponent desactive = new TextComponent("Préfix désactiver !");
					desactive.setColor(ChatColor.RED);
					sender.sendMessage(desactive);
				}
			} else {
				
				TextComponent desactive = new TextComponent("Vous n'avez pas encore de prefix !");
				desactive.setColor(ChatColor.RED);
				sender.sendMessage(desactive);
			}
		}*/
	}
}
