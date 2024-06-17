package org.orinn.tuchetao.GUI;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.orinn.tuchetao.GUI.Item.FilledItem;
import org.orinn.tuchetao.GUI.Item.InteractiveItem;
import org.orinn.tuchetao.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class GUIManager {

    private static final HashMap<Player, HashMap<UUID, InteractiveItem>> itemsMapper = new HashMap<>();
    private static final HashMap<Player, List<FilledItem>> decorateMapper = new HashMap<>();

    public static HashMap<Player, HashMap<UUID, InteractiveItem>> getItemsMapper() {
        return itemsMapper;
    }

    public static HashMap<Player, List<FilledItem>> getDecorateMapper() {
        return decorateMapper;
    }

    public static void register(Main plugin) {
        Bukkit.getPluginManager().registerEvents(new ItemListener(), plugin);
    }

}
