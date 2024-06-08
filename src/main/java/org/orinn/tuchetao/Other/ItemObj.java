package org.orinn.tuchetao.Other;

public class ItemObj {
    private String item;
    private String type;
    private String id;
    private Integer amount;

    public ItemObj(String item, String type, String id, Integer amount) {
        this.item = item;
        this.type = type;
        this.id = id;
        this.amount = amount;
    }

    public ItemObj() {
        // Khởi tạo các trường nếu cần
        this.item = null;
        this.type = null; // Hoặc gán giá trị mặc định khác
        this.id = null; // Hoặc gán giá trị mặc định khác
        this.amount = null;
    }

    public String getType() {
        return type;
    }
    public String getId() {
        return id;
    }
    public Integer getAmount() { // Trả về Integer để có thể null
        return amount;
    }

    public void setType(String type) {
        this.type = type;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setAmount(Integer amount) { // Nhận Integer để cho phép null
        this.amount = amount;
    }
}