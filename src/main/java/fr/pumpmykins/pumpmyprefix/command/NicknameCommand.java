package fr.pumpmykins.pumpmyprefix.command;

import java.util.Map;
import java.util.UUID;

import fr.pumpmykins.pumpmyprefix.ChatPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class NicknameCommand extends Command {

	private ChatPlayer chatPlayer;
	
	public NicknameCommand(String name, ChatPlayer cp) {
		super(name);
		this.chatPlayer = cp;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(sender.hasPermission("rank.tier2") || sender.hasPermission("rank.tier3")) {
			
			Map<UUID, String> nickList = this.chatPlayer.getNickname();
			
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			if(args.length > 0) {
			
				if(!nickList.containsKey(player.getUniqueId())) {
					
					nickList.put(player.getUniqueId(), args[0]);
					
					TextComponent desactive = new TextComponent("NickName appliqué !");
					desactive.setColor(ChatColor.GOLD);
					sender.sendMessage(desactive);
				} else {
					
					nickList.remove(player.getUniqueId());
					nickList.put(player.getUniqueId(), args[0]);
					
					TextComponent desactive = new TextComponent("NickName appliqué !");
					desactive.setColor(ChatColor.GOLD);
					sender.sendMessage(desactive);
				}
			} else {
				
				if(nickList.containsKey(player.getUniqueId())) {
					
					nickList.remove(player.getUniqueId());
					TextComponent desactive = new TextComponent("NickName supprimé !");
					desactive.setColor(ChatColor.GOLD);
					sender.sendMessage(desactive);
				}
			}
			
			this.chatPlayer.setNickname(nickList);
			
		} else {
			
			TextComponent desactive = new TextComponent("Vous n'avez pas de nickname !");
			desactive.setColor(ChatColor.GOLD);
			sender.sendMessage(desactive);
		}
	}

}
