package org.orinn.tuchetao.Listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Items {

    // Vanilla
    public static int getAmount(Player player, ItemStack itemStack) {
        int Amount = 0;
        if (itemStack != null) {
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null && item.isSimilar(itemStack)) {
                    Amount += item.getAmount();
                }
            }
            return Amount;
        }
        return 0;
    }


    public static boolean isInventoryEmpty(Player player) {
        Inventory inv = player.getInventory();
        for (int i = 0; i < 36; i++) {
            if (inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR) {
                return false;
            }
        }
        return true;
    }
}

