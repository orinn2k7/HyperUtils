package org.orinn.tuchetao.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.orinn.tuchetao.Main;

import java.io.File;

public class Settings {

    private static File file;
    private static FileConfiguration settings;


    public static void loadSettings() {
        file = new File(Main.getInstance().getDataFolder(), "settings.yml");
        if (!file.exists()) {
            Main.getInstance().saveResource("settings.yml", false);
        }
        settings = YamlConfiguration.loadConfiguration(file);
    }
    public static FileConfiguration getSettings() {
        return settings;
    }



}
