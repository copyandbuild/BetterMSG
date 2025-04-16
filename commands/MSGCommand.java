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

import java.util.HashMap;
import java.util.UUID;

public class MSGCommand implements CommandExecutor {

    private static final String COLOR_PERMISSION = BetterMSG.getInstance().getConfig().getString("permissions.use-color");
    private static final HashMap<UUID, UUID> lastMessageMap = new HashMap<>();
    private static final Util util = Util.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String playersonly = util.getMessage("messages.players-only");
        String prefix = util.getMessage("messages.prefix");
        String use = util.getMessage("messages.use");
        String playerLabel = util.getMessage("messages.player");
        String messageLabel = util.getMessage("messages.message");
        String notfound = util.getMessage("messages.not-found");
        String to = util.getMessage("messages.to");
        String from = util.getMessage("messages.from");

        if (!(sender instanceof Player)) {
            sender.sendMessage(playersonly);
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage(prefix + use + "§8: §7/msg <" + playerLabel + "> <" + messageLabel + ">");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(prefix + playerLabel + " " + notfound);
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

        target.sendMessage("§8[§a" + util.getPlayerName(player) + " §8-> §e" + to + "§8] §7" + message);
        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);

        player.sendMessage("§8[§a" + from + " §8-> §e" + util.getPlayerName(target) + "§8] §7" + message);

        lastMessageMap.put(player.getUniqueId(), target.getUniqueId());
        lastMessageMap.put(target.getUniqueId(), player.getUniqueId());

        return true;
    }

    public static UUID getLastMessaged(UUID playerUUID) {
        return lastMessageMap.get(playerUUID);
    }
}