package org.orinn.tuchetao.Commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.orinn.tuchetao.GUI.PersonalGUI;
import org.orinn.tuchetao.Main;
import org.orinn.tuchetao.Utils;
import org.orinn.tuchetao.files.FileManager;
import org.orinn.tuchetao.storage.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class KhoCommand implements CommandExecutor, TabCompleter {

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
            p.openInventory(new PersonalGUI(p).getGUI());
            return true;
        }

        if (args[0].equalsIgnoreCase("check")) {
            playerData data = DataManager.PLAYER_DATA.get(p.getName());
            if (args.length == 1) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &fĐang kiểm tra..."));
                if (data.getStorage().keySet().isEmpty()) {
                    p.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&c Không tìm thấy dữ liệu..."));
                    return false;
                }
                for (String item : data.getStorage().keySet()) {
                    p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &fSố lượng vật phầm &c" + item + "&8 : &a" + data.getStorage().get(item)));
                }
                return true;
            } else if (args.length == 2) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &fĐang kiểm tra..."));
                if (!data.getStorage().containsKey(args[1])) {
                    p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &c" + args[1].toUpperCase() + " &fkhông tồn tại"));
                } else {
                    p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &fSố lượng vật phẩm &c" + args[1] + "&8 : &a" + data.getStorage().get(args[1])));
                }
                return true;
            } else {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &cHãy dùng lệnh: /kho check [item]"));
            }
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            playerData data = DataManager.PLAYER_DATA.get(p.getName());
            p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &fDanh sách vật phẩm:"));
            for (String item : data.getStorage().keySet()) {
                p.sendMessage(Utils.TranslateColorCodes("&c" + item.toUpperCase()));
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
            if (args.length < 3) {
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
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&f Vui lòng nhập một số nguyên"));
                return true;
            }
            if (amount > data.getAmount(args[1])) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&f Số lượng nhập vào lớn hơn số lượng trong kho"));
                return true;
            }
            try {
                ItemStack item = new ItemStack(Material.valueOf(args[1]), amount);
                data.subAmount(args[1].toUpperCase(), amount);
                p.getInventory().addItem(item);
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&f Rút thành công &c" + amount + " " + args[1].toUpperCase() ));
            } catch (Exception e) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&f Có lỗi xảy ra. Vui lòng liên hệ admin"));
                logger.log(Level.SEVERE, "Có lỗi xảy ra:", e);
            }
            return true;
        }


        if (args[0].equalsIgnoreCase("admin")) {
            if (!p.hasPermission("kho.admin")) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&c Bạn không có quyền dùng lệnh này"));
                return false;
            }
            if (args.length == 1) {
                p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &c Thiếu lệnh"));
                return false;
            }

            if (args[1].equalsIgnoreCase("save")) {
                try {
                    DataManager.saveAllData();
                    p.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&a Lưu dữ liệu thành công"));
                } catch (Exception e) {
                    p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &cCó lỗi khi lưu dữ liệu. Vui lòng liên hệ admin"));
                    logger.log(Level.SEVERE, "Có lỗi khi lưu dữ liệu:", e);
                }
            }

            if (args[1].equalsIgnoreCase("reload")) {
                try {
                    List<String> oldList = DropsList.dropsList;
                    BlocksList.loadBlocks();
                    DropsList.loadDrops();
                    if (oldList != DropsList.dropsList) {
                        for (Player player : Main.getInstance().getServer().getOnlinePlayers()) {
                            DataStorage.loadData(player);
                            DataStorage.saveData(player.getName());
                            player.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&a Khởi tạo dữ liệu thành công..."));
                        }
                    }
                    p.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&a Tải lại config thành oông!"));
                } catch (Exception e) {
                    p.sendMessage(Utils.TranslateColorCodes("&eStorage &8| &cCó lỗi khi lưu dữ liệu. Vui lòng kiểm tra console!"));
                    logger.log(Level.SEVERE, "Có lỗi khi tải dữ liệu:", e);
                }
            }
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("check", "list", "save", "take", "admin");
        }

        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("check")) {
                playerData data = DataManager.PLAYER_DATA.get(commandSender.getName());
                if (data != null) {
                    return new ArrayList<>(data.getStorage().keySet());
                }
            } else if (args[0].equalsIgnoreCase("take")) {
                playerData data = DataManager.PLAYER_DATA.get(commandSender.getName());
                if (data != null) {
                    return new ArrayList<>(data.getStorage().keySet());
                }
            } else if (args[0].equalsIgnoreCase("admin")) {
                return Arrays.asList("save", "reload");
            }
        }
        return new ArrayList<>();
    }
}