package org.orinn.tuchetao;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.orinn.tuchetao.storage.DataManager;
import org.orinn.tuchetao.storage.playerData;

public class PAPI extends PlaceholderExpansion {

    private final Main plugin;

    public PAPI(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "hyper";
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player p, @NotNull String args) {
        if (p == null) return null;
        playerData data = DataManager.PLAYER_DATA.get(p.getName());

        if (args.startsWith("storage_")) {
            String item = args.substring(8);
            return String.valueOf(data.getStorage().get(item.toUpperCase()));
        }

        return null;
    }
}
