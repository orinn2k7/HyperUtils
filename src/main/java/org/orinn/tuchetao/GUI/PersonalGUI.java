package org.orinn.tuchetao.GUI;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.orinn.tuchetao.GUI.Item.InteractiveItem;
import org.orinn.tuchetao.Utils;
import org.orinn.tuchetao.files.GuiFile;

import java.util.List;
import java.util.Objects;

public class PersonalGUI implements GUI {

    private final FileConfiguration CONFIG = GuiFile.get();
    private final Player player;

    public PersonalGUI(Player player) {
        this.player = player;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        Component title = Utils.TranslateColorCodes(PlaceholderAPI.setPlaceholders(player, Objects.requireNonNull(CONFIG.getString("title"))));
        Inventory inventory = Bukkit.createInventory(player, 9 * CONFIG.getInt("size"), title);
        for (String key : Objects.requireNonNull(CONFIG.getConfigurationSection("storage_items")).getKeys(false)) {
            Component displayName = Utils.TranslateColorCodes(Objects.requireNonNull(CONFIG.getString("storage_items." + key + ".display_name")));
            Material material = Material.valueOf(CONFIG.getString("storage_items." + key + ".material"));
            int slot = CONFIG.getInt("storage_items." + key + ".slot");
            List<String> loreStrings = CONFIG.getStringList("storage_items." + key + ".lore");
            Component[] lores = new Component[loreStrings.size()];
            for (int i = 0; i < loreStrings.size(); i++) {
                lores[i] = Utils.TranslateColorCodes(PlaceholderAPI.setPlaceholders(player, loreStrings.get(i)));
            }
            InteractiveItem interactiveItem = new InteractiveItem(player, key, material, displayName, slot, lores);
            inventory.setItem(interactiveItem.getSlot(), interactiveItem);
        }
        return inventory;
    }

}
