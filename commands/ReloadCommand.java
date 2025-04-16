package dev.larrox.bettermsg.commands;

import dev.larrox.bettermsg.BetterMSG;
import dev.larrox.bettermsg.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission(BetterMSG.getInstance().getConfig().getString("permissions.reload"))) {
            BetterMSG.getInstance().reloadConfig();
            Util.getInstance().loadMessagesFile();
            sender.sendMessage(Util.getInstance().getMessage("messages.config-reload-success"));
            return true;
        } else {
            sender.sendMessage(Util.getInstance().getMessage("messages.no-permission"));
            return true;
        }
    }
}