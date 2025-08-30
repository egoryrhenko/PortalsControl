package org.ferrum.portalsControl;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.ferrum.portalsControl.commands.ControlCommand;
import org.ferrum.portalsControl.listeners.PortalListener;

import java.util.logging.Logger;

public final class PortalsControl extends JavaPlugin {

    public static PortalsControl plugin;
    //private static Logger logger;

    @Override
    public void onEnable() {
        plugin = this;
        //logger = getLogger();

        ConfigManager.loadConfig();
        PortalListener portalListener = new PortalListener();

        ControlCommand controlCommand = new ControlCommand();

        getServer().getPluginManager().registerEvents(portalListener, this);

        RegisterCommand("portalscontrol", controlCommand, controlCommand, "portalscontrol.command");
    }

    private void RegisterCommand(String name, CommandExecutor commandExecutor, TabCompleter tabCompleter, String permission){
        PluginCommand command = getCommand(name);
        if (command == null) {
            return;
        }
        command.setExecutor(commandExecutor);
        command.setTabCompleter(tabCompleter);
        if (permission != null){
            command.setPermission(permission);
        }
    }
}
