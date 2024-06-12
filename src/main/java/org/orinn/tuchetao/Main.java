package org.orinn.tuchetao;

import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.orinn.tuchetao.Commands.*;
import org.orinn.tuchetao.Listener.playerJoin;
import org.orinn.tuchetao.files.*;
import org.orinn.tuchetao.Listener.BlockBreak;
import org.orinn.tuchetao.storage.DataManager;
import org.orinn.tuchetao.storage.DataStorage;
import org.orinn.tuchetao.storage.BlocksList;
import org.orinn.tuchetao.storage.DropsList;

public final class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        SimpleConfigurationManager.register(this);
        this.loadFile();
        this.loadData();
        this.loadCommand();
        this.loadListener();
    }

    @Override
    public void onDisable() {
        DataManager.saveAllData();
    }

    public void loadFile() {
        FileManager.loadFiles();
        Settings.loadSettings();
        Data.loadData();
        Item.loadData();
    }

    public void loadCommand() {
        new CompressCommand(this);
        new Compress(this);
        new SetMultiplier(this);
        new test(this);
        new KhoCommand(this);
    }

    public void loadData() {
        BlocksList.loadBlocks();
        DropsList.loadDrops();
        for (Player player : getServer().getOnlinePlayers()) {
            DataStorage.loadData(player);
            DataStorage.saveData(player.getName());
            player.sendMessage(Utils.TranslateColorCodes("&eStorage &8|&a Khởi tạo dữ liệu thành công..."));
        }
    }

    public void loadListener() {
        new BlockBreak(this);
        new playerJoin(this);
    }

    public static Main getInstance() {
        return instance;
    }
}
