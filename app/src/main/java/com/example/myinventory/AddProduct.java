package com.example.myinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class AddProduct extends AppCompatActivity {

    private EditText name, barcode, price, stock;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private String db = "Products";
    private InputMethodManager im;
    private DecimalFormat df = new DecimalFormat("#,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        name = findViewById(R.id.txtProductName);
        barcode = findViewById(R.id.txtBarcode);
        price = findViewById(R.id.txtPrice);
        stock = findViewById(R.id.txtFirstStock);


    }

    public void add(View v){
        String id, nam, bcode, prce;
        int stck;
        Product p;

        im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(name.getWindowToken(), 0);

        if (validate()){
            id = Data.getId();
            nam = name.getText().toString();
            bcode = barcode.getText().toString();
            prce = df.format(Integer.parseInt(price.getText().toString()));
            stck = Integer.parseInt(stock.getText().toString());

            p = new Product(id, nam, bcode, prce, stck);

            addVerifyingBarcode(v, p);

        }
    }

    private boolean validate(){
        if (name.getText().toString().isEmpty()){
            name.setError(getString(R.string.name_empty_error));
            name.requestFocus();
            return false;
        }
        if (barcode.getText().toString().isEmpty()){
            barcode.setError(getString(R.string.barcode_empty_error));
            barcode.requestFocus();
            return false;
        }
        if (price.getText().toString().isEmpty()){
            price.setError(getString(R.string.price_empty_error));
            price.requestFocus();
            return false;
        }
        if (stock.getText().toString().isEmpty()){
            stock.setError("Type in the first stock");
            stock.requestFocus();
            return false;
        }
        return true;
    }

    public void addVerifyingBarcode(View v, Product p){
        reference.child(db).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> barcodes = new ArrayList<>();
                if (snapshot.exists()){
                    String bcode;
                    Log.i("TAG", "onDataChange: No está vacío");
                    for (DataSnapshot data : snapshot.getChildren()){
                        bcode = data.getValue(Product.class).getBarcode();
                        barcodes.add(bcode);
                    }
                    if (!barcodes.contains(p.getBarcode())){
                        Log.i("TAG", "onDataChange: No lo tiene ");
                        p.save();
                        clear();
                        Snackbar.make(v, R.string.add_success, Snackbar.LENGTH_LONG).show();
                    } else {
                        Log.i("TAG", "onDataChange: Si lo tiene ");
                        barcode.setError(getString(R.string.barcode_repeated));
                        barcode.requestFocus();
                    }
                } else {
                    Log.i("TAG", "onDataChange: Está vacío");
                    p.save();
                    clear();
                    Snackbar.make(v, R.string.add_success, Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void clear(View v){
        clear();
    }

    private void clear(){
        name.setText("");
        barcode.setText("");
        price.setText("");
        stock.setText("");
        name.requestFocus();
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(AddProduct.this, MainActivity.class);
        startActivity(i);
    }
}