package dev.larrox.bettermsg;

import dev.larrox.bettermsg.commands.MSGCommand;
import dev.larrox.bettermsg.commands.ReloadCommand;
import dev.larrox.bettermsg.commands.ReplyCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterMSG extends JavaPlugin {

    private static BetterMSG instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        new Util(this);

        getCommand("msg").setExecutor(new MSGCommand());
        getCommand("reply").setExecutor(new ReplyCommand());
        getCommand("reloadmsg").setExecutor(new ReloadCommand());
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    public static BetterMSG getInstance() {
        return instance;
    }
}