package com.example.pancakemechanic.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pancakemechanic.R;
import com.example.pancakemechanic.utils.model.PancakeCart;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private CartClickedListeners cartClickedListeners;
    private List<PancakeCart> pancakeCartList;

    public CartAdapter(CartClickedListeners cartClickedListeners) {
        this.cartClickedListeners = cartClickedListeners;
    }

    public void setPancakeCartList(List<PancakeCart> pancakeCartList) {
        this.pancakeCartList = pancakeCartList;
        notifyDataSetChanged();
    }

    public List<PancakeCart> getPancakeCartList() {
        return pancakeCartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        PancakeCart pancakeCart = pancakeCartList.get(position);
        holder.pancakeImageView.setImageResource(pancakeCart.getPancakeImage());
        holder.pancakeNameTv.setText(pancakeCart.getPancakeName());
        holder.pancakeCategoryNameTv.setText(pancakeCart.getPancakeCategoryName());
        holder.pancakeQuantity.setText(String.valueOf(pancakeCart.getQuantity()));
        holder.pancakePriceTv.setText(String.valueOf(pancakeCart.getTotalItemPrice()));

        holder.deletePancakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onDeleteClicked(pancakeCart);
            }
        });

        holder.addQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onPlusClicked(pancakeCart);
            }
        });

        holder.minusQuantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartClickedListeners.onMinusClicked(pancakeCart);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (pancakeCartList != null) {
            return pancakeCartList.size();
        }
        return 0;
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView pancakeNameTv, pancakeCategoryNameTv, pancakePriceTv, pancakeQuantity;
        private ImageView deletePancakeBtn;
        private ImageView pancakeImageView;
        private ImageButton addQuantityBtn, minusQuantityBtn;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            pancakeNameTv = itemView.findViewById(R.id.eachCartItemName);
            pancakeCategoryNameTv = itemView.findViewById(R.id.eachCartItemCategoryNameTv);
            pancakePriceTv = itemView.findViewById(R.id.eachCartItemPriceTv);
            deletePancakeBtn = itemView.findViewById(R.id.eachCartItemDeleteBtn);
            pancakeImageView = itemView.findViewById(R.id.eachCartItemIV);
            pancakeQuantity = itemView.findViewById(R.id.eachCartItemQuantityTV);
            addQuantityBtn = itemView.findViewById(R.id.eachCartItemAddQuantityBtn);
            minusQuantityBtn = itemView.findViewById(R.id.eachCartItemMinusQuantityBtn);
        }
    }

    public interface CartClickedListeners {
        void onDeleteClicked(PancakeCart pancakeCart);

        void onPlusClicked(PancakeCart pancakeCart);

        void onMinusClicked(PancakeCart pancakeCart);
    }

}