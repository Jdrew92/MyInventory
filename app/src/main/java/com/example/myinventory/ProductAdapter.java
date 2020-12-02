package com.example.myinventory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private ArrayList<Product> products;
    private OnProductClickListener clickListener;

    public ProductAdapter(ArrayList<Product> products, OnProductClickListener clickListener) {
        this.products = products;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product p = products.get(position);

        holder.name.setText(p.getName());
        holder.price.setText("$"+p.getPrice());
        holder.stock.setText(""+p.getStock());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onProductClick(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView name, price, stock;
        private View v;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView;
            name = v.findViewById(R.id.lblPNameItem);
            price = v.findViewById(R.id.lblPpriceItem);
            stock = v.findViewById(R.id.lblPStockItem);
        }
    }

    public interface OnProductClickListener{
        void onProductClick(Product p);
    }
}
