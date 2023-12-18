package com.example.pancakemechanic.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.pancakemechanic.MainActivity;
import com.example.pancakemechanic.repository.CartRepo;
import com.example.pancakemechanic.R;
import com.example.pancakemechanic.utils.adapter.CartAdapter;
import com.example.pancakemechanic.utils.model.PancakeCart;
import com.example.pancakemechanic.utils.model.Ticket;
import com.example.pancakemechanic.viewmodel.CartViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartClickedListeners {

    private RecyclerView recyclerView;
    private CartViewModel cartViewModel;
    private TextView totalCartPriceTv, textView;
    private AppCompatButton checkoutBtn;
    private CardView cardView;
    private CartAdapter cartAdapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initializeVariables();

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.cart);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.cart:
                        return true;
                }
                return false;
            }
        });

        cartViewModel.getAllCartItems().observe(this, new Observer<List<PancakeCart>>() {
            @Override
            public void onChanged(List<PancakeCart> pancakeCarts) {
                double price = 0;
                cartAdapter.setPancakeCartList(pancakeCarts);
                for (int i = 0; i < pancakeCarts.size(); i++) {
                    price = price + pancakeCarts.get(i).getTotalItemPrice();
                }
                totalCartPriceTv.setText(String.valueOf(price));
            }
        });

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartViewModel.deleteAllCartItems();
                textView.setVisibility(View.INVISIBLE);
                checkoutBtn.setVisibility(View.INVISIBLE);
                totalCartPriceTv.setVisibility(View.INVISIBLE);
                cardView.setVisibility(View.VISIBLE);

                // Create a ticket object with the items from the cart
                List<PancakeCart> pancakeCarts = cartAdapter.getPancakeCartList();
                Ticket ticket = new Ticket(pancakeCarts);

                // Log the ticket in the database
                CartRepo cartRepo = new CartRepo(getApplication());
                cartRepo.logTicketInDatabase(ticket);
            }
        });
    }

    private void initializeVariables() {
        cartAdapter = new CartAdapter(this);
        textView = findViewById(R.id.textView2);
        cardView = findViewById(R.id.cartActivityCardView);
        totalCartPriceTv = findViewById(R.id.cartActivityTotalPriceTv);
        checkoutBtn = findViewById(R.id.cartActivityCheckoutBtn);
        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        recyclerView = findViewById(R.id.cartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(cartAdapter);
    }

    @Override
    public void onDeleteClicked(PancakeCart pancakeCart) {
        cartViewModel.deleteCartItem(pancakeCart);
    }

    @Override
    public void onPlusClicked(PancakeCart pancakeCart) {
        int quantity = pancakeCart.getQuantity() + 1;
        cartViewModel.updateQuantity(pancakeCart.getId(), quantity);
        cartViewModel.updatePrice(pancakeCart.getId(), quantity * pancakeCart.getPancakePrice());
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMinusClicked(PancakeCart pancakeCart) {
        int quantity = pancakeCart.getQuantity() - 1;
        if (quantity != 0) {
            cartViewModel.updateQuantity(pancakeCart.getId(), pancakeCart.getQuantity());
            cartViewModel.updatePrice(pancakeCart.getId(), quantity * pancakeCart.getPancakePrice());
        } else {
            cartViewModel.deleteCartItem(pancakeCart);
        }
        cartAdapter.notifyDataSetChanged();
    }
}