package com.example.myinventory;

import java.util.ArrayList;

public class Data {
    private static ArrayList<Product> products = new ArrayList<>();

    public static void saveProduct(Product product){
        products.add(product);
    }

    public static ArrayList<Product> getProducts(){
        return products;
    }
}
