package fr.pumpmykins.pumpmychat.command;

import fr.pumpmykins.pumpmychat.ChatPlayer;
import fr.pumpmykins.pumpmychat.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixWarnPrefixOwnerCommand extends QSubCommand {

	private ChatPlayer cp;

	public PrefixWarnPrefixOwnerCommand(ChatPlayer chatPlayer) {

		this.cp = chatPlayer;
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return "rank.staff.moderateur";
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {

		if(sender instanceof ProxiedPlayer) {

			if(sender.hasPermission("pumpmykins.staff.modo") || sender.hasPermission("pumpmykins.staff.admin") || sender.hasPermission("pumpmykins.staff.responsable")) {

				ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[1]);

				if(!this.cp.warnPrefix(p.getUniqueId())) {

					sender.sendMessage(Main.getERROR_NO_PREFIX());

				} else {

					TextComponent desactive = new TextComponent("Préfix warn ! ("+p.getName()+" a "+(this.cp.getPrefix().get(p.getUniqueId())).getWarn()+" warn");
					desactive.setColor(ChatColor.DARK_RED);
					sender.sendMessage(desactive);

				}
			}
		}
	}
}
