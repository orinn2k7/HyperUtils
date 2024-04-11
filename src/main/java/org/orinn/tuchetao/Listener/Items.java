package org.orinn.tuchetao.Listener;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Items {

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

}
