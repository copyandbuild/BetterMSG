package dev.larrox.bettermsg;

import dev.larrox.bettermsg.commands.MSGCommand;
import dev.larrox.bettermsg.commands.BetterMSGCommand;
import dev.larrox.bettermsg.commands.ReplyCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class BetterMSG extends JavaPlugin {

    private static BetterMSG instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        Util util = new Util(this);
        Util.getInstance().loadMessagesFile();

        getCommand("msg").setExecutor(new MSGCommand(this, util));
        getCommand("reply").setExecutor(new ReplyCommand(this, util));
        getCommand("bettermsg").setExecutor(new BetterMSGCommand(this, util));
    }

    public static BetterMSG getInstance() {
        return instance;
    }
}