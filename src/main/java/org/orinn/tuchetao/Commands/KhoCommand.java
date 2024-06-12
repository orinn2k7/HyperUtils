package org.orinn.tuchetao.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.orinn.tuchetao.Main;
import org.orinn.tuchetao.Utils;
import org.orinn.tuchetao.storage.DataManager;
import org.orinn.tuchetao.storage.DataStorage;
import org.orinn.tuchetao.storage.playerData;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhoCommand implements CommandExecutor {

    public KhoCommand(Main plugin) {
        Objects.requireNonNull(plugin.getCommand("kho")).setExecutor(this);
    }

    private static final Logger logger = Logger.getLogger(KhoCommand.class.getName());

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            logger.log(Level.SEVERE, "Chỉ có người chơi mới thực hiện được lệnh này");
            return false;
        }

        Player p = (Player) sender;
        if (args.length == 0) {
            sender.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&a GUI đang update sau hihi =)))"));
            return false;
        }

        if (args[0].equalsIgnoreCase("check")) {
            playerData data = DataManager.PLAYER_DATA.get(p.getName());
            if (args.length == 1) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &fĐang kiểm tra..."));
                for (String item : data.getStorage().keySet()) {
                    p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &fSố lượng vật phầm &c" + item + "&8 : &a" + data.getStorage().get(item)));
                }
            } else if (args.length == 2) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &fĐang kiểm tra..."));
                if (!data.getStorage().containsKey(args[1])) {
                    p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &c" + args[1].toUpperCase() + " &fkhông tồn tại"));
                } else {
                    p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &fSố lướng vật phẩm &c" + args[1] + "&8 : &a" + data.getStorage().get(args[1])));
                }
            } else {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &cHãy dùng lệnh"));
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            playerData data = DataManager.PLAYER_DATA.get(p.getName());
            for (String item : data.getStorage().keySet()) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &fSố lướng vật phải màu &c" + item + "&8 : &a" + data.getStorage().get(item)));
            }
        }

        if (args[0].equalsIgnoreCase("save")) {
            try {
                DataStorage.saveData(p.getName());
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &a Lưu dữ liệu người chơi thành công!"));
            } catch (Exception e) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &cCó lỗi khi lưu dữ liệu. Vui lòng liên hệ admin"));
                logger.log(Level.SEVERE, "Có lỗi khi lưu dữ liệu:", e);
            }
        }

        if (args[0].equalsIgnoreCase("take")) {
            if (args.length != 3) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &cHãy dùng lệnh: /kho take <item> <amount>"));
                return true;
            }
            playerData data = DataManager.PLAYER_DATA.get(p.getName());
            if (!data.getStorage().containsKey(args[1])) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &c" + args[1].toUpperCase() + " &không tồn tại"));
                return true;
            }
            int amount;
            try {
                amount = Integer.parseInt(args[2]);
            } catch (Exception ignore) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&f Vui lòng nhập số nguyên"));
                return true;
            }
            if (amount > data.getAmount(args[1])) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&f Số lượng nhập vào lớn hơn số lượng trong kho"));
                return true;
            }
            try {
                ItemStack item = new ItemStack(Material.valueOf(args[1]), amount);
                data.subAmount(args[1], amount);
                p.getInventory().addItem(item);
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&f Đánh ghi số lướng vết phải màu &c" + args[1] + "&8 : &a" + data.getStorage().get(args[1])));
            } catch (Exception e) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&f Có lỗi khi trả vấn"));
                logger.log(Level.SEVERE, "Có lỗi khi trả vấn:", e);
            }
            return true;
        }

        return true;
    }
}