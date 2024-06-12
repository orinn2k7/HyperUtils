package org.orinn.tuchetao.storage;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.orinn.tuchetao.files.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DropsList {

    public static List<String> dropsList = new ArrayList<>();

    public static boolean checkDrop(Material item) {
        return dropsList.contains(item.name());
    }

    public static void loadDrops() {
        dropsList.clear();
        FileConfiguration config = FileManager.getConfig();
        if (config == null || config.getConfigurationSection("allow_blocks") == null) {
            return;
        }
        for (String key : Objects.requireNonNull(config.getConfigurationSection("allow_blocks")).getKeys(false)) {
            String value = config.getString("allow_blocks." + key);
            if (value == null || dropsList.contains(value)) {
                continue;
            }
            dropsList.add(value);
        }
    }


}
