package org.orinn.tuchetao.GUI;

import de.tr7zw.nbtapi.NBTItem;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.orinn.tuchetao.GUI.Item.InteractiveItem;
import org.orinn.tuchetao.Utils;
import org.orinn.tuchetao.files.GuiFile;
import org.orinn.tuchetao.storage.DataManager;
import org.orinn.tuchetao.storage.playerData;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ItemListener implements Listener {

    private final FileConfiguration CONFIG = GuiFile.get();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        boolean isGUI = e.getClickedInventory() != null && e.getClickedInventory().getHolder() != null && e.getClickedInventory().getHolder() instanceof GUI;
        boolean isInteractiveItem = e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR && new NBTItem(e.getCurrentItem()).hasTag("storage:id");

        if (isGUI || isInteractiveItem) {
            e.setCancelled(true);
            p.updateInventory();

            UUID uuid = new NBTItem(Objects.requireNonNull(e.getCurrentItem())).getUUID("storage:id");

            if (GUIManager.getItemsMapper().get(p).containsKey(uuid)) {
                playerData data = DataManager.PLAYER_DATA.get(p.getName());
                InteractiveItem item = GUIManager.getItemsMapper().get(p).get(uuid);
                String mat = item.getMaterial();
                
                if (e.getClick().isLeftClick()) {
                    ItemStack stack = new ItemStack(Material.valueOf(mat), 1);
                    data.subAmount(mat, 1);
                    p.getInventory().addItem(stack);
                    refresh(p, e.getClickedInventory(), item);
                } else {
                    p.closeInventory();
                }
            }

        }
    }

//    private void refreshGUI(Player player, Inventory inventory) {
//        for (String key : Objects.requireNonNull(CONFIG.getConfigurationSection("storage_items")).getKeys(false)) {
//            Component displayName = Utils.TranslateColorCodes(Objects.requireNonNull(CONFIG.getString("storage_items." + key + ".display_name")));
//            Material material = Material.valueOf(CONFIG.getString("storage_items." + key + ".material"));
//            int slot = CONFIG.getInt("storage_items." + key + ".slot");
//            List<String> loreStrings = CONFIG.getStringList("storage_items." + key + ".lore");
//            Component[] lores = new Component[loreStrings.size()];
//            for (int i = 0; i < loreStrings.size(); i++) {
//                lores[i] = Utils.TranslateColorCodes(PlaceholderAPI.setPlaceholders(player, loreStrings.get(i)));
//            }
//            InteractiveItem interactiveItem = new InteractiveItem(key, material, displayName, slot, lores);
//            inventory.setItem(interactiveItem.getSlot(), interactiveItem);
//        }
//    }

    private void refresh(Player player, Inventory inventory, InteractiveItem item) {
        UUID uuid = item.getUUID();
        GUIManager.getItemsMapper().get(player).remove(uuid);

        String key = item.getKey();
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
}
