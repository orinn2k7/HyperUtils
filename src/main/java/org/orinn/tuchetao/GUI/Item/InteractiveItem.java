package org.orinn.tuchetao.GUI.Item;

import de.tr7zw.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.orinn.tuchetao.GUI.GUIManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InteractiveItem extends ItemStack {

    private UUID uuid;
    private final String key;
    private int slot;

    public InteractiveItem(Player player, String key, Material material, Component displayName, int slot, Component... lore) {
        super(material);
        ItemMeta meta = this.getItemMeta();
        if (meta != null) {
            meta.displayName(displayName);
            meta.lore(Arrays.asList(lore));
            this.setItemMeta(meta);
        }
        this.key = key;
        this.slot = slot;
        createMapping(player);
    }

    public void createMapping(Player player) {
        UUID uuid = UUID.randomUUID();
        this.uuid = uuid;

        Map<UUID, InteractiveItem> playerItemMap = GUIManager.getItemsMapper().computeIfAbsent(player, k -> new HashMap<>());

        playerItemMap.put(uuid, this);

        NBTItem nbtItem = new NBTItem(this);
        nbtItem.setUUID("storage:id", uuid);
        this.setItemMeta(nbtItem.getItem().getItemMeta());
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getKey() {
        return this.key;
    }

    public String getMaterial() {
        return this.getType().name();
    }

    public int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

}
