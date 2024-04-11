package org.orinn.tuchetao.Listener;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Items {

    // Vanilla
    public static int getAmount(Player player, String id) {
        int Amount = 0;
        Material material = Material.getMaterial(id.toUpperCase());
        if (material == null) {
            return 0;
        }
        ItemStack itemStack = new ItemStack(material);
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null  && item.isSimilar(itemStack)) {
                Amount += item.getAmount();
            }
        }
        return Amount;
    }

    // Mmoitems
    public static class getMMOItems {
        public static int getAmount(Player player, String type, String id) {
            int Amount = 0;
            MMOItem mmoItem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getTypes().get(type), id);
            if (mmoItem == null) {
                return 0;
            }
            ItemStack itemStack = mmoItem.newBuilder().build();
            for (ItemStack item : player.getInventory().getContents()) {
                if (item != null  && item.isSimilar(itemStack)) {
                    Amount += item.getAmount();
                }
            }
            return Amount;
        }
    }

    // ItemsAdder Lười quá
    public static class getItemsAdder {
        public static int getAmount(Player player, String id) {
            return 0;
        }
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

//    public static void giveItems(Player player, ItemStack itemStack, int amount) {
//        if (player.getInventory().addItem(itemStack).isEmpty()) {
//            // Nếu không có vấn đề gì xảy ra, thoát phương thức
//            return;
//        }
//    }


}
