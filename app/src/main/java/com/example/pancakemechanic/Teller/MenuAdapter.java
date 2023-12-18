package com.example.pancakemechanic.Teller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pancakemechanic.R;

import java.net.URI;
import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<com.example.pancakemechanic.Teller.MenuAdapter.MenuViewHolder> {
    private Context context;
    private ArrayList name_id,price_id;
    private Button add_to_cart;


    public MenuAdapter(Context context, ArrayList name_id, ArrayList price_id) {
        this.context = context;
        this.name_id = name_id;
        this.price_id = price_id;
    }

    @NonNull
    @Override
    public com.example.pancakemechanic.Teller.MenuAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.activity_menu,parent,false);
        return new com.example.pancakemechanic.Teller.MenuAdapter.MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull com.example.pancakemechanic.Teller.MenuAdapter.MenuViewHolder holder, int position) {
        holder.name_id.setText(String.valueOf(name_id.get(position)));
        holder.price_id.setText(String.valueOf(price_id.get(position)));
    }

    @Override
    public int getItemCount() {
        return name_id.size();
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder {
        TextView name_id,price_id;
        Button add_to_cart;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            name_id = itemView.findViewById(R.id.textname);
            price_id = itemView.findViewById(R.id.textprice);
            add_to_cart = itemView.findViewById(R.id.button_add);
        }

    }

}
