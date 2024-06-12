package org.orinn.tuchetao.storage;

import java.util.HashMap;

public class playerData {

    public HashMap<String, Integer> storage;

    public playerData(HashMap<String, Integer> storage) {
        this.storage = storage;
    }

    public playerData() {
        storage = new HashMap<>();
    }

    public HashMap<String, Integer> getStorage() {
        return storage;
    }

    public int getAmount(String material) {
        return storage.get(material);
    }

    public void setAmount(String material, int amount) {
        storage.put(material, Math.max(amount, 0));
    }

    public void addAmount(String material, int amount) {
        if (amount < 0L && storage.get(material) + amount < 0L) {
            storage.put(material, 0);
        } else {
            storage.put(material, storage.get(material) + amount);
        }
    }

    public void subAmount(String material, int amount) {
        storage.put(material, Math.max(storage.get(material) - amount, 0));
    }




}
