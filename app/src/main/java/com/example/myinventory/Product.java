package com.example.myinventory;

public class Product {
    private String id, name, barcode, price;
    private int stock;

    public Product(String id, String name, String barcode, String price, int stock) {
        this.id = id;
        this.name = name;
        this.barcode = barcode;
        this.price = price;
        this.stock = stock;
    }

    public Product(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void delete(){
        Data.deleteProduct(this);
    }

    public void updateStock(int newStock) {
        Data.updateStock(this, newStock);
    }
}
