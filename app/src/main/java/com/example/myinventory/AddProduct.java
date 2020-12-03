package com.example.myinventory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DecimalFormat;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddProduct extends AppCompatActivity {

    private EditText name, barcode, price, stock;
    private CircleImageView img;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private String db = "Products";
    private InputMethodManager im;
    private Uri uri;
    private DecimalFormat df = new DecimalFormat("#,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        name = findViewById(R.id.txtProductName);
        barcode = findViewById(R.id.txtBarcode);
        price = findViewById(R.id.txtPrice);
        stock = findViewById(R.id.txtFirstStock);
        img = findViewById(R.id.imgSelectImage);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

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
        if (uri == null){
            Snackbar.make((View) name, getText(R.string.select_image), Snackbar.LENGTH_LONG).show();
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
                        uploadImg(p.getId());
                        clear();
                        Snackbar.make(v, R.string.add_success, Snackbar.LENGTH_LONG).show();
                        uri = null;
                    } else {
                        Log.i("TAG", "onDataChange: Si lo tiene ");
                        barcode.setError(getString(R.string.barcode_repeated));
                        barcode.requestFocus();
                    }
                } else {
                    Log.i("TAG", "onDataChange: Está vacío");
                    p.save();
                    uploadImg(p.getId());
                    clear();
                    Snackbar.make(v, R.string.add_success, Snackbar.LENGTH_LONG).show();
                    uri = null;
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
        img.setImageResource(android.R.drawable.ic_menu_gallery);
        name.requestFocus();
    }

    public void onBackPressed(){
        finish();
        Intent i = new Intent(AddProduct.this, MainActivity.class);
        startActivity(i);
    }

    public void selectImage(View v){
        Intent in = new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(in,getString(R.string.select_image)), 1);
    }

    public void uploadImg(String id){
        StorageReference child = storageReference.child(id);
        UploadTask uploadTask = child.putFile(uri);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            uri = data.getData();
            if (uri != null){
                img.setImageURI(uri);
            }
        }
    }
}