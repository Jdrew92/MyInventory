package com.example.myinventory;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener{

    private ProductAdapter adapter;
    private RecyclerView list;
    private LinearLayoutManager llm;
    private ArrayList<Product> products;
    private DatabaseReference databaseReference;
    private static String db = "Products";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        list = findViewById(R.id.lstProducts);

        products = new ArrayList<>();
        llm = new LinearLayoutManager(this);
        adapter = new ProductAdapter(products, this);

        llm.setOrientation(RecyclerView.VERTICAL);
        list.setLayoutManager(llm);
        list.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(db).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                if (snapshot.exists()){
                    for (DataSnapshot snap : snapshot.getChildren()){
                        Product p = snap.getValue(Product.class);
                        products.add(p);
                    }
                }
                adapter.notifyDataSetChanged();
                Data.setProducts(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void add(View v){
        Intent intent = new Intent(MainActivity.this, AddProduct.class);
        startActivity(intent);
    }

    @Override
    public void onProductClick(Product p) {
        Intent intent;
        Bundle bundle;
        bundle = new Bundle();
        bundle.putString("id", p.getId());
        bundle.putString("name", p.getName());
        bundle.putString("barcode", p.getBarcode());
        bundle.putString("price", p.getPrice());
        bundle.putInt("stock", p.getStock());

        intent = new Intent(MainActivity.this, ProductDetail.class);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }
}