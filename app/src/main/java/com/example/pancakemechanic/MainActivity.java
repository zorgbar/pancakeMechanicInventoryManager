package com.example.pancakemechanic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.pancakemechanic.utils.adapter.PancakeItemAdapter;
import com.example.pancakemechanic.utils.model.PancakeCart;
import com.example.pancakemechanic.utils.model.PancakeItem;
import com.example.pancakemechanic.viewmodel.CartViewModel;
import com.example.pancakemechanic.views.CartActivity;
import com.example.pancakemechanic.views.DetailedActivity;
import com.example.pancakemechanic.views.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PancakeItemAdapter.PancakeClickedListeners{

    private RecyclerView recyclerView;
    private List<PancakeItem> pancakeItemList;
    private PancakeItemAdapter adapter;
    private CartViewModel viewModel;
    private List<PancakeCart> pancakeCartList;
    private CoordinatorLayout coordinatorLayout;
    private ImageView cartImageView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
        setUpList();

        adapter.setPancakeItemList(pancakeItemList);
        recyclerView.setAdapter(adapter);

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.menu:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.cart:
                        startActivity(new Intent(getApplicationContext(), CartActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getAllCartItems().observe(this, new Observer<List<PancakeCart>>() {
            @Override
            public void onChanged(List<PancakeCart> pancakeCarts) {
                pancakeCartList.addAll(pancakeCarts);
            }
        });
    }

    private void setUpList() {
        pancakeItemList.add(new PancakeItem("THE BORING", "DESSERT", R.drawable.classic, 25));
        pancakeItemList.add(new PancakeItem("O-SO-CREAMY", "DESSERT", R.drawable.creamy, 30));
        pancakeItemList.add(new PancakeItem("BANOFFEE", "DESSERT", R.drawable.banoffee, 35));
        pancakeItemList.add(new PancakeItem("B&W", "DESSERT", R.drawable.bandw, 40));
        pancakeItemList.add(new PancakeItem("CHOCOLATE LOG", "DESSERT", R.drawable.chocolatelog, 40));
        pancakeItemList.add(new PancakeItem("NUTTY CRACK", "DESSERT", R.drawable.nutty, 40));
        pancakeItemList.add(new PancakeItem("MILKTART", "GOURMET", R.drawable.milktart, 40));
        pancakeItemList.add(new PancakeItem("BAR-ONE", "GOURMET", R.drawable.barone, 45));
        pancakeItemList.add(new PancakeItem("CRISPY MINT", "GOURMET", R.drawable.mint, 50));
        pancakeItemList.add(new PancakeItem("LOTUS FLOWER", "GOURMET", R.drawable.lotus, 55));
        pancakeItemList.add(new PancakeItem("THE VOLCANO", "GOURMET", R.drawable.volcano, 60));
        pancakeItemList.add(new PancakeItem("COOKIES N CREAM", "BOUQUETS", R.drawable.cookiencream, 60));
        pancakeItemList.add(new PancakeItem("SMORES", "BOUQUETS", R.drawable.smores, 70));
        pancakeItemList.add(new PancakeItem("OREO", "EXTRAS", R.drawable.oreo, 5));
        pancakeItemList.add(new PancakeItem("MARSHMALLOWS", "EXTRAS", R.drawable.marshmallows, 5));
        pancakeItemList.add(new PancakeItem("BANANA", "EXTRAS", R.drawable.banana, 10));
        pancakeItemList.add(new PancakeItem("NUTELLA", "EXTRAS", R.drawable.nutella, 10));
        pancakeItemList.add(new PancakeItem("STRAWBERRIES", "EXTRAS", R.drawable.strawberries, 15));

    }

    private void initializeVariables() {

        cartImageView = findViewById(R.id.cartIv);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        pancakeCartList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        pancakeItemList = new ArrayList<>();
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new PancakeItemAdapter(this);

    }

    @Override
    public void onCardClicked(PancakeItem pancake) {

        Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
        intent.putExtra("pancakeItem", pancake);
        startActivity(intent);
    }

    @Override
    public void onAddToCartBtnClicked(PancakeItem pancakeItem) {
        PancakeCart pancakeCart = new PancakeCart();
        pancakeCart.setPancakeName(pancakeItem.getPancakeName());
        pancakeCart.setPancakeCategoryName(pancakeItem.getPancakeCategoryName());
        pancakeCart.setPancakePrice(pancakeItem.getPancakePrice());
        pancakeCart.setPancakeImage(pancakeItem.getPancakeImage());

        final int[] quantity = {1};
        final int[] id = new int[1];

        if (!pancakeCartList.isEmpty()) {
            for (int i = 0; i < pancakeCartList.size(); i++) {
                if (pancakeCart.getPancakeName().equals(pancakeCartList.get(i).getPancakeName())) {
                    quantity[0] = pancakeCartList.get(i).getQuantity();
                    quantity[0]++;
                    id[0] = pancakeCartList.get(i).getId();
                }
            }
        }

        Log.d("TAG", "onAddToCartBtnClicked: " + quantity[0]);

        if (quantity[0] == 1) {
            pancakeCart.setQuantity(quantity[0]);
            pancakeCart.setTotalItemPrice(quantity[0] * pancakeCart.getPancakePrice());
            viewModel.insertCartItem(pancakeCart);
        } else {
            viewModel.updateQuantity(id[0], quantity[0]);
            viewModel.updatePrice(id[0], quantity[0] * pancakeCart.getPancakePrice());
        }

        makeSnackBar("Item Added To Cart");
    }

    private void makeSnackBar(String msg) {
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_SHORT)
                .setAction("Go to Cart", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, CartActivity.class));
                    }
                }).show();
    }
}