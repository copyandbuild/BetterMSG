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

    private static final String COLOR_PERMISSION = BetterMSG.getInstance().getConfig().getString("permissions.use-color");
    private static final Util util = Util.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String playersonly = util.getMessage("messages.players-only");
        String prefix = util.getMessage("messages.prefix");
        String use = util.getMessage("messages.use");
        String playerNotFound = util.getMessage("messages.not-found");
        String NothingToReply = util.getMessage("messages.nothing-to-reply");
        String to = util.getMessage("messages.to");
        String from = util.getMessage("messages.from");

        if (!(sender instanceof Player)) {
            sender.sendMessage(playersonly);
            return true;
        }

        Player player = (Player) sender;
        UUID lastMessagedUUID = MSGCommand.getLastMessaged(player.getUniqueId());

        if (lastMessagedUUID == null) {
            player.sendMessage(prefix + NothingToReply);
            return true;
        }

        Player target = Bukkit.getPlayer(lastMessagedUUID);

        if (target == null) {
            player.sendMessage(prefix + playerNotFound);
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(prefix + use + " §8: /msg <player> <message>");
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

        target.sendMessage("§8[§a" + util.getPlayerName(player) + " §8-> §e" + to + "§8] §7" + message);
        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);

        player.sendMessage("§8[§a" + from + " §8-> §e" + util.getPlayerName(target) + "§8] §7" + message);

        return true;
    }
}