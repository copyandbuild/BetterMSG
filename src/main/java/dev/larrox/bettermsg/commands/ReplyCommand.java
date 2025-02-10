package dev.larrox.bettermsg.commands;

import dev.larrox.bettermsg.BetterMSG;
import dev.larrox.bettermsg.Util;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReplyCommand implements CommandExecutor {

    private static final String COLOR_PERMISSION = BetterMSG.getInstance().getConfigPerm("color");
    private static Util util;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String playersonly = getConfigMessage("players-only");
        String prefix = getConfigMessage("prefix");
        String Use = getConfigMessage("use");
        String Player = getConfigMessage("player");
        String Message = getConfigMessage("Nachricht");
        String notfound = getConfigMessage("not-found");
        String to = getConfigMessage("to");
        String from = getConfigMessage("from");

        if (!(sender instanceof Player)) {
            sender.sendMessage("Du musst ein Spieler sein, um diesen Command auszuführen");
            return true;
        }

        Player player = (Player) sender;

        UUID lastMessagedUUID = MSGCommand.getLastMessaged(player.getUniqueId());

        if (lastMessagedUUID == null) {
            player.sendMessage(prefix + Player + notfound);
            return true;
        }

        Player target = Bukkit.getPlayer(lastMessagedUUID);

        if (target == null) {
            player.sendMessage(prefix + Player + notfound);
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(prefix + Use +"§8: /msg <"+Player+"> <"+Message+">");
            return true;
        }

        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            messageBuilder.append(args[i]);
            if (i < args.length - 1) {
                messageBuilder.append(" ");
            }
        }
        String message = messageBuilder.toString();

        if (player.hasPermission(COLOR_PERMISSION)) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        target.sendMessage("§8[§a" + util.getInstance().getPlayerName() + " §8-> §e"+to+"§8] §7" + message);
        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);

        player.sendMessage("§8[§a"+from+" §8-> §e" + util.getInstance().getPlayerName() + "§8] §7" + message);

        return true;
    }
    public String getConfigMessage(String path) {
        return util.getConfigMessage(path);
    }
}
