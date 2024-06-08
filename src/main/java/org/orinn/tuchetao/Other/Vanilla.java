package org.orinn.tuchetao.Other;

import org.bukkit.Material;

public class Vanilla {
    private String id;
    private int amount;

    public Vanilla(String id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }
    public int getAmount() {
        return amount;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
