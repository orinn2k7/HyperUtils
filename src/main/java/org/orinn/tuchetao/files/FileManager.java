package org.orinn.tuchetao.files;


import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.configuration.file.FileConfiguration;

public class FileManager {

    public static SimpleConfigurationManager getFileSetting() {
        return SimpleConfigurationManager.get();
    }
    public static FileConfiguration getConfig() {
        return getFileSetting().get("storage.yml");
    }

    public static void loadFiles() {
        getFileSetting().build("",false, "storage.yml");
    }
}
