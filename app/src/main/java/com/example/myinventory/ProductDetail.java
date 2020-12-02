package com.example.myinventory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class ProductDetail extends AppCompatActivity {

    private TextView name, barcode, price, stock;
    private Bundle bundle;
    private Intent intent;
    private Product p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        String  nam, bcode, prc;
        int stck;

        name = findViewById(R.id.lblProductName);
        barcode = findViewById(R.id.lblProductBCode);
        price = findViewById(R.id.lblProductPrice);
        stock = findViewById(R.id.lblProductStock);

        //Bundle es un encapsulamiento, como un zip
        intent = getIntent();
        bundle = intent.getBundleExtra("data");

        nam = bundle.getString("name");
        bcode = bundle.getString("barcode");
        prc = bundle.getString("price");
        stck = bundle.getInt("stock");


        p = new Product(nam, bcode, prc, stck);

        name.setText(nam);
        barcode.setText(bcode);
        price.setText("$"+prc);
        stock.setText(""+stck);
    }

    public void onBackPressed(){
        finish();
        Intent intent = new Intent(ProductDetail.this, MainActivity.class);
        startActivity(intent);
    }
}