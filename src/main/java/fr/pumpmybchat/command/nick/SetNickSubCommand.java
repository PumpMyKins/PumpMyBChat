package fr.pumpmybchat.command.nick;

import java.util.List;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.chat.ChatManager;
import fr.pumpmybchat.chat.ChatProfile;
import fr.pumpmybchat.chat.Nickname;
import fr.pumpmybchat.command.utils.ISubCommand;
import fr.pumpmybchat.utils.ChatColorUtils;
import fr.pumpmybchat.utils.InsufisantModificationException;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SetNickSubCommand implements ISubCommand {

	private ChatManager chatManager;

	public SetNickSubCommand(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		if(args.size() != 1) {

			new HelpNickSubCommand().onSubCommand(exec, sender);
			return;

		}

		String stringNick = args.get(0).trim();

		if(stringNick.isEmpty()) {

			new HelpNickSubCommand().onSubCommand(exec, sender);
			return;

		}
		
		ProxiedPlayer player = (ProxiedPlayer) sender;

		if(ChatColorUtils.containsChatColorCodes(stringNick) && !player.hasPermission("pumpmybchat.nick.colored")) {

			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Impossible de colorer votre surnom");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			sender.sendMessage(txt);

			TextComponent txt2 = new TextComponent("Fonctionnalité réservée aux tiers supérieurs");
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

			if(ChatColorUtils.getWithoutChatColorCodesString(stringNick).length() > 14) {
				
				TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
				TextComponent txt1 = new TextComponent("Surnom trop long !");
				txt1.setColor(ChatColor.RED);
				txt.addExtra(txt1);			
				sender.sendMessage(txt);
				
				return;
			}
			
			stringNick = ChatColorUtils.getChatColorCodesTranslatedString(stringNick);
			ChatProfile chatProfile = this.chatManager.getPlayerChatProfile(player);
			
			if(chatProfile == null) {

				TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
				TextComponent txt1 = new TextComponent("ERREUR : Paramètre ChatProfile introuvable");
				txt1.setColor(ChatColor.RED);
				txt.addExtra(txt1);			
				player.sendMessage(txt);
				
				TextComponent txt3 = new TextComponent("Contactez le staff !");
				txt3.setColor(ChatColor.RED);
				
				player.sendMessage(txt3);
				return;

			}
			
			Nickname nick = chatProfile.getNickname();
			
			if(nick == null) {

				TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
				TextComponent txt1 = new TextComponent("ERREUR : Paramètre de surnom introuvable");
				txt1.setColor(ChatColor.RED);
				txt.addExtra(txt1);			
				player.sendMessage(txt);
				
				TextComponent txt2 = new TextComponent("Si vous venez d'acheter la fonctionnalité en boutique, patientez quelques minutes.");
				txt2.setColor(ChatColor.RED);		
				player.sendMessage(txt2);
				
				TextComponent txt3 = new TextComponent("Sinon, contactez le staff !");
				txt3.setColor(ChatColor.RED);
				
				player.sendMessage(txt3);
				return;

			}
			
			if(nick.isBlocked()) {
				
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
				
				boolean modificated = true;
				if(player.hasPermission("pumpmybchat.nick.infinite")) {
					modificated = false;					
				}
				
				this.chatManager.updatePlayerNickContent(player, stringNick,modificated);
				
				sender.sendMessage(new TextComponent(Main.PLUGIN_PREFIX + "§bSurnom appliqué !"));
				
				if(modificated) {
					
					TextComponent txt = new TextComponent("Nombre de modifcation restante : ");
					txt.setColor(ChatColor.AQUA);
					TextComponent txt1 = new TextComponent("" + nick.getModification());
					txt1.setColor(ChatColor.DARK_BLUE);
					txt.addExtra(txt1);
					sender.sendMessage(txt);
					
				}
				
				if(!nick.isActive()) {
					
					TextComponent activation = new TextComponent("/nick activate");
					activation.setColor(ChatColor.DARK_BLUE);
					activation.setBold(true);
					activation.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/nick activate"));
					activation.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder("§bExecuter la commande !").create()));
					TextComponent txt2 = new TextComponent(" pour activer votre surnom");
					txt2.setColor(ChatColor.AQUA);
					activation.addExtra(txt2);
					
					sender.sendMessage(activation);
					
				}
				
				
			} catch (InsufisantModificationException e) {
				
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
