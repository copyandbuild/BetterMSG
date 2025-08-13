package dev.larrox.bettermsg.commands;

import dev.larrox.bettermsg.BetterMSG;
import dev.larrox.bettermsg.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BetterMSGCommand implements CommandExecutor, TabCompleter {

    private final BetterMSG betterMSG;
    private final Util util;
    private final String reloadPermission;

    public BetterMSGCommand(BetterMSG betterMSG, Util util) {
        this.betterMSG = betterMSG;
        this.util = util;
        this.reloadPermission = betterMSG.getConfig().getString("permissions.reload", "bettermsg.reload");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String noPermissionMessage = util.getMessage("messages.no-permission");
        String reloadSuccessMessage = util.getMessage("messages.config-reload-success");
        String invalidOptionMessage = util.getMessage("messages.invalid-reload-option");
        String prefix = util.getMessage("messages.prefix");

        if (!sender.hasPermission(reloadPermission)) {
            sender.sendMessage(noPermissionMessage);
            return true;
        }

        if (args.length < 1) {
            sender.sendMessage(prefix +invalidOptionMessage);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                betterMSG.reloadConfig();
                util.loadMessagesFile();
                sender.sendMessage(prefix + reloadSuccessMessage);
                break;
            default:
                sender.sendMessage(prefix +invalidOptionMessage);
                return true;
        }
        return true;
    }

    @Override
    public  List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission(reloadPermission)) {
            return List.of();
        }

        if (args.length == 1) {
            List<String> options = Arrays.asList("reload");
            String input = args[0].toLowerCase();
            List<String> matches = new ArrayList<>();
            for (String option : options) {
                if (option.startsWith(input)) {
                    matches.add(option);
                }
            }
            return matches;
        }
        return List.of();
    }
}
