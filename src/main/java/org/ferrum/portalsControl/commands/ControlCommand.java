package org.ferrum.portalsControl.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.ferrum.portalsControl.ConfigManager;
import org.ferrum.portalsControl.listeners.PortalListener;

import java.util.List;

public class ControlCommand implements CommandExecutor, TabCompleter {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(command.getUsage());
            return true;
        }

        if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("portalscontrol.reload")) {
            ConfigManager.loadConfig();
            sender.sendMessage("§aPortalsControl config reloaded.");
            return true;
        }

        String worldType = args[0].toLowerCase();
        Boolean isBlock = null;

        if (args.length == 2) {
            if (args[1].equalsIgnoreCase("on")) isBlock = false;
            if (args[1].equalsIgnoreCase("off")) isBlock = true;
        }

        switch (worldType) {
            case "world" -> {
                if (isBlock != null) {
                    PortalListener.blockWorld = isBlock;
                    ConfigManager.setValue("Block_teleport_to.world", isBlock);
                    sender.sendMessage(ConfigManager.getMessage(isBlock ? "ToWorldPortalOff" : "ToWorldPortalOn"));
                } else {
                    sender.sendMessage(ConfigManager.getMessage(PortalListener.blockWorld ? "ToWorldPortalOff" : "ToWorldPortalOn"));
                }
                return true;
            }
            case "nether" -> {
                if (isBlock != null) {
                    PortalListener.blockNether = isBlock;
                    ConfigManager.setValue("Block_teleport_to.nether", isBlock);
                    sender.sendMessage(ConfigManager.getMessage(isBlock ? "ToNetherPortalOff" : "ToNetherPortalOn"));
                } else {
                    sender.sendMessage(ConfigManager.getMessage(PortalListener.blockNether ? "ToNetherPortalOff" : "ToNetherPortalOn"));
                }
                return true;
            }
            case "end" -> {
                if (isBlock != null) {
                    PortalListener.blockEnd = isBlock;
                    ConfigManager.setValue("Block_teleport_to.end", isBlock);
                    sender.sendMessage(ConfigManager.getMessage(isBlock ? "ToEndPortalOff" : "ToEndPortalOn"));
                } else {
                    sender.sendMessage(ConfigManager.getMessage(PortalListener.blockEnd ? "ToEndPortalOff" : "ToEndPortalOn"));
                }
                return true;
            }
            default -> sender.sendMessage(ConfigManager.getMessage("UnknownWorldType") + worldType);
        }

        sender.sendMessage(command.getUsage());
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return switch (strings.length) {
            case 1 -> commandSender.hasPermission("portalscontrol.reload") ? List.of("reload", "world", "nether", "end") : List.of("world", "nether", "end");
            case 2 -> strings[0].equals("reload") ? List.of() : List.of("on", "off");
            default -> List.of();
        };
    }
}
