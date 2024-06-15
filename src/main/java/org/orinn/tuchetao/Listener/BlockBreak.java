package org.orinn.tuchetao.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.orinn.tuchetao.Main;
import org.orinn.tuchetao.Utils;
import org.orinn.tuchetao.storage.DataManager;
import org.orinn.tuchetao.storage.DropsList;
import org.orinn.tuchetao.storage.playerData;

import java.util.*;

import static org.bukkit.GameMode.*;

public class BlockBreak implements Listener {

    public BlockBreak(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (player.getGameMode() == CREATIVE || player.getGameMode() == ADVENTURE) {
            return;
        }

        Block block = event.getBlock();
        playerData data = DataManager.PLAYER_DATA.get(player.getName());
        Map<Material, Integer> map = new HashMap<>();

        List<ItemStack> drops = new ArrayList<>(event.getBlock().getDrops(player.getInventory().getItemInMainHand()));
        if (block.getState() instanceof Container) {
            Container container = (Container) block.getState();
            for (ItemStack item : container.getInventory().getContents()) {
                if (item != null && item.getType() != Material.AIR) {
                    drops.add(item);
                }
            }
        }

        for (ItemStack item : drops) {
            if (DropsList.checkDrop(item.getType()) && Utils.isDefaultItem(item, item.getType())) {
                map.merge(item.getType(), item.getAmount(), Integer::sum);
            } else {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
            }
        }

        for (Map.Entry<Material, Integer> entry : map.entrySet()) {
            Material material = entry.getKey();
            int amount = entry.getValue();
            data.addAmount(material.name(), amount);
            player.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&c " + material.name() + " &f: &a+" + amount));
        }

        event.setDropItems(false);
    }
}
