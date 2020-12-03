package com.example.myinventory;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetail extends AppCompatActivity {

    private TextView name, barcode, price, stock;
    private Bundle bundle;
    private Intent intent;
    private CircleImageView img;
    private Product p;
    private StorageReference storageReference;

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
        img = findViewById(R.id.imgProductImg);

        storageReference = FirebaseStorage.getInstance().getReference();

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
        storageReference.child(id).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(img);
            }
        });

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
        EditText addStock = new EditText(this);
        addStock.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_stock);
        builder.setMessage(R.string.add_msg);
        builder.setView(addStock);
        builder.setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.cancel, null);

        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean closeDialog = false;
                if (!addStock.getText().toString().isEmpty()){
                    int newStock = Integer.parseInt(stock.getText().toString()) + Integer.parseInt(addStock.getText().toString());
                    p.updateStock(newStock);
                    stock.setText(""+newStock);
                    closeDialog = true;
                    Snackbar.make(v, R.string.updated_msg, Snackbar.LENGTH_LONG).show();
                } else {
                    addStock.setError(getString(R.string.number_msg));
                    closeDialog = false;
                }
                if (closeDialog == true){
                    dialog.dismiss();
                }
            }
        });
    }

    public void removeFromStock(View v){
        EditText removeStock = new EditText(this);
        removeStock.setInputType(InputType.TYPE_CLASS_NUMBER);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.add_stock);
        builder.setMessage(R.string.remove_msg);
        builder.setView(removeStock);
        builder.setPositiveButton(R.string.remove, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean closeDialog = false;
                if (!removeStock.getText().toString().isEmpty()) {
                    int newStock = Integer.parseInt(stock.getText().toString()) - Integer.parseInt(removeStock.getText().toString());
                    p.updateStock(newStock);
                    stock.setText("" + newStock);
                    Snackbar.make(v, R.string.updated_msg, Snackbar.LENGTH_LONG).show();
                    closeDialog = true;
                } else {
                    removeStock.setError(getString(R.string.number_msg));
                    closeDialog = false;
                }
                if (closeDialog == true){
                    dialog.dismiss();
                }
            }
        });
    }

}