package com.example.pancakemechanic.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pancakemechanic.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private ArrayList name_id,price_id;

    public ProductAdapter(Context context, ArrayList name_id, ArrayList price_id) {
        this.context = context;
        this.name_id = name_id;
        this.price_id = price_id;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new ProductViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.name_id.setText(String.valueOf(name_id.get(position)));
        holder.price_id.setText(String.valueOf(price_id.get(position)));
    }

    @Override
    public int getItemCount() {
        return name_id.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name_id,price_id;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            name_id = itemView.findViewById(R.id.textname);
            price_id = itemView.findViewById(R.id.textprice);
        }
    }
}
