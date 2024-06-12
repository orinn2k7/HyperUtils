package org.orinn.tuchetao.Listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.orinn.tuchetao.Main;
import org.orinn.tuchetao.Utils;
import org.orinn.tuchetao.storage.BlocksList;
import org.orinn.tuchetao.storage.DataManager;
import org.orinn.tuchetao.storage.DataStorage;
import org.orinn.tuchetao.storage.DropsList;

public class playerJoin implements Listener {

    public playerJoin(Main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        DataStorage.loadData(player);
        DataStorage.saveData(player.getName());
        player.sendMessage(DropsList.dropsList.toArray(new String[0]));
        player.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&a Khởi tạo dữ liệu thành công..."));
    }

}
