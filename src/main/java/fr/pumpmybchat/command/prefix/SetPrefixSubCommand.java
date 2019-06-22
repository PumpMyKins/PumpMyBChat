package fr.pumpmybchat.command.prefix;

import java.util.List;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.chat.ChatManager;
import fr.pumpmybchat.chat.ChatProfile;
import fr.pumpmybchat.chat.Prefix;
import fr.pumpmybchat.command.utils.ISubCommand;
import fr.pumpmybchat.utils.ChatColorUtils;
import fr.pumpmybchat.utils.InsufisantModificationPrefixException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SetPrefixSubCommand implements ISubCommand {

	private ChatManager chatManager;

	public SetPrefixSubCommand(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		if(args.size() != 1) {

			new HelpPrefixSubCommand().onSubCommand(exec, sender);
			return;

		}

		String stringPrefix = args.get(0).trim();

		if(stringPrefix.isEmpty()) {

			new HelpPrefixSubCommand().onSubCommand(exec, sender);
			return;

		}
		
		if(stringPrefix.length() > 10) {
			
			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Prefix trop long !");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			sender.sendMessage(txt);
			
			return;
		}
		
		ProxiedPlayer player = (ProxiedPlayer) sender;

		if(ChatColorUtils.containsChatColorCodes(stringPrefix) && !player.hasPermission("pumpmybchat.prefix.colored")) {

			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Impossible de colorer votre prefix");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			sender.sendMessage(txt);

			TextComponent txt2 = new TextComponent("Fonctionnalité réservé aux tiers supérieurs");
			txt2.setColor(ChatColor.RED);	
			sender.sendMessage(txt2);

			TextComponent link = new TextComponent("http://store.pumpmykins.eu/");
			link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://store.pumpmykins.eu/"));
			link.setBold(true);
			link.setColor(ChatColor.DARK_BLUE);
			TextComponent txt3 = new TextComponent("Voir : ");
			txt3.setColor(ChatColor.RED);
			txt3.addExtra(link);
			sender.sendMessage(txt3);

		}else {

			stringPrefix = ChatColorUtils.getChatColorCodesTranslatedString(stringPrefix);
			ChatProfile chatProfile = this.chatManager.getPlayerChatProfile(player);
			Prefix prefix = chatProfile.getPrefix();
			
			if(prefix == null) {

				TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
				TextComponent txt1 = new TextComponent("ERREUR : Paramètre de prefix introuvable");
				txt1.setColor(ChatColor.RED);
				txt.addExtra(txt1);			
				player.sendMessage(txt);

				TextComponent txt2 = new TextComponent("Contactez le staff !");
				txt2.setColor(ChatColor.RED);

				player.sendMessage(txt2);
				return;

			}
			
			if(prefix.isBlocked()) {
				
				TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
				TextComponent txt1 = new TextComponent("Cette fonctionnalité vous a été retiré suite à un abus !");
				txt1.setColor(ChatColor.RED);
				txt.addExtra(txt1);			
				player.sendMessage(txt);
				
				TextComponent txt2 = new TextComponent("Contactez le staff !");
				txt2.setColor(ChatColor.RED);
				
				player.sendMessage(txt2);
				return;
				
			}
			
			try {
				
				this.chatManager.updatePlayerPrefixContent(player, stringPrefix);
				
				sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "§bPrefix appliqué !"));				
				
				if(!prefix.isActive()) {
					
					TextComponent activation = new TextComponent("/prefix activate");
					activation.setColor(ChatColor.DARK_BLUE);
					activation.setBold(true);
					activation.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/prefix activate"));
					activation.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder("§bExecuter la commande !").create()));
					TextComponent txt = new TextComponent(" pour activer votre préfix");
					txt.setColor(ChatColor.AQUA);
					activation.addExtra(txt);
					
					sender.sendMessage(activation);
					
				}
				
				
			} catch (InsufisantModificationPrefixException e) {
				
				TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
				TextComponent txt1 = new TextComponent("Vous avez épuisé votre nombre de modification possible !");
				txt1.setColor(ChatColor.RED);
				txt.addExtra(txt1);			
				sender.sendMessage(txt);

				TextComponent txt2 = new TextComponent("Obtenez en plus dans les tiers supérieurs.");
				txt2.setColor(ChatColor.RED);	
				sender.sendMessage(txt2);

				TextComponent link = new TextComponent("http://store.pumpmykins.eu/");
				link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://store.pumpmykins.eu/"));
				link.setBold(true);
				link.setColor(ChatColor.DARK_BLUE);
				TextComponent txt3 = new TextComponent("Voir : ");
				txt3.setColor(ChatColor.RED);
				txt3.addExtra(link);
				sender.sendMessage(txt3);
				
			} catch (Exception e) {
				e.printStackTrace();
				sender.sendMessage(new TextComponent(e.getMessage()));
			}			

		}

	}

}
