package fr.pumpmybchat.command.prefix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.utils.ISubCommand;
import fr.pumpmybchat.utils.ISubTabCompleter;
import fr.pumpmybchat.utils.SubCommandData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class PrefixCommandExecutor extends Command implements TabExecutor{
	
	private Main main;
	private List<SubCommandData> subCommandList;
	
	private PrefixCommandExecutor(String name) {
		super(name);
		this.subCommandList = new ArrayList<>();
	}

	public PrefixCommandExecutor(Main main) {
		
		this("prefix");
		this.main = main;
		
	}
	
	public void addSubCommand(String sub , String perm , ISubCommand i) {

		this.subCommandList.add(new SubCommandData(sub, perm, i));

	}

	public void addSubCommand(String sub , ISubCommand i) {

		this.subCommandList.add(new SubCommandData(sub, i));

	}

	public List<SubCommandData> getSubCommandList() {
		return subCommandList;
	}

	public Main getMain() {
		return main;
	}

	private static List<String> getArgs(String[] a) {

		List<String> l = new ArrayList<>();

		for (int i = 1; i < a.length; i++) {

			l.add(a[i]);

		}

		return l;

	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(!sender.hasPermission("pumpmybchat.command.prefix")) {
			
			this.sendMsg_BuyInStore(sender);			
			return;
			
		}
		
		if( args.length < 1 ) {

			new HelpPrefixSubCommand().onSubCommand(this, sender);
			return;

		}else {

			String sub = args[0];

			for (SubCommandData s : this.subCommandList) {

				String subCmd = s.getSubCommand();
				String permission = s.getPermissionNode();

				if(sub.equals(subCmd)) {

					if(!permission.equals("none") && !sender.hasPermission(permission)) {
						// pas la permission
						this.sendMsg_BuyInStore(sender);
						return;

					}else {
						// permission trouvé
						//System.out.println("Executor sub command : " + s.getSubCommandExecutor().getClass().getName());
						s.execute(this,sender, getArgs(args));
						return;

					}

				}

			}

			new HelpPrefixSubCommand().onSubCommand(this,sender);
			return;

		}

	}

	@Override
	public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
		List<String> subCmdNameList = new ArrayList<>();

		for (SubCommandData subcmd : this.subCommandList) {

			subCmdNameList.add(subcmd.getSubCommand());

		}

		if(args.length == 1) {

			String arg = args[0];

			if(arg.trim().isEmpty()) {

				return subCmdNameList;

			}else {

				for (Iterator<String> iterator = subCmdNameList.iterator(); iterator.hasNext();) {

					String string = iterator.next();

					if(!string.startsWith(arg)) {

						iterator.remove();

					}

				}

				return subCmdNameList;

			}

		}else if(args.length == 2 & subCmdNameList.contains(args[0])) {			

			String subcommand = args[0];

			for (SubCommandData subcmd : this.subCommandList) {

				if(subcmd.getSubCommand().equalsIgnoreCase(subcommand) & subcmd.getSubCommandExecutor() instanceof ISubTabCompleter) {

					return ((ISubTabCompleter) subcmd.getSubCommandExecutor()).onTabComplete(this, sender, getArgs(args));

				}

				continue;

			}

			return new ArrayList<>();

		}

		return new ArrayList<>();
	}
	
	private void sendMsg_BuyInStore(CommandSender player) {
		
		TextComponent txt = new TextComponent(Main.PLUGIN_PREFIX);
		TextComponent txt1 = new TextComponent("Fonctionnalité achetable en boutique");
		txt1.setColor(ChatColor.RED);
		txt.addExtra(txt1);			
		player.sendMessage(txt);

		TextComponent link = new TextComponent("http://store.pumpmykins.eu/");
		link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://store.pumpmykins.eu/"));
		link.setBold(true);
		link.setColor(ChatColor.DARK_BLUE);
		TextComponent txt2 = new TextComponent("Voir : ");
		txt2.setColor(ChatColor.RED);
		txt2.addExtra(link);
		player.sendMessage(txt2);
		
	}

}
