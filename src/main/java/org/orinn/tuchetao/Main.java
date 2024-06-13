package org.orinn.tuchetao;

import net.xconfig.bukkit.model.SimpleConfigurationManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.orinn.tuchetao.Commands.*;
import org.orinn.tuchetao.GUI.PersonalGUI;
import org.orinn.tuchetao.Listener.playerJoin;
import org.orinn.tuchetao.files.*;
import org.orinn.tuchetao.Listener.BlockBreak;
import org.orinn.tuchetao.storage.*;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    private static Main instance;
    private static final Logger logger = Logger.getLogger(DataFile.class.getName());

    @Override
    public void onEnable() {
        instance = this;
        SimpleConfigurationManager.register(this);
        this.loadDepend();
        this.loadFile();
        this.loadData();
        this.loadCommand();
        this.loadListener();
//        this.loadGUI();
    }

    @Override
    public void onDisable() {
        DataManager.saveAllData();
    }

    public void loadDepend() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            logger.warning("Không tìm thấy PlaceholderAPI!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public void loadFile() {
        FileManager.loadFiles();
        Settings.loadSettings();
        GuiFile.load();
        Data.loadData();
        Item.loadData();
    }

    public void saveFile() {
        GuiFile.save();
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

//    public void loadGUI() {
//        new PersonalGUI(this);
//    }

    public static Main getInstance() {
        return instance;
    }
}
