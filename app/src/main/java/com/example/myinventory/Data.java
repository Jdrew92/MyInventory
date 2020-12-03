package com.example.myinventory;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Data {
    private static String db = "Products";
    private static boolean verified;
    private static ArrayList<Product> products = new ArrayList<>();
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static String getId(){
        return databaseReference.push().getKey();
    }

    public static void saveProduct(Product product){
        databaseReference.child(db).child(product.getId()).setValue(product);
    }

    public static void setProducts(ArrayList<Product> productsList){
        products = productsList;
    }

    public static void deleteProduct(Product product){
        databaseReference.child(db).child(product.getId()).removeValue();
    }

    public static void updateStock(Product product, int newStock) {
        databaseReference.child(db).child(product.getId()).child("stock").setValue(newStock);
    }
}
