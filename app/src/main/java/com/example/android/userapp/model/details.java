package com.example.android.userapp.model;

public class details {
    String itemName,category,expDate;
    int quantity;

    public details() {
    }
   public details(String name,String c,int q,String date){
        itemName=name;
        category=c;
        expDate=date;
        quantity=q;
   }
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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
