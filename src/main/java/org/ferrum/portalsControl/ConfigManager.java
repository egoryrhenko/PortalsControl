package org.ferrum.portalsControl;


import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.ferrum.portalsControl.listeners.PortalListener;



import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private static File configFile;
    private static FileConfiguration config;
    private static FileConfiguration language;


    public static void loadConfig() {
        configFile = new File(PortalsControl.plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            PortalsControl.plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        String lang = config.getString("language", "en");

        File languageFile = new File(PortalsControl.plugin.getDataFolder(), "/language/" + lang + ".yml");
        if (!languageFile.exists()) {
            PortalsControl.plugin.saveResource("language/" + lang + ".yml", false);
        }
        language = YamlConfiguration.loadConfiguration(languageFile);


        PortalListener.blockWorld = getValue("Block_teleport_to.world");
        PortalListener.blockNether = getValue("Block_teleport_to.nether");
        PortalListener.blockEnd = getValue("Block_teleport_to.end");
    }

    public static String getMessage(String key) {
        return ChatColor.translateAlternateColorCodes('&', language.getString(key,"text."+key));
    }

    public static boolean getValue(String path) {
        return config.getBoolean(path);
    }

    public static void setValue(String path, Boolean value) {
        config.set(path, value);
        saveConfig();
    }

    public static void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            PortalsControl.plugin.getLogger().severe(e.getMessage());
        }
    }


}
