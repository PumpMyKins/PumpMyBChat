package fr.pumpmybchat.command;

import java.util.ArrayList;
import java.util.List;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.chat.ChatProfile;
import fr.pumpmybchat.chat.Nickname;
import fr.pumpmybchat.utils.ChatColorUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class MsgCommand extends Command implements TabExecutor{

	private Main main;

	public MsgCommand(Main main,String name, String... aliases) {
		super(name, "pumpmybchat.command.msg", aliases);
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if(args.length < 1) {

			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Vous devez spécifier le joueur auquel vous souhaitez envoyer un message !");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			sender.sendMessage(txt);

			return;
		}

		if(!getProxiedPlayerNames().contains(args[0])) {
			
			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Joueur introuvable !");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			sender.sendMessage(txt);
			
			return;
		}

		//&& Lists.newArrayList(this.onTabComplete(sender, args)).contains(args[0])) {

		String message = this.getMessage(args);

		if(ChatColorUtils.containsChatColorCodes(message) && !sender.hasPermission("pumpmybchat.msg.colored")) {

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

			return;

		}

		message = ChatColorUtils.getChatColorCodesTranslatedString(message);

		TextComponent messages = new TextComponent();
		TextComponent tcStartPrefix = new TextComponent("[");
		tcStartPrefix.setColor(ChatColor.GOLD);

		TextComponent senderName;
		if(sender instanceof ProxiedPlayer) {

			ProxiedPlayer player = (ProxiedPlayer) sender;

			if(player.hasPermission("pumpmybchat.prefix.staff")) {
				TextComponent staff = new TextComponent("۞");
				staff.setBold(true);
				staff.setColor(ChatColor.DARK_RED);
				messages.addExtra(staff);
			}

			ChatProfile chatProfile = this.main.getChatManager().getPlayerChatProfile(player);
			Nickname nick = chatProfile.getNickname();		
			
			if(nick!= null && nick.isActive() && !nick.isBlocked()) {

				senderName = new TextComponent(nick.getNick());
				senderName.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder("§bPseudo d'origine : §1" + sender.getName() + "\n§bCliquez pour répondre !").create()));

			}else {

				senderName = new TextComponent(player.getDisplayName());
				senderName.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder("§bCliquez pour répondre !").create()));

			}

			senderName.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.SUGGEST_COMMAND, "/msg " + sender.getName()));


		}else {

			senderName = new TextComponent("Console");

		}

		messages.addExtra(tcStartPrefix);
		messages.addExtra(senderName);

		TextComponent bet = new TextComponent(" => ");
		bet.setColor(ChatColor.GOLD);

		messages.addExtra(bet);

		TextComponent you = new TextComponent("You");

		messages.addExtra(you);

		TextComponent bet1 = new TextComponent(" > ");
		bet1.setColor(ChatColor.GOLD);
		bet1.setBold(true);

		messages.addExtra(bet1);

		messages.addExtra(new TextComponent(message));

		ProxiedPlayer player = this.main.getProxy().getPlayer(args[0]);		
		player.sendMessage(messages);			

		ChatProfile chatProfile = this.main.getChatManager().getPlayerChatProfile(player);
		Nickname nick = chatProfile.getNickname();		
		
		if(nick!= null && nick.isActive() && !nick.isBlocked()) {

			senderName = new TextComponent(nick.getNick());

		}else {

			senderName = new TextComponent(player.getName());

		}

		messages = new TextComponent();
		messages.addExtra(tcStartPrefix);		
		messages.addExtra(you);
		messages.addExtra(bet);
		messages.addExtra(senderName);
		messages.addExtra(bet1);
		messages.addExtra(new TextComponent(message));

		sender.sendMessage(messages);

		this.main.getMsgManager().addLastMessageSender(player, sender);		

	}
	
	public static List<String> getProxiedPlayerNames(){
		
		List<String> l = new ArrayList<>();

		for (ProxiedPlayer player : ProxyServer.getInstance().getPlayers()) {
			
			l.add(player.getName());
			
		}
		
		return l;
		
	}

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {

		List<String> l = new ArrayList<>();
		
		for (String playerName : getProxiedPlayerNames()) {

			if(args.length == 0) {
				l.add(playerName);
				continue;
			}

			if(args.length == 1) {

				if(args[0].trim().isEmpty()) {
					l.add(playerName);
					continue;
				}

				if(playerName.startsWith(args[0])) {
					l.add(playerName);
					continue;

				}

			}

		}

		return l;

	}

	private String getMessage(String[] args) {

		String msg = "";	

		for (int i = 1; i < args.length; i++) {

			msg += args[i] + " ";

		}

		return msg;

	}	

}
