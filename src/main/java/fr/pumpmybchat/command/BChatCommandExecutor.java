package fr.pumpmybchat.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fr.pumpmybchat.Main;
import fr.pumpmybchat.command.utils.ISubCommand;
import fr.pumpmybchat.command.utils.ISubTabCompleter;
import fr.pumpmybchat.command.utils.SubCommandData;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class BChatCommandExecutor extends Command implements TabExecutor{

	public static final TextComponent NO_PERM = new TextComponent("§cVous n'avez pas la permission de faire cela !");
	
	private Main main;
	private List<SubCommandData> subCommandList;
	
	private BChatCommandExecutor(String name) {
		super(name);
		this.subCommandList = new ArrayList<>();
	}

	public BChatCommandExecutor(Main main) {
		
		this("pumpmybchat");
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
		
		if(sender instanceof ProxiedPlayer) {
			sender.sendMessage(new TextComponent("Must be a player"));
			return;
		}
		
		if( args.length < 1 ) {

			sender.sendMessage(new TextComponent("Bad args"));
			return;

		}else {

			String sub = args[0];

			for (SubCommandData s : this.subCommandList) {

				String subCmd = s.getSubCommand();
				String permission = s.getPermissionNode();

				if(sub.equals(subCmd)) {

					if(!permission.equals("none") && !sender.hasPermission(permission)) {
						// pas la permission
						sender.sendMessage(new TextComponent("No enough permission"));
						return;

					}else {
						// permission trouvé
						//System.out.println("Executor sub command : " + s.getSubCommandExecutor().getClass().getName());
						s.execute(this,sender, getArgs(args));
						return;

					}

				}

			}

			sender.sendMessage(new TextComponent("Bad args"));
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

}
