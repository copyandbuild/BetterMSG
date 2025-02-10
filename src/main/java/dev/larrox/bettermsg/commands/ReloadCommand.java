package dev.larrox.bettermsg.commands;

import dev.larrox.bettermsg.BetterMSG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission(BetterMSG.getInstance().getConfigPerm("color"))) {
            BetterMSG.getInstance().reloadConfig();
            sender.sendMessage(BetterMSG.getInstance().getConfigMessage("config-reload-success"));
            return true;
        } else {
            sender.sendMessage(BetterMSG.getInstance().getConfigMessage("no-permission"));
            return true;
        }
    }
}
