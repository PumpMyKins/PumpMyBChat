package fr.pumpmybchat.command;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.command.utils.ISubCommand;
import fr.pumpmybchat.command.utils.ISubTabCompleter;
import fr.pumpmybchat.utils.ChatColorUtils;
import fr.pumpmybchat.utils.DiscordWebhook;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class FakeMsgBChatSubCommand implements ISubCommand,ISubTabCompleter {

	private Main main;

	public FakeMsgBChatSubCommand(Main main) {
		this.main = main;
	}

	@Override
	public void onSubCommand(Command exec, CommandSender sender, List<String> args) {

		if(sender instanceof ProxiedPlayer) {

			TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
			TextComponent txt1 = new TextComponent("Console command only !");
			txt1.setColor(ChatColor.RED);
			txt.addExtra(txt1);			
			sender.sendMessage(txt);

		}else {

			if(args.size() > 3 && this.onTabComplete(exec, sender, args).contains(args.get(0)) && !args.get(1).trim().isEmpty()) {

				String message = "";
				for (int i = 2; i < (args.size()-1); i++) {
					message += args.get(i);
				}

				message = ChatColorUtils.getChatColorCodesTranslatedString(message);

				TextComponent messages = new TextComponent();
				TextComponent tcStartPrefix = new TextComponent("[");
				tcStartPrefix.setColor(ChatColor.GOLD);

				TextComponent staff = new TextComponent("۞");
				staff.setBold(true);
				staff.setColor(ChatColor.DARK_RED);
				messages.addExtra(staff);

				messages.addExtra(tcStartPrefix);

				TextComponent nicknameText = new TextComponent(args.get(1));

				messages.addExtra(nicknameText);

				TextComponent bet = new TextComponent(" > ");
				bet.setColor(ChatColor.GOLD);
				bet.setBold(true);

				messages.addExtra(bet);

				messages.addExtra(new TextComponent(message));		

				ServerInfo serverInfo = this.main.getProxy().getServerInfo(args.get(0));

				ProxyServer.getInstance().getLogger().log(Level.INFO,"[" + serverInfo.getName() + "]" + messages.toLegacyText());


				for (ProxiedPlayer receiver : serverInfo.getPlayers()) {
					receiver.sendMessage(messages);
				}

				String webhookUrl = this.main.getConfigManager().getWebHookUrl(serverInfo.getName());

				if(!webhookUrl.equalsIgnoreCase("none")) {

					this.main.getProxy().getScheduler().runAsync(this.main, new Runnable() {

						@Override
						public void run() {			

							try {

								DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
								Date now = new Date(System.currentTimeMillis());

								DiscordWebhook webhook = new DiscordWebhook(webhookUrl);
								webhook.setTts(false);
								webhook.setUsername("PumpMyBChat");

								webhook.addEmbed(new DiscordWebhook.EmbedObject().setTitle("" + shortDateFormat.format(now)).setDescription(messages.toPlainText()));

								webhook.execute();

							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}
					});

				}

			}else {

				TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
				TextComponent txt1 = new TextComponent("Syntahx error");
				txt1.setColor(ChatColor.RED);
				txt.addExtra(txt1);			
				sender.sendMessage(txt);

			}

		}

	}

	@Override
	public List<String> onTabComplete(Command command, CommandSender sender, List<String> args) {

		return new ArrayList<>(this.main.getProxy().getServers().keySet());

	}

}
