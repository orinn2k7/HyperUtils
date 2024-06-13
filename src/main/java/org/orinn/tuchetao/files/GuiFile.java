package org.orinn.tuchetao.files;

import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.configuration.file.FileConfiguration;

public class GuiFile {

    private static final SimpleConfigurationManager XCONFIG = SimpleConfigurationManager.get();

    public static FileConfiguration get() {
        return XCONFIG.get("gui.yml");
    }

    public static void load() {
        XCONFIG.build("", false, "gui.yml");
    }

    public static void save() {
        XCONFIG.save("gui.yml");
    }

    public static void reload() {
        XCONFIG.reload("gui.yml");
    }
}
