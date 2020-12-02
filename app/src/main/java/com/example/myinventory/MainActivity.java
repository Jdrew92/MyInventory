package com.example.myinventory;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener{

    private ProductAdapter adapter;
    private RecyclerView list;
    private LinearLayoutManager llm;
    private ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        list = findViewById(R.id.lstProducts);
        if (Data.getProducts().isEmpty()){
            Log.i("TAG", "No existe");
            products = new ArrayList<>();
        } else {
            Log.i("TAG", "Sí existe");
            products = Data.getProducts();
        }
        llm = new LinearLayoutManager(this);
        adapter = new ProductAdapter(products, this);

        llm.setOrientation(RecyclerView.VERTICAL);
        list.setLayoutManager(llm);
        list.setAdapter(adapter);


    }

    public void add(View v){
        Intent intent = new Intent(MainActivity.this, AddProduct.class);
        startActivity(intent);
    }

    @Override
    public void onProductClick(Product p) {

    }
}