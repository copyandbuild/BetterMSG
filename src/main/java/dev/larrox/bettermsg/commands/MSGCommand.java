package dev.larrox.bettermsg.commands;

import dev.larrox.bettermsg.BetterMSG;
import org.bukkit.Bukkit;
import dev.larrox.bettermsg.Util;
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

    private final BetterMSG betterMSG;
    private final Util util;

    public MSGCommand(BetterMSG betterMSG, Util util) {
        this.betterMSG = betterMSG;
        this.util = util;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            String playersOnlyMsg = util.getMessage("messages.players-only");
            sender.sendMessage(playersOnlyMsg);
            return true;
        }

        if (args.length < 2) {
            String useMsg = util.getMessage("messages.use");
            String prefix = util.getMessage("messages.prefix");
            player.sendMessage(prefix + useMsg);
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null || !target.isOnline()) {
            String notFoundMsg = util.getMessage("messages.not-found").replace("%to%", args[0]);
            String prefix = util.getMessage("messages.prefix");
            player.sendMessage(prefix + notFoundMsg);
            return true;
        }

        String message = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));
        if (player.hasPermission(COLOR_PERMISSION)) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        String senderName = util.getPlayerName(player);
        String targetName = util.getPlayerName(target);

        String targetMsgFormat = util.getMessage("messages.target-msg");
        String formattedTargetMsg = targetMsgFormat
                .replace("%from%", senderName)
                .replace("%to%", targetName)
                .replace("%prefix%", util.getMessage("messages.prefix"))
                .replace("%message%", message);
        target.sendMessage(ChatColor.translateAlternateColorCodes('&', formattedTargetMsg));
        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);

        // Nachricht an Sender senden - Format aus config
        String senderMsgFormat = util.getMessage("messages.sender-msg");
        String formattedSenderMsg = senderMsgFormat
                .replace("%from%", senderName)
                .replace("%to%", targetName)
                .replace("%prefix%", util.getMessage("messages.prefix"))
                .replace("%message%", message);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', formattedSenderMsg));

        util.lastMessageMap.put(player.getUniqueId(), target.getUniqueId());
        util.lastMessageMap.put(target.getUniqueId(), player.getUniqueId());

        return true;
    }

    public static UUID getLastMessaged(UUID playerUUID) {
        return Util.lastMessageMap.get(playerUUID);
    }
}
