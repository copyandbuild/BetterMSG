package dev.larrox.bettermsg;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class Util {

    private static Util instance;
    private static BetterMSG betterMSG;
    private FileConfiguration messagesConfig;
    public static final HashMap<UUID, UUID> lastMessageMap = new HashMap<>();
    private File messagesFile;

    public Util(BetterMSG plugin) {
        betterMSG = plugin;
        instance = this;
        loadMessagesFile();
    }

    public static Util getInstance() {
        return instance;
    }

    public void loadMessagesFile() {
        messagesFile = new File(betterMSG.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            betterMSG.saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public String getMessage(String path) {
        String message = messagesConfig.getString(path, "Message not found: " + path);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String getPlayerName(Player player) {
        if (betterMSG.getConfig().getBoolean("use-displayname")) {
            return player.getDisplayName();
        } else {
            return player.getName();
        }
    }

}