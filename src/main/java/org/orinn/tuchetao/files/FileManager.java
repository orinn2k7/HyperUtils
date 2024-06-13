package org.orinn.tuchetao.files;


import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.configuration.file.FileConfiguration;

public class FileManager {

    public static SimpleConfigurationManager getFileSetting() {
        return SimpleConfigurationManager.get();
    }
    
    private static final SimpleConfigurationManager XCONFIG = SimpleConfigurationManager.get();
    
    public static FileConfiguration getConfig() {
        return XCONFIG.get("storage.yml");
    }

    public static void loadFiles() {
        XCONFIG.build("",false, "storage.yml");
    }

    public static void reloadFiles() {
        XCONFIG.reload("storage.yml");
    }
}
