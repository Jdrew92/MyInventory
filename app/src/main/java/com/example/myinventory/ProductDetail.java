package com.example.myinventory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;

public class ProductDetail extends AppCompatActivity {

    private TextView name, barcode, price, stock;
    private Bundle bundle;
    private Intent intent;
    private Product p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        String  id, nam, bcode, prc;
        int stck;

        name = findViewById(R.id.lblProductName);
        barcode = findViewById(R.id.lblProductBCode);
        price = findViewById(R.id.lblProductPrice);
        stock = findViewById(R.id.lblProductStock);

        intent = getIntent();
        bundle = intent.getBundleExtra("data");

        id = bundle.getString("id");
        nam = bundle.getString("name");
        bcode = bundle.getString("barcode");
        prc = bundle.getString("price");
        stck = bundle.getInt("stock");


        p = new Product(id, nam, bcode, prc, stck);

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

    public void delete(View v){
        String yes, no;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.delete_product);
        builder.setMessage(R.string.msg_delete);
        yes = getString(R.string.yes);
        no = getString(R.string.no);
        builder.setPositiveButton(yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                p.delete();
                onBackPressed();
            }
        });
        builder.setNegativeButton(no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addToStock(View v){
        EditText addstock = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_stock);
        builder.setMessage("How many products do you want to add?");
        builder.setView(addstock);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               int newStock = Integer.parseInt(stock.getText().toString()) + Integer.parseInt(addstock.getText().toString());
               p.updateStock(newStock);
               stock.setText(""+newStock);
               Snackbar.make(v, "Product stock updated successfully!", Snackbar.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void removeFromStock(View v){
        EditText addstock = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_stock);
        builder.setMessage("How many products do you want to add?");
        builder.setView(addstock);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int newStock = Integer.parseInt(stock.getText().toString()) - Integer.parseInt(addstock.getText().toString());
                p.updateStock(newStock);
                stock.setText(""+newStock);
                Snackbar.make(v, "Product stock updated successfully!", Snackbar.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}