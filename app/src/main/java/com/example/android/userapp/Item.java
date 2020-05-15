package com.example.android.userapp;

public class Item {
    private String itemName,mainCategory,category,brand;

    public Item(String itemName,String mainCategory,String category,String brand) {
        this.itemName=itemName;
        this.mainCategory=mainCategory;
        this.category=category;
        this.brand=brand;
    }

    public String getItemName() {
        return itemName;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }
}
