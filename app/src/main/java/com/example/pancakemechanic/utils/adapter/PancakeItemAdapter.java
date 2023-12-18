package com.example.pancakemechanic.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pancakemechanic.R;
import com.example.pancakemechanic.utils.model.PancakeItem;

import java.util.List;

public class PancakeItemAdapter extends RecyclerView.Adapter<PancakeItemAdapter.PancakeItemViewHolder> {

    private List<PancakeItem> pancakeItemList;
    private PancakeItemAdapter.PancakeClickedListeners pancakeClickedListeners;
    public PancakeItemAdapter(PancakeItemAdapter.PancakeClickedListeners pancakeClickedListeners){
        this.pancakeClickedListeners = pancakeClickedListeners;
    }
    public void setPancakeItemList(List<PancakeItem> pancakeItemList){
        this.pancakeItemList = pancakeItemList;
    }
    @NonNull
    @Override
    public PancakeItemAdapter.PancakeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_pancake , parent , false);
        return new PancakeItemAdapter.PancakeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PancakeItemAdapter.PancakeItemViewHolder holder, int position) {
        PancakeItem pancakeItem = pancakeItemList.get(position);
        holder.pancakeNameTv.setText(pancakeItem.getPancakeName());
        holder.pancakeCategoryNameTv.setText(pancakeItem.getPancakeCategoryName());
        holder.pancakePriceTv.setText(String.valueOf(pancakeItem.getPancakePrice()));
        holder.pancakeImageView.setImageResource(pancakeItem.getPancakeImage());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pancakeClickedListeners.onCardClicked(pancakeItem);
            }
        });

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pancakeClickedListeners.onAddToCartBtnClicked(pancakeItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (pancakeItemList == null){
            return 0;
        }else{
            return pancakeItemList.size();
        }
    }

    public class PancakeItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView pancakeImageView , addToCartBtn;
        private TextView pancakeNameTv, pancakeCategoryNameTv, pancakePriceTv;
        private CardView cardView;
        public PancakeItemViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.eachPancakeCardView);
            addToCartBtn = itemView.findViewById(R.id.eachPancakeAddToCartBtn);
            pancakeNameTv = itemView.findViewById(R.id.eachPancakeName);
            pancakeImageView = itemView.findViewById(R.id.eachPancakeIv);
            pancakeCategoryNameTv = itemView.findViewById(R.id.eachPancakeCategoryNameTv);
            pancakePriceTv = itemView.findViewById(R.id.eachPancakePriceTv);
        }
    }

    public interface PancakeClickedListeners{
        void onCardClicked(PancakeItem pancake);
        void onAddToCartBtnClicked(PancakeItem pancakeItem);
    }
}