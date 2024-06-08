package org.orinn.tuchetao;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Actions {

    public static void addItem(Player player, ItemStack itemStack, int amount) {
        if (player == null || itemStack == null) {
            return;
        }
        itemStack.setAmount(amount);
        player.getInventory().addItem(itemStack);
    }

    public static void removeItem(Player player, ItemStack itemStack, int amount) {
        if (player == null || itemStack == null) {
            return;
        }
        itemStack.setAmount(amount);
        player.getInventory().removeItem(itemStack);
    }


}
