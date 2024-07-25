package dev.larrox.bettermsg.commands;

import dev.larrox.bettermsg.BetterMSG;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MSGCommand implements CommandExecutor {

    private static final String COLOR_PERMISSION = "bettermsg.color";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Du musst ein Spieler sein, um diesen Command auszuführen");
            return true;
        }

        Player player = (Player) sender;
        String prefix = BetterMSG.getInstance().getMessagePrefix();

        if (args.length == 0) {
            player.sendMessage(prefix + "Benutze: /msg <Spieler> <Nachricht>");
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(prefix + "Benutze: /msg <Spieler> <Nachricht>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(prefix + "Spieler nicht gefunden!");
            return true;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            messageBuilder.append(args[i]);
            if (i < args.length - 1) {
                messageBuilder.append(" ");
            }
        }
        String message = messageBuilder.toString();

        if (player.hasPermission(COLOR_PERMISSION)) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        target.sendMessage("§8[§a" + player.getName() + " §8-> §eDir§8] §7" + message);
        player.sendMessage("§8[§aDu §8-> §e" + target.getName() + "§8] §7" + message);
        return true;
    }
}
