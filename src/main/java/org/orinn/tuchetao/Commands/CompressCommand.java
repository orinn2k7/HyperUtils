package org.orinn.tuchetao.Commands;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.orinn.tuchetao.FileManager.Data;
import org.orinn.tuchetao.FileManager.Settings;
import org.orinn.tuchetao.Listener.Items;
import org.orinn.tuchetao.Main;
import org.orinn.tuchetao.Utils;

import java.util.Objects;
import java.util.Set;

public class CompressCommand implements CommandExecutor {

    public CompressCommand(Main plugin) {
        Objects.requireNonNull(plugin.getCommand("compress")).setExecutor(this);
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
        String KhoangSan = args[0];
        Inventory inv = player.getInventory();
        FileConfiguration settings = Settings.getSettings();
        FileConfiguration data = Data.getData();

        Set<String> CompressList = Objects.requireNonNull(settings.getConfigurationSection("compressor")).getKeys(false);

        if (CompressList.contains(KhoangSan)) {

            String permission = settings.getString("compressor." + KhoangSan + ".permission");

            if(!player.hasPermission(permission)) {
                player.sendMessage("Không có quyền");
                return false;
            }

            // Lấy thông tin về item
            String RequireItemType = settings.getString("compressor." + KhoangSan + ".requirement.item-type");
            String NewItemType = settings.getString("compressor." + KhoangSan + ".new-item.item-type");
            String RequireId = settings.getString("compressor." + KhoangSan + ".requirement.id");
            String NewId = settings.getString("compressor." + KhoangSan + ".new-item.id");
            int RequireAmount = settings.getInt("compressor." + KhoangSan + ".requirement.amount");
            int NewAmount = settings.getInt("compressor." + KhoangSan + ".new-item.amount");
            double Multiplier = data.getDouble(player.getName() + ".multiplier");
            ItemStack RequireStack = null;
            ItemStack NewStack = null;

            // Build Stack item đầu ra
            if (NewItemType.equalsIgnoreCase("VANILLA")) {
                NewStack = new ItemStack(Material.valueOf(NewId));
            } else if (NewItemType.equalsIgnoreCase("MMOITEMS")) {
                String NewType = settings.getString("compressor." + KhoangSan + ".new-item.type");
                MMOItem mmoItem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getTypes().get(NewType), NewId);
                if (mmoItem == null) {
                    return false;
                }
                NewStack = mmoItem.newBuilder().build();
            } else {
                return false;
            }

            // Build stack vào && Lấy amount item trong inv
            int Amount = 0;
            if (RequireItemType.equalsIgnoreCase("vanilla")) {
                RequireStack = new ItemStack(Material.valueOf(RequireId));
                Amount = Items.getAmount(player, RequireStack);
            } else if (RequireItemType.equalsIgnoreCase("MMOITEMS")) {
                String RequireType = settings.getString("compressor." + KhoangSan + ".requirement.type");
                MMOItem mmoItem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getTypes().get(RequireType), RequireId);
                if (mmoItem == null) {
                    return false;
                }
                RequireStack = mmoItem.newBuilder().build();
                Amount = Items.getAmount(player, RequireStack);
            } else {
                return false;
            }

            // Check xem đủ ko, ko thì cook
            if (Amount < RequireAmount) {
                player.sendMessage("Bạn phải có đủ " + RequireAmount);
                return false;
            }

            int KetQua = (int) (Amount/ RequireAmount);
            RequireStack.setAmount(KetQua * RequireAmount);
            inv.removeItem(RequireStack);
            NewStack.setAmount(KetQua * NewAmount);
            inv.addItem(NewStack);

        }
        return true;
    }
}
