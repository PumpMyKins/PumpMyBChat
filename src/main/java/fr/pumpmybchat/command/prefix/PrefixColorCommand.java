package fr.pumpmybchat.command.prefix;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;

public class PrefixColorCommand extends QSubCommand {

	@Override
	public String getPermission() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCommand(CommandSender sender, String[] args) {

		TextComponent intro = new TextComponent("Liste des couleurs :");
		intro.setColor(ChatColor.WHITE);
		TextComponent preColor = new TextComponent("******************");
		preColor.setObfuscated(true);
		preColor.setColor(ChatColor.GOLD);
		TextComponent black = new TextComponent("&0 Couleur Noir");
		black.setColor(ChatColor.BLACK);
		TextComponent dark_blue = new TextComponent("&1 Couleur bleu foncé");
		dark_blue.setColor(ChatColor.DARK_BLUE);
		TextComponent dark_green = new TextComponent("&2 Couleur vert foncé");
		dark_green.setColor(ChatColor.DARK_GREEN);
		TextComponent sky_blue = new TextComponent("&3 Couleur Bleu Ciel");
		sky_blue.setColor(ChatColor.DARK_AQUA);
		TextComponent dark_red = new TextComponent("&4 Couleur rouge foncé");
		dark_red.setColor(ChatColor.DARK_RED);
		TextComponent dark_purple = new TextComponent("&5 Couleur Violet foncé");
		dark_purple.setColor(ChatColor.DARK_PURPLE);
		TextComponent gold = new TextComponent("&6 Couleur Or");
		gold.setColor(ChatColor.GOLD);
		TextComponent grey = new TextComponent("&7 Couleur gris claire");
		grey.setColor(ChatColor.GRAY);
		TextComponent dark_gray = new TextComponent("&8 Couleur gris foncé");
		dark_gray.setColor(ChatColor.DARK_GRAY);
		TextComponent light_blue = new TextComponent("&9 Couleur bleu claire");
		light_blue.setColor(ChatColor.BLUE);
		TextComponent green = new TextComponent("&a Couleur vert claire");
		green.setColor(ChatColor.GREEN);
		TextComponent aqua = new TextComponent("&b Couleur bleu cyan");
		aqua.setColor(ChatColor.AQUA);
		TextComponent red = new TextComponent("&c Couleur rouge");
		red.setColor(ChatColor.RED);
		TextComponent magenta = new TextComponent("&d Couleur Violet claire / Magenta");
		magenta.setColor(ChatColor.LIGHT_PURPLE);
		TextComponent yellow = new TextComponent("&e Couleur jaune");
		yellow.setColor(ChatColor.YELLOW);
		TextComponent white = new TextComponent("&f Couleur blanche");
		white.setColor(ChatColor.WHITE);
		TextComponent reset = new TextComponent("&r Pour reset !");
		TextComponent italique = new TextComponent("&o Pour mettre en italique");
		italique.setItalic(true);
		TextComponent souligne = new TextComponent("&n Pour Souligner");
		souligne.setUnderlined(true);
		TextComponent barre = new TextComponent("&m Pour barré");
		barre.setStrikethrough(true);
		TextComponent gras = new TextComponent("&l Pour mettre en gras");
		gras.setBold(true);
		TextComponent magic = new TextComponent("&k Pour les caractères dynamique ");
		TextComponent magiccomp = new TextComponent("azerty");
		magiccomp.setObfuscated(true);
		magic.addExtra(magiccomp);
		
		
		sender.sendMessage(black);
		sender.sendMessage(dark_blue);
		sender.sendMessage(dark_green);
		sender.sendMessage(sky_blue);
		sender.sendMessage(dark_red);
		sender.sendMessage(dark_purple);
		sender.sendMessage(dark_purple);
		sender.sendMessage(gold);
		sender.sendMessage(grey);
		sender.sendMessage(dark_gray);
		sender.sendMessage(light_blue);
		sender.sendMessage(green);
		sender.sendMessage(aqua);
		sender.sendMessage(red);
		sender.sendMessage(magenta);
		sender.sendMessage(yellow);
		sender.sendMessage(white);
		sender.sendMessage(reset);
		sender.sendMessage(italique);
		sender.sendMessage(souligne);
		sender.sendMessage(barre);
		sender.sendMessage(gras);
		sender.sendMessage(magic);
		
	}

}
