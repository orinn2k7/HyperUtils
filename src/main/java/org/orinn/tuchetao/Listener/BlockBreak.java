package org.orinn.tuchetao.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.orinn.tuchetao.Main;
import org.orinn.tuchetao.Utils;
import org.orinn.tuchetao.storage.*;

import java.util.List;

public class BlockBreak implements Listener {

    public BlockBreak(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material block = event.getBlock().getType();
        playerData data = DataManager.PLAYER_DATA.get(player.getName());

        if (BlocksList.checkBlock(block)) {
            event.setDropItems(false);
            List<ItemStack> drops = (List<ItemStack>) event.getBlock().getDrops(player.getInventory().getItemInMainHand());

            for (ItemStack item : drops) {
                if (DropsList.checkDrop(item.getType())) {
                    data.addAmount(item.getType().name(), item.getAmount());
                    player.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&c " + item.getType().name() + " &f: &a+" + item.getAmount()));
                }
            }
        }
    }
}