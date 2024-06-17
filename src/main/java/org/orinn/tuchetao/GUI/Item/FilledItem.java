package org.orinn.tuchetao.GUI.Item;


import de.tr7zw.nbtapi.NBTItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.orinn.tuchetao.GUI.GUIManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class FilledItem extends ItemStack {

    private int slot;

    public FilledItem(Material material, Component displayName, int slot, Component... lore) {
        super(material);
        ItemMeta meta = this.getItemMeta();
        if (meta != null) {
            meta.displayName(displayName);
            meta.lore(Arrays.asList(lore));
            this.setItemMeta(meta);
        }
        this.slot = slot;
    }

    public void createMapping(Player player) {
        UUID uuid = UUID.randomUUID();
        List<FilledItem> decorateList = GUIManager.getDecorateMapper().computeIfAbsent(player, k -> new ArrayList<>());
        decorateList.add(this);

        NBTItem nbtItem = new NBTItem(this);
        nbtItem.setUUID("decorate:id", uuid);
        this.setItemMeta(nbtItem.getItem().getItemMeta());
    }

}
