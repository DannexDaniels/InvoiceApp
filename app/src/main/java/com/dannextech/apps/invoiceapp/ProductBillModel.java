package com.dannextech.apps.invoiceapp;

/**
 * Created by amoh on 12/28/2017.
 */

public class ProductBillModel {
    String prodName, cusName, cusOrg;
    int price, quantity;

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductBillModel() {
    }

}
