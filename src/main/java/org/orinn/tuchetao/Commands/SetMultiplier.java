package org.orinn.tuchetao.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.orinn.tuchetao.files.Data;
import org.orinn.tuchetao.files.Settings;
import org.orinn.tuchetao.Main;
import org.orinn.tuchetao.Utils;

import java.util.ArrayList;
import java.util.List;

public class SetMultiplier implements CommandExecutor, TabCompleter {

    public SetMultiplier(Main plugin) {
        plugin.getCommand("multiplier").setExecutor(this);
    }

    public void setup(String name) {
        FileConfiguration data = Data.getData();
        FileConfiguration settings = Settings.getSettings();
        data.set(name + ".multiplier", settings.getInt("default-multiplier"));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String string, @NotNull String[] args) {

        FileConfiguration data = Data.getData();
        FileConfiguration settings = Settings.getSettings();

        if (args.length == 0) {
            sender.sendMessage("Thiếu cú pháp");
            return true;
        }

        Player player = (Player) Bukkit.getPlayer(args[1]);
        double multiplier = data.getDouble(player.getName() + ".multiplier");

        if (args[0].equals("set")) {
            if (args.length < 3) {
                sender.sendMessage("Thiếu cú pháp: /multi set <player> <amount>");
                return true;
            }
            double input = Double.parseDouble(args[2]);
            data.set(player.getName() + ".multiplier", input);
            Data.saveData();

        } else if (args[0].equals("check")) {
            if (args.length < 2) {
                sender.sendMessage("Thiếu cú pháp: /multi check <player>");
                return true;
            }
            sender.sendMessage("Checked: " + data.getDouble("multiplier." + sender.getName()));

        } else if (args[0].equals("add")) {
            if (args.length < 3) {
                sender.sendMessage("Thiếu cú pháp: /multi add <player> <amount>");
                return true;
            }
            double input = Double.parseDouble(args[2]);
            if (input < 0) {
                sender.sendMessage(Utils.TranslateColorCodes("&cVui lòng nhập số lớn hơn 0"));
                return true;
            }
            double KetQua = multiplier + input;
            data.set(player.getName() + ".multiplier", KetQua);
            Data.saveData();

        } else if (args[0].equals("subtract")) {
            if (args.length < 3) {
                sender.sendMessage("Thiếu cú pháp: /multi subtract <player> <amount>");
                return true;
            }
            double input = Double.parseDouble(args[2]);
            if (input < 0) {
                sender.sendMessage(Utils.TranslateColorCodes("&cVui lòng nhập số lớn hơn 0"));
                return true;
            }
            double KetQua = multiplier - input;
            if (multiplier < 0) {
                data.set(player.getName() + ".multiplier", 0.0);
            } else {
                data.set(player.getName() + ".multiplier", KetQua);
            }
            Data.saveData();

        } else {
            sender.sendMessage(Utils.TranslateColorCodes("&cKhông tìm thấy lệnh"));
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("set");
            list.add("add");
            list.add("subtract");
            list.add("check");
            return list;
        }
        return null;
    }
}