package org.orinn.tuchetao.storage;

import java.util.HashMap;

public class DataManager {

    public static HashMap<String, playerData> PLAYER_DATA = new HashMap<>();

    public static void saveAllData() {
        for (String playerName : DataManager.PLAYER_DATA.keySet()) {
            DataStorage.saveData(playerName);
        }
    }

}
