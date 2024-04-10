package org.orinn.tuchetao.Listener;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.item.mmoitem.MMOItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.orinn.tuchetao.FileManager.Data;
import org.orinn.tuchetao.FileManager.Settings;

import java.util.Set;

public class CheTao {

//    private final JavaPlugin plugin;
//    private static YamlConfiguration settings;
//
//    public CheTao(JavaPlugin plugin, YamlConfiguration settings) {
//        this.plugin = plugin;
//        this.settings = settings;
//        load();
//    }
//
//    private void load() {
//        File config = new File(plugin.getDataFolder(), "settings.yml");
//
//        if (!config.exists()) {
//            plugin.saveResource("settings.yml", false);
//        }
//        settings = YamlConfiguration.loadConfiguration(config);
//    }



    public static void doCompress(Player player, String NhapKhoangSan) {

        FileConfiguration settings = Settings.getSettings();
        FileConfiguration data = Data.getData();

        Set<String> CompressList = settings.getConfigurationSection("compressor").getKeys(false);
        Inventory inv = player.getInventory();


        if (CompressList.contains(NhapKhoangSan)) {

            String permission = settings.getString("compressor." + NhapKhoangSan + ".permission");


            if (!player.hasPermission(permission)) {
                player.sendMessage("Không có quyền");
                return;
            }

            String RequireItemType = settings.getString("compressor." + NhapKhoangSan + ".requirement.item-type");
            String NewItemType = settings.getString("compressor." + NhapKhoangSan + ".new-item.item-type");
            String RequireId = settings.getString("compressor." + NhapKhoangSan + ".requirement.id");
            String NewId = settings.getString("compressor." + NhapKhoangSan + ".new-item.id");
            int RequireAmount = settings.getInt("compressor." + NhapKhoangSan + ".requirement.amount");
            int NewAmount = settings.getInt("compressor." + NhapKhoangSan + ".new-item.amount");
            double Multiplier = data.getDouble(player.getName() + ".multiplier");
            ItemStack RequireStack = null;
            ItemStack NewStack = null;


            // requireitem
            int Amount = 0;
            if (RequireItemType.equalsIgnoreCase("VANILLA")) {
                RequireStack = new ItemStack(Material.valueOf(RequireId));
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null && item.isSimilar(RequireStack)) {
                        Amount += item.getAmount();
                    }
                }
            } else if (RequireItemType.equalsIgnoreCase("MMOITEMS")) {
                String RequireType = settings.getString("compressor." + NhapKhoangSan + ".requirement.type");
                MMOItem mmoItem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getTypes().get(RequireType), RequireId);
                if (mmoItem == null) {
                    return;
                }
                RequireStack = mmoItem.newBuilder().build();
                for (ItemStack item : player.getInventory().getContents()) {
                    if (item != null && item.isSimilar(RequireStack)) {
                        Amount += item.getAmount();
                    }
                }
            } else {

                return;
            }

            // newitem
            if (NewItemType.equalsIgnoreCase("VANILLA")) {
                NewStack = new ItemStack(Material.valueOf(NewId));
            } else if (NewItemType.equalsIgnoreCase("MMOITEMS")) {
                String NewType = settings.getString("compressor." + NhapKhoangSan + ".new-item.type");
                MMOItem mmoItem = MMOItems.plugin.getMMOItem(MMOItems.plugin.getTypes().get(NewType), NewId);
                if (mmoItem == null) {
                    return;
                }
                NewStack = mmoItem.newBuilder().build();
            } else {
                return;
            }

            if (Amount < RequireAmount) {
                return;
            }

            int KetQua = (int) (Amount / RequireAmount) ;
            RequireStack.setAmount(KetQua*RequireAmount);
            inv.removeItem(RequireStack);
            NewStack.setAmount((int) (KetQua*NewAmount*Multiplier));
            inv.addItem(NewStack);

        }
    }
}