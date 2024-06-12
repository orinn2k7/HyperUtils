package org.orinn.tuchetao.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.orinn.tuchetao.Listener.Items;
import org.orinn.tuchetao.Main;
import org.orinn.tuchetao.storage.DataFile;
import org.orinn.tuchetao.storage.DataManager;
import org.orinn.tuchetao.storage.DropsList;

import java.io.File;
import java.util.Objects;

public class test implements CommandExecutor {

    public test(Main plugin) {
        Objects.requireNonNull(plugin.getCommand("test")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = (Player) commandSender;
        DataManager.saveAllData();
        File data = DataFile.getFile(player.getName());
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(data);
        for (String mat : DropsList.dropsList) {
            if (!yaml.contains("storage." + mat)) {
                yaml.set("storage." + mat, 0);
            }
            player.sendMessage(Objects.requireNonNull(yaml.getString("storage." + mat)));
        }

        return false;
    }
}
