package dev.larrox.bettermsg.commands;

import dev.larrox.bettermsg.BetterMSG;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("bettermsg.reload")) {
            BetterMSG.getInstance().reloadConfig();
            sender.sendMessage("§aDie Konfiguration wurde erfolgreich neu geladen.");
            return true;
        } else {
            sender.sendMessage("§cDu hast keine Berechtigung, diesen Befehl auszuführen.");
            return true;
        }
    }
}
