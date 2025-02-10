package dev.larrox.bettermsg;

import org.bukkit.entity.Player;

public class Util {

    public Util instance;
    private static BetterMSG betterMSG;
    Player player;
    String playername;

    public String getPlayerName() {

        if (BetterMSG.getInstance().getConfig().getBoolean("use-displayname")) {
            return player.getDisplayName();
        } else {
            return player.getName();
        }

    }

    public String getConfigMessage(String path){
        return betterMSG.getConfigMessage(path);
    }

    public Util getInstance() {
        return instance;
    }

}
