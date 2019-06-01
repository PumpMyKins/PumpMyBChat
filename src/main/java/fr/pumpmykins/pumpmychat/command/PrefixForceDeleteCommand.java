package fr.pumpmykins.pumpmychat.command;

import fr.pumpmykins.pumpmychat.ChatPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixForceDeleteCommand extends QSubCommand {

	private ChatPlayer cp;
	
	public PrefixForceDeleteCommand(ChatPlayer chatPlayer) {

		this.cp = chatPlayer;
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return "rank.staff.responsable";
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(sender instanceof ProxiedPlayer) {

			if(sender.hasPermission("rank.staff.responsable"))

				if(args.length >= 2) {

					ProxiedPlayer deletescope = ProxyServer.getInstance().getPlayer(args[1]);
					if(deletescope == null) {

						for(ProxiedPlayer i : ProxyServer.getInstance().getPlayers()) {

							if(i.getDisplayName().toLowerCase() == args[1].toLowerCase()) {

								deletescope = ProxyServer.getInstance().getPlayer(i.getName());
								break;
							} else {
								continue;
							}
						}

						if(deletescope == null) {

							TextComponent playerunknown = new TextComponent("Joueur inconnue !");
							playerunknown.setColor(ChatColor.DARK_RED);
							sender.sendMessage(playerunknown);

						}
					}
					if(deletescope != null) {	

						this.cp.forceDelete(deletescope.getUniqueId());
						
						TextComponent playerunknown = new TextComponent("Prefix Supprimer !");
						playerunknown.setColor(ChatColor.DARK_RED);
						sender.sendMessage(playerunknown);
						
						TextComponent deleteprefix = new TextComponent("Votre Prefix a ete supprime par "+sender.getName()+" !");
						deleteprefix.setColor(ChatColor.DARK_RED);
						sender.sendMessage(deleteprefix);
					}
				}
		}
	}
}
