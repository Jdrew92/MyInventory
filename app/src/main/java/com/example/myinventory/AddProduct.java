package com.example.myinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

public class AddProduct extends AppCompatActivity {

    private EditText name, barcode, price, stock;

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
        String nam, bcode, prce;
        int stck;
        Product p;
        if (validate()){

            nam = name.getText().toString();
            bcode = barcode.getText().toString();
            prce = price.getText().toString();
            stck = Integer.parseInt(stock.getText().toString());

            p = new Product(nam, bcode, prce, stck);
            p.save();

            clear();
            Snackbar.make(v, R.string.add_success, Snackbar.LENGTH_LONG).show();
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