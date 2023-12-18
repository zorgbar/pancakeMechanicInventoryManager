package com.example.pancakemechanic.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pancakemechanic.R;
import com.example.pancakemechanic.utils.model.PancakeCart;
import com.example.pancakemechanic.utils.model.PancakeItem;
import com.example.pancakemechanic.viewmodel.CartViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {

    private ImageView pancakeImageView;
    private TextView pancakeNameTV, pancakeCategoryNameTV, pancakePriceTV;
    private AppCompatButton addToCartBtn;
    private PancakeItem pancake;
    private CartViewModel viewModel;
    private List<PancakeCart> pancakeCartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        pancake = getIntent().getParcelableExtra("pancakeItem");
        initializeVariables();

        viewModel.getAllCartItems().observe(this, new Observer<List<PancakeCart>>() {
            @Override
            public void onChanged(List<PancakeCart> pancakeCarts) {
                pancakeCartList.addAll(pancakeCarts);
            }
        });

        if (pancake != null) {
            setDataToWidgets();
        }

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertToRoom();
            }
        });

    }

    private void insertToRoom(){
        PancakeCart pancakeCart = new PancakeCart();
        pancakeCart.setPancakeName(pancake.getPancakeName());
        pancakeCart.setPancakeCategoryName(pancake.getPancakeCategoryName());
        pancakeCart.setPancakePrice(pancake.getPancakePrice());
        pancakeCart.setPancakeImage(pancake.getPancakeImage());

        final int[] quantity = {1};
        final int[] id = new int[1];

        if (!pancakeCartList.isEmpty()){
            for(int i=0;i<pancakeCartList.size();i++){
                if (pancakeCart.getPancakeName().equals(pancakeCartList.get(i).getPancakeName())){
                    quantity[0] = pancakeCartList.get(i).getQuantity();
                    quantity[0]++;
                    id[0] = pancakeCartList.get(i).getId();
                }
            }
        }

        if (quantity[0]==1){
            pancakeCart.setQuantity(quantity[0]);
            pancakeCart.setTotalItemPrice(quantity[0]*pancakeCart.getPancakePrice());
            viewModel.insertCartItem(pancakeCart);
        }else{

            viewModel.updateQuantity(id[0] ,quantity[0]);
            viewModel.updatePrice(id[0] , quantity[0]*pancakeCart.getPancakePrice());
        }

        startActivity(new Intent(DetailedActivity.this , CartActivity.class));
    }

    private void setDataToWidgets() {
        pancakeNameTV.setText(pancake.getPancakeName());
        pancakeCategoryNameTV.setText(pancake.getPancakeCategoryName());
        pancakePriceTV.setText(String.valueOf(pancake.getPancakePrice()));
        pancakeImageView.setImageResource(pancake.getPancakeImage());
    }

    private void initializeVariables() {

        pancakeCartList = new ArrayList<>();
        pancakeImageView = findViewById(R.id.detailActivityPancakeIV);
        pancakeNameTV = findViewById(R.id.detailActivityPancakeNameTv);
        pancakeCategoryNameTV = findViewById(R.id.detailActivityPancakeCategoryNameTv);
        pancakePriceTV = findViewById(R.id.detailActivityPancakePriceTv);
        addToCartBtn = findViewById(R.id.detailActivityAddToCartBtn);

        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
    }
}
