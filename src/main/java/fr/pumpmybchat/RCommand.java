package fr.pumpmybchat;

import fr.pumpmybchat.chat.ChatProfile;
import fr.pumpmybchat.utils.ChatColorUtils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class RCommand extends Command {

	private Main main;

	public RCommand(Main main, String string) {
		super(string);
		this.main = main;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		CommandSender target;
		try {
			target = this.main.getMsgManager().getLastMessageSenderName(sender);
		} catch (Exception e) {
			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Impossible de répondre !");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			sender.sendMessage(txt);
			return;
		}
		
		String message = this.getMessage(args);
		
		if(message.trim().isEmpty()) {
			
			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Réponse vide !");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			sender.sendMessage(txt);
			return;
			
		}
		
		if(ChatColorUtils.containsChatColorCodes(message) && !sender.hasPermission("pumpmybchat.msg.colored")) {

			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Impossible de colorer votre surnom");
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
			if(chatProfile.getNickname().hasOne()) {
				
				senderName = new TextComponent(chatProfile.getNickname().getUnSafeNickname());
				senderName.setHoverEvent(new HoverEvent(Action.SHOW_TEXT,new ComponentBuilder("§bPseudo d'origine : §1" + sender.getName() + "\n§bCliquez pour répondre !").create()));
				
			}else {
				
				senderName = new TextComponent(player.getName());
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
		
		target.sendMessage(messages);
		
		if(target instanceof ProxiedPlayer) {
			
			ProxiedPlayer player = (ProxiedPlayer) target;	
			
			ChatProfile chatProfile = this.main.getChatManager().getPlayerChatProfile(player);
			if(chatProfile.getNickname().hasOne()) {
				
				senderName = new TextComponent(chatProfile.getNickname().getUnSafeNickname());
				
			}else {
				
				senderName = new TextComponent(player.getName());
				
			}			
			
		}else {
			
			senderName = new TextComponent("Console");
			
		}
		
		messages = new TextComponent();
		messages.addExtra(tcStartPrefix);		
		messages.addExtra(you);
		messages.addExtra(bet);
		messages.addExtra(senderName);
		messages.addExtra(bet1);
		messages.addExtra(new TextComponent(message));
		
		sender.sendMessage(messages);

	}

	private String getMessage(String[] args) {

		String msg = "";	

		for (int i = 0; i < args.length; i++) {

			msg += args[i] + " ";

		}

		return msg;

	}

}
