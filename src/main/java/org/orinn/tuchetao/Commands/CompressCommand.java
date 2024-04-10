package org.orinn.tuchetao.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.orinn.tuchetao.Listener.CheTao;
import org.orinn.tuchetao.Main;
import org.orinn.tuchetao.Utils;

public class CompressCommand implements CommandExecutor {

    private Main plugin;

    public CompressCommand(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("compress").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.TranslateColorCodes("&cKhông phải ngươời chơi"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "Thiếu Args"));
            return true;
        }

        Player player = (Player) sender;
        String NhapKhoangSan = args[0];
        CheTao.doCompress(player, NhapKhoangSan);
        return true;
    }
}
