package org.orinn.tuchetao.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.orinn.tuchetao.Main;

import java.io.File;
import java.io.IOException;

public class Data {
    
    private static File file;
    private static FileConfiguration DataFile;

    // multi file
    public static void loadData() {
        file = new File(Main.getInstance().getDataFolder(), "data.yml");
        if (!file.exists()) {
            Main.getInstance().saveResource("data.yml", false);
        }
        DataFile = YamlConfiguration.loadConfiguration(file);
    }
    public static FileConfiguration getData() {
        return DataFile;
    }
    public static void saveData() {
        try {
            DataFile.save(file);
        } catch (IOException var1) {
            var1.printStackTrace();
        }
    }
    
}
