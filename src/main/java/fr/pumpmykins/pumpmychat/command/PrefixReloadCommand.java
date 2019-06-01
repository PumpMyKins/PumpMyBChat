package fr.pumpmykins.pumpmychat.command;

import fr.pumpmykins.pumpmychat.ChatPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixReloadCommand extends QSubCommand {

	@SuppressWarnings("unused")
	private ChatPlayer chatPlayer;

	public PrefixReloadCommand(ChatPlayer cp) {

		this.chatPlayer = cp;
	}

	@Override
	public String getPermission() {

		return null;
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
		if(sender instanceof ProxiedPlayer) {

			if(sender.hasPermission("rank.tier1") || sender.hasPermission("rank.tier2") || sender.hasPermission("rank.tier3")) {

				this.chatPlayer = new ChatPlayer();
				
			}
		}
	}
}

