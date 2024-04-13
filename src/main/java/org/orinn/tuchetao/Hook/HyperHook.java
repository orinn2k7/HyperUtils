package org.orinn.tuchetao.Hook;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static spark.Spark.*;

public class HyperHook {

    public static void Hook() {

        port(25000);
        post("checkUser", (req, res) -> {
            Player output = Bukkit.getPlayer(req.queryParams("username"));
            if (output == null) {
                return "Player No Exist!!";
            }
            return "Online";
        });
    }
}
