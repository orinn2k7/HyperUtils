package org.orinn.tuchetao;

import org.bukkit.plugin.java.JavaPlugin;
import org.orinn.tuchetao.Commands.CompressCommand;
import org.orinn.tuchetao.Commands.SetMultiplier;
import org.orinn.tuchetao.Commands.test;
import org.orinn.tuchetao.FileManager.Data;
import org.orinn.tuchetao.FileManager.Settings;
import org.orinn.tuchetao.Hook.HyperHook;

public final class Main extends JavaPlugin {
    private static Main instance;
    @Override
    public void onEnable() {
        instance = this;
        this.loadFile();
        this.loadCommand();
        this.loadHook();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void loadFile() {
        Settings.loadSettings();
        Data.loadData();
    }
    public void loadCommand() {
        new CompressCommand(this);
        new SetMultiplier(this);
        new test(this);
    }

    public void loadHook() {
        HyperHook.Hook();
    }

    public static Main getInstance() {
        return instance;
    }
}
