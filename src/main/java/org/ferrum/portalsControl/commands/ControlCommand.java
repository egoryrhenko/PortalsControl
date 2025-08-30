package org.ferrum.portalsControl.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.ferrum.portalsControl.ConfigManager;
import org.ferrum.portalsControl.listeners.PortalListener;

import java.util.List;

public class ControlCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 2) {
            switch (strings[0].toLowerCase()) {
                case "world" -> {
                    switch (strings[1].toLowerCase()) {
                        case "off":
                            PortalListener.blockWorld = true;
                            ConfigManager.setValue("Block_teleport_to.world", true);
                            commandSender.sendMessage(ConfigManager.getMessage("ToWorldPortalOff"));
                            return true;
                        case "on":
                            PortalListener.blockWorld = false;
                            ConfigManager.setValue("Block_teleport_to.world", false);
                            commandSender.sendMessage(ConfigManager.getMessage("ToWorldPortalOn"));
                            return true;
                        default:
                            commandSender.sendMessage(command.getUsage());
                            return true;
                    }
                }
                case "nether" -> {
                    switch (strings[1].toLowerCase()) {
                        case "off":
                            PortalListener.blockNether = true;
                            ConfigManager.setValue("Block_teleport_to.nether", true);
                            commandSender.sendMessage(ConfigManager.getMessage("ToNetherPortalOff"));
                            return true;
                        case "on":
                            PortalListener.blockNether = false;
                            ConfigManager.setValue("Block_teleport_to.nether", false);
                            commandSender.sendMessage(ConfigManager.getMessage("ToNetherPortalOn"));
                            return true;
                        default:
                            commandSender.sendMessage(command.getUsage());
                            return true;
                    }
                }
                case "end" -> {
                    switch (strings[1].toLowerCase()) {
                        case "off":
                            PortalListener.blockEnd = true;
                            ConfigManager.setValue("Block_teleport_to.end", true);
                            commandSender.sendMessage(ConfigManager.getMessage("ToEndPortalOff"));
                            return true;
                        case "on":
                            PortalListener.blockEnd = false;
                            ConfigManager.setValue("Block_teleport_to.end", false);
                            commandSender.sendMessage(ConfigManager.getMessage("ToEndPortalOn"));
                            return true;
                        default:
                            commandSender.sendMessage(command.getUsage());
                            return true;
                    }
                }
                default -> {
                    commandSender.sendMessage(command.getUsage());
                    return true;
                }
            }
        } else if (strings[0].equals("reload") && commandSender.hasPermission("portalscontrol.reload")) {
            ConfigManager.loadConfig();
            commandSender.sendMessage("PortalsControl config reloaded");
        }
        commandSender.sendMessage(command.getUsage());
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
