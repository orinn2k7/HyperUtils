package org.orinn.tuchetao.storage;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.orinn.tuchetao.files.FileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlocksList {
    public static List<String> blocksList = new ArrayList<>();

    public static boolean checkBlock(Material block) {
        return blocksList.contains(block.name());
    }

    public static void loadBlocks() {
        blocksList.clear();
        FileConfiguration config = FileManager.getConfig();
        if (config == null || config.getConfigurationSection("allow_blocks") == null) {
            return;
        }
        blocksList.addAll(Objects.requireNonNull(config.getConfigurationSection("allow_blocks")).getKeys(false));
    }
}