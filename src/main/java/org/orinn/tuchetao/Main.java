package org.orinn.tuchetao;

import org.bukkit.plugin.java.JavaPlugin;
import org.orinn.tuchetao.Commands.Compress;
import org.orinn.tuchetao.Commands.CompressCommand;
import org.orinn.tuchetao.Commands.SetMultiplier;
import org.orinn.tuchetao.Commands.test;
import org.orinn.tuchetao.FileManager.*;

public final class Main extends JavaPlugin {
    private static Main instance;
    @Override
    public void onEnable() {
        instance = this;
        this.loadFile();
        this.loadCommand();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadFile() {
        Settings.loadSettings();
        Data.loadData();
        Item.loadData();
    }
    public void loadCommand() {
        new CompressCommand(this);
        new Compress(this);
        new SetMultiplier(this);
        new test(this);
    }


    public static Main getInstance() {
        return instance;
    }
}
