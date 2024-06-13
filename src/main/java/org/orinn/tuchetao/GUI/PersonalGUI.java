package org.orinn.tuchetao.GUI;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.orinn.tuchetao.Main;
import org.orinn.tuchetao.Utils;
import org.orinn.tuchetao.files.GuiFile;

import java.awt.*;
import java.util.Objects;

public class PersonalGUI {

    private final FileConfiguration CONFIG = GuiFile.get();
    private final Player player;

    public PersonalGUI(Player player) {
        this.player = player;
    }

    public Inventory getGUI() {
        Component title = Utils.TranslateColorCodes(PlaceholderAPI.setPlaceholders(player, Objects.requireNonNull(CONFIG.getString("title"))));
        return Bukkit.createInventory(player, 9 * CONFIG.getInt("size"), title);
    }


}
