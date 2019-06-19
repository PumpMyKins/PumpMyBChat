package fr.pumpmybchat.command.prefix;

import fr.pumpmybchat.ChatPlayer;
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

			if(sender.hasPermission("pumpmykins.vip.tier1") || sender.hasPermission("pumpmykins.vip.tier2") || sender.hasPermission("pumpmykins.vip.tier3")) {

				this.chatPlayer = new ChatPlayer();
				
			}
		}
	}
}

