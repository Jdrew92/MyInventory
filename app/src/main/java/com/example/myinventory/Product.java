package com.example.myinventory;

public class Product {
    private String name, barcode, price;
    private int stock;

    public Product(String name, String barcode, String price, int stock) {
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void save(){
        Data.saveProduct(this);
    }
}
