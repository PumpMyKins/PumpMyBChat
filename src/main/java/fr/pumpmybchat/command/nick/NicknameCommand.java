package fr.pumpmybchat.command.nick;

import fr.pumpmybchat.ChatManager;
import fr.pumpmybchat.Main;
import fr.pumpmybchat.utils.ChatColorUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class NicknameCommand extends Command {

	private ChatManager chatManager;

	public NicknameCommand(String name, ChatManager chatManager) {
		super(name);
		this.chatManager = chatManager;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if(!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "§cVous devez etre un joueur pour faire cela !"));
			return;
		}
		
		if(sender.hasPermission("pumpmybchat.command.nick")) {

			ProxiedPlayer player = (ProxiedPlayer) sender;

			if(args.length == 1) {

				String nickname = args[0];
				if(nickname.trim().isEmpty()) {
					
					
					
				}else {
					
					this.chatManager.setNickname(player,ChatColor.translateAlternateColorCodes('&', args[0]));				
					sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "Surnom (\"§r" + nickname + "§r§f\") appliqué !"));
					sender.sendMessage(new TextComponent("§fVotre surnom sera automatiquement supprimé lors de votre déconnexion du serveur."));
					TextComponent txt = new TextComponent("§fUtilisez la commande : \"");
					TextComponent cmd = new TextComponent("§1/nick");
					cmd.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/nick"));
					txt.addExtra(cmd);
					txt.addExtra("§f\" pour supprimer votre surnom.");
					sender.sendMessage(txt);
					
					
				}
				


			} else if(args.length == 0){

				if(this.chatManager.hasNickname(player)) {

					this.chatManager.unsetNickname(player);					
					sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "Surnom supprimé !"));
					
				}else {
					
					sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "§cVous ne possedez pas de surnom !"));
					
				}
				
			}else {
				
				sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "§cErreur de synthaxe dans votre commande !"));
				TextComponent txt = new TextComponent("§fDéfinir un surnom : ");
				TextComponent cmd = new TextComponent("§1/nick <surnom>");
				cmd.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/nick PumpMyKins"));
				txt.addExtra(cmd);
				sender.sendMessage(new TextComponent(txt));
				
				TextComponent txt1 = new TextComponent("§fSupprimer un surnom : ");
				TextComponent cmd1 = new TextComponent("§1/nick");
				cmd1.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/nick"));
				txt1.addExtra(cmd1);
				sender.sendMessage(new TextComponent(txt1));
				
			}
			
		} else {

			sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "§cCette fonctionnalité doit etre acheté en boutique."));
			TextComponent link = new TextComponent("§l§1http://store.pumpmykins.eu/");
			link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://store.pumpmykins.eu/"));
			TextComponent txt = new TextComponent("§fVoir : ");
			txt.addExtra(link);
			sender.sendMessage(txt);
			
			
		}

	}

}
