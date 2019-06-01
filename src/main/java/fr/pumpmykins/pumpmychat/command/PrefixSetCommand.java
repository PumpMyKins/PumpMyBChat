package fr.pumpmykins.pumpmychat.command;

import fr.pumpmykins.pumpmychat.ChatPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixSetCommand extends QSubCommand {

	private ChatPlayer cp;

	public PrefixSetCommand(ChatPlayer chatPlayer) {
		this.cp = chatPlayer;
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {

		if(args.length > 0) {

			if(sender instanceof ProxiedPlayer) {

				if(sender.hasPermission("rank.tier1") || sender.hasPermission("rank.tier2") || sender.hasPermission("rank.tier3")) {

					ProxiedPlayer p = (ProxiedPlayer) sender;

					String prefix = args[1];

					if(this.cp.hasPrefix(p.getUniqueId())) {

						int mod = this.cp.getPrefix().get(p.getUniqueId()).getModification();

						if(sender.hasPermission("rank.tier2")) {

							if(mod >= 2) {

								TextComponent desactive = new TextComponent("Vous avez atteint le nombre de modification maximum !");
								desactive.setColor(ChatColor.RED);
								desactive.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder("Contacter un staff si votre prefix actuel est bug").create()));
								sender.sendMessage(desactive);
							}
						} 
						if(sender.hasPermission("rank.tier1")) {
							if(mod >= 1) {

								TextComponent desactive = new TextComponent("Vous avez atteint le nombre de modification maximum !");
								desactive.setColor(ChatColor.RED);
								desactive.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new ComponentBuilder("Contacter un staff si votre prefix actuel est bug").create()));
								sender.sendMessage(desactive);
							}
						}
					}
					if(this.cp.setPrefix(p.getUniqueId(), prefix)) {

						TextComponent desactive = new TextComponent("Préfix set !");
						desactive.setColor(ChatColor.GREEN);
						sender.sendMessage(desactive);
					} 
				} 
			}
		} 
	}
}