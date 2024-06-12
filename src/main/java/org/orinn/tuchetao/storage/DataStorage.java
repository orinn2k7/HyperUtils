package org.orinn.tuchetao.storage;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataStorage {

    private static final Logger logger = Logger.getLogger(DataFile.class.getName());

    public static void loadData(Player player) {
        String playerName = player.getName();
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(DataFile.getFile(playerName));

        playerData data = DataManager.PLAYER_DATA.getOrDefault(playerName, new playerData());

        if (!yaml.contains("storage")) {
            DataFile.addDefaultData(DataFile.getFile(playerName));
            yaml = YamlConfiguration.loadConfiguration(DataFile.getFile(playerName));
        }

        for (String material : Objects.requireNonNull(yaml.getConfigurationSection("storage")).getKeys(false)) {
            data.setAmount(material, yaml.getInt("storage." + material));
        }
        DataManager.PLAYER_DATA.put(playerName, data);
    }

    public static void saveData(String playerName) {
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(DataFile.getFile(playerName));
        playerData data = DataManager.PLAYER_DATA.get(playerName);

        if (data == null) {
            return;
        };
        if (!yaml.contains("storage")) {
            DataFile.addDefaultData(DataFile.getFile(playerName));
            yaml = YamlConfiguration.loadConfiguration(DataFile.getFile(playerName));
        }

        for (String material : data.getStorage().keySet()) {
            yaml.set("storage." + material, data.getStorage().get(material));
        }

        try {
            yaml.save(DataFile.getFile(playerName));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to save data to file: " + DataFile.getFile(playerName).getName(), e);
        }
    }


}
