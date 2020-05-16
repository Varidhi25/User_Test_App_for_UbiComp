package com.example.android.userapp.model;

import java.io.Serializable;

public class details implements Serializable {
    String itemName,mainCategory,category,brand,expDate;
    int quantity;

    public details() {
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public details(String name, String mc, String c, String b, int q, String date){
        itemName=name;
        mainCategory=mc;
        category=c;
        brand=b;
        expDate=date;
        quantity=q;
   }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
