package org.orinn.tuchetao.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.orinn.tuchetao.Main;

import java.io.File;
import java.io.IOException;
public class Item {
    private static File file;
    private static FileConfiguration ItemFile;

    public static void loadData() {
        file = new File(Main.getInstance().getDataFolder(), "items.yml");
        if (!file.exists()) {
            Main.getInstance().saveResource("items.yml", false);
        }
        ItemFile = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getData() {
        return ItemFile;
    }
    public static void saveData() {
        try {
            ItemFile.save(file);
        } catch (IOException var1) {
            var1.printStackTrace();
        }
    }
}
