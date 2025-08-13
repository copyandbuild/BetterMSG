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

    private final String COLOR_PERMISSION = BetterMSG.getInstance().getConfig().getString("permissions.use-color");
    private final Util util;
    private final BetterMSG betterMSG;

    public ReplyCommand(BetterMSG betterMSG, Util util) {
        this.betterMSG = betterMSG;
        this.util = util;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(util.getMessage("messages.players-only"));
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(util.getMessage("messages.prefix") + util.getMessage("messages.use-reply"));
            return true;
        }

        UUID lastMessagedUUID = MSGCommand.getLastMessaged(player.getUniqueId());
        if (lastMessagedUUID == null) {
            player.sendMessage(util.getMessage("messages.prefix") + util.getMessage("messages.nothing-to-reply"));
            return true;
        }

        Player target = Bukkit.getPlayer(lastMessagedUUID);
        if (target == null || !target.isOnline()) {
            player.sendMessage(util.getMessage("messages.prefix") + util.getMessage("messages.not-found")
                    .replace("%to%", "Unknown"));
            return true;
        }

        String message = String.join(" ", args);
        if (player.hasPermission(COLOR_PERMISSION)) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }

        String senderName = util.getPlayerName(player);
        String targetName = util.getPlayerName(target);

        String targetFormat = util.getMessage("messages.target-msg");
        target.sendMessage(ChatColor.translateAlternateColorCodes('&',
                targetFormat.replace("%from%", senderName)
                        .replace("%to%", targetName)
                        .replace("%prefix%", util.getMessage("messages.prefix"))
                        .replace("%message%", message)));

        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);

        String senderFormat = util.getMessage("messages.sender-msg");
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                senderFormat.replace("%from%", senderName)
                        .replace("%to%", targetName)
                        .replace("%prefix%", util.getMessage("messages.prefix"))
                        .replace("%message%", message)));

        util.lastMessageMap.put(player.getUniqueId(), target.getUniqueId());
        util.lastMessageMap.put(target.getUniqueId(), player.getUniqueId());


        return true;
    }
}
