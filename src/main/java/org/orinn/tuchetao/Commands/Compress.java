package org.orinn.tuchetao.Commands;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.orinn.tuchetao.files.*;
import org.orinn.tuchetao.Listener.Items;
import org.orinn.tuchetao.Main;
import org.orinn.tuchetao.Other.ItemObj;
import org.orinn.tuchetao.Utils;
import org.orinn.tuchetao.Actions;

import java.util.Objects;
import java.util.Set;

public class Compress implements CommandExecutor {

    public Compress(Main plugin) {
        Objects.requireNonNull(plugin.getCommand("compress2")).setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        // Kiểm tra người chơi
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.TranslateColorCodes("&cKhông phải ngườii chơi"));
            return false;
        }

        // Kiểm tra arguments
        if (args.length == 0) {
            sender.sendMessage(Utils.TranslateColorCodes("&cThiếu Args"));
            return true;
        }

        // Khởi tạo biến
        Player p = (Player) sender;
        String itemId = args[0];
        FileConfiguration cfg = Settings.getSettings();
        FileConfiguration itemsCfg = Item.getData();

        // Kiểm tra item hợp lệ
            Set<String> compressList = Objects.requireNonNull(itemsCfg.getConfigurationSection("items")).getKeys(false);
        if (!compressList.contains(itemId)) {
            sender.sendMessage(Utils.TranslateColorCodes("&cItem không hợp lệ"));
            return false;
        }

        // Kiểm tra quyền
        String perm = cfg.getString("compressor." + itemId + ".permission");
        assert perm != null;
        if (!p.hasPermission(perm)) {
            sender.sendMessage("Không có quyền");
            return false;
        }

        // Lấy thông tin item
        ItemObj reqItem = getItemObj(itemsCfg, "items" + itemId + ".requirement"); // Item yêu cầu
        ItemObj prodItem = getItemObj(itemsCfg, "items" + itemId + ".production"); // Item sản phẩm
        if (reqItem == null || prodItem == null) {
            p.sendMessage(Utils.TranslateColorCodes("&cLỗi"));
            return false;
        }

        // Tạo ItemStack từ ItemObj
        ItemStack reqStack = createStack(reqItem);
        ItemStack prodStack = createStack(prodItem);
        if (reqStack == null || prodStack == null) {
            p.sendMessage(Utils.TranslateColorCodes("&cLỗi khi tạo vật phẩm."));
            return false;
        }

        // Kiểm tra số lượng item
        int amount = Items.getAmount(p, reqStack); // Số lượng item yêu cầu người chơi có
        if (amount < reqItem.getAmount()) {
            p.sendMessage(Utils.TranslateColorCodes("&cKhông đủ vật phẩm"));
            return false;
        }

        // Tính số lượng item sản phẩm
        int resultAmt = (int) Math.ceil(amount / (double) reqItem.getAmount());
        Actions.removeItem(p, reqStack, resultAmt * reqItem.getAmount());
        Actions.addItem(p, prodStack, resultAmt);

        return true;
    }

    // Hàm lấy thông tin item từ config
    private ItemObj getItemObj(FileConfiguration cfg, String path) {
        String[] parts = Objects.requireNonNull(cfg.getString(path)).split(" ");
        if (parts.length < 2) {
            return null;
        }
        if (parts[0].equalsIgnoreCase("VANNILA")) {
            return new ItemObj(parts[0], null, parts[1], Integer.parseInt(parts[2]));
        } else if (parts[0].equalsIgnoreCase("MMOITEMS")){
            return new ItemObj(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
        } else {
            return null;
        }
    }

    // Hàm tạo ItemStack từ ItemObj
    private ItemStack createStack(ItemObj item) {
        if (item.getType().equalsIgnoreCase("VANNILA")) {
            return new ItemStack(Material.valueOf(item.getId()));
        } else if (item.getType().equalsIgnoreCase("MMOITEMS")) {
            MMOItem mmoItem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getTypes().get(item.getType()), item.getId());
            if (mmoItem == null) {
                return null;
            }
            return mmoItem.newBuilder().build();
        }
        return null;
    }
}