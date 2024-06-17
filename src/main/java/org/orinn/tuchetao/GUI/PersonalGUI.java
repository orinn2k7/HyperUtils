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

import java.util.ArrayList;
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

        createDecorateItems(inventory);
        createInteractiveItems(inventory);
        return inventory;
    }

    private void createDecorateItems(Inventory inventory) {
        Component displayName = Utils.TranslateColorCodes(CONFIG.getString("decorate_items.display_name"));
        Material material = Material.valueOf(CONFIG.getString("decorate_items.material"));
        Component[] loresList = parseLores(CONFIG.getStringList("decorate_items.lore"));
        List<Integer> slotsList = parseSlots(CONFIG.getStringList("decorate_items.slots"));
    }

    private void createInteractiveItems(Inventory inventory) {
        for (String key : Objects.requireNonNull(CONFIG.getConfigurationSection("storage_items")).getKeys(false)) {
            Component displayName = Utils.TranslateColorCodes(Objects.requireNonNull(CONFIG.getString("storage_items." + key + ".display_name")));
            Material material = Material.valueOf(CONFIG.getString("storage_items." + key + ".material"));
            int slot = CONFIG.getInt("storage_items." + key + ".slot");
            List<String> loreStrings = CONFIG.getStringList("storage_items." + key + ".lore");
            Component[] lores = parseLores(loreStrings);
            InteractiveItem interactiveItem = new InteractiveItem(player, key, material, displayName, slot, lores);
            inventory.setItem(interactiveItem.getSlot(), interactiveItem);
        }
    }


    private Component[] parseLores(List<String> loresList) {
        Component[] lores = new Component[loresList.size()];
        for (int i = 0; i < loresList.size(); i++) {
            lores[i] = Utils.TranslateColorCodes(PlaceholderAPI.setPlaceholders(player, loresList.get(i)));
        }
        return lores;
    }

    private List<Integer> parseSlots(List<String> slotsList) {
        List<Integer> slots = new ArrayList<>();
        for (String slot : slotsList) {
            if (slot.contains("-")) {
                String[] range = slot.split("-");
                int start = Integer.parseInt(range[0]);
                int end = Integer.parseInt(range[1]);
                for (int i = start; i <= end; i++) {
                    if (slots.contains(i)) continue;
                    slots.add(i);
                }
            } else {
                if (slots.contains(Integer.parseInt(slot))) continue;
                slots.add(Integer.parseInt(slot));
            }
        }
        return slots;
    }

}
