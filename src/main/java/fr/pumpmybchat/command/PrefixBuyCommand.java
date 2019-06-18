package fr.pumpmybchat.command;

import java.util.UUID;

import fr.pumpmybchat.ChatPlayer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PrefixBuyCommand extends QSubCommand {

	private ChatPlayer cp;
	
	public PrefixBuyCommand(ChatPlayer chatPlayer) {
		this.cp = chatPlayer;
	}

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {
		
		/*if(!(sender instanceof ProxiedPlayer)) {
			
			this.cp.resetModification(UUID.fromString(args[1]));
		}*/
	}

}
