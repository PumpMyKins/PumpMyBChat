package fr.pumpmybchat.command.nick;

import fr.pumpmybchat.ChatManager;
import fr.pumpmybchat.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
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

		/*if(sender.hasPermission("pumpmykins.vip.tier2") || sender.hasPermission("pumpmykins.vip.tier3")) {

			ProxiedPlayer player = (ProxiedPlayer) sender;

			if(args.length > 0) {

				this.chatPlayer.addNickname(player.getUniqueId(), args[0]);
				
				TextComponent desactive = new TextComponent("NickName appliqué !");
				desactive.setColor(ChatColor.GOLD);
				sender.sendMessage(desactive);

			} else {

				if(this.chatPlayer.hasNickname(player.getUniqueId())) {

					this.chatPlayer.removeNickname(player.getUniqueId());
					
					TextComponent desactive = new TextComponent("NickName supprimé !");
					desactive.setColor(ChatColor.GOLD);
					sender.sendMessage(desactive);
				}
			}
		} else {

			sender.sendMessage(Main.getERROR_NO_PREFIX());
		}
*/
	}

}
