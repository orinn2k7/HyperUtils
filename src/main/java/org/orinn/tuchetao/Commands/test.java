package org.orinn.tuchetao.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.orinn.tuchetao.Listener.Items;
import org.orinn.tuchetao.Main;

public class test implements CommandExecutor {

    private Main plugin;

    public test(org.orinn.tuchetao.Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("test").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        Player player = (Player) commandSender;
        if (Items.isInventoryEmpty(player)) {
            player.sendMessage("Y");

        } else {
            player.sendMessage("N");
        }

        return false;
    }
}
