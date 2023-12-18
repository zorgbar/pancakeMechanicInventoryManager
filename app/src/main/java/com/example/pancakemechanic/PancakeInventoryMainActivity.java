package com.example.pancakemechanic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pancakemechanic.Admin.AdminAddProduct;
import com.example.pancakemechanic.Admin.AdminOrdersActivity;
import com.example.pancakemechanic.Admin.ProductListActivity;
import com.example.pancakemechanic.views.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class PancakeInventoryMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InventoryAdapter inventoryAdapter;
    private Button addItemButton;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pancake_inventory_activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        addItemButton = findViewById(R.id.addItemButton);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.inventory);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.menu:
                        startActivity(new Intent(getApplicationContext(), ProductListActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.orders:
                        startActivity(new Intent(getApplicationContext(), AdminOrdersActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), AdminAddProduct.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.inventory:
                        return true;

                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PancakeInventoryMainActivity.this, AddItemActivity.class);
                startActivity(intent);
            }
        });

        setupRecyclerView();
        loadInventoryItems();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        inventoryAdapter = new InventoryAdapter();
        recyclerView.setAdapter(inventoryAdapter);
    }

    private void loadInventoryItems() {
        PancakeInventoryDatabaseHelper pancakeInventoryDatabaseHelper = new PancakeInventoryDatabaseHelper(this);
        List<InventoryItem> itemList = pancakeInventoryDatabaseHelper.getAllInventoryItems();
        inventoryAdapter.setItems(itemList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadInventoryItems();
    }

    private class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {

        private List<InventoryItem> itemList;

        public void setItems(List<InventoryItem> itemList) {
            this.itemList = itemList;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            InventoryItem item = itemList.get(position);
            holder.itemNameTextView.setText(item.getName());
            holder.itemQuantityTextView.setText("Quantity: " + item.getQuantity());
            holder.itemPriceTextView.setText("Price: R" + item.getPrice());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PancakeInventoryMainActivity.this, EditItemActivity.class);
                    intent.putExtra("itemId", item.getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView itemNameTextView;
            TextView itemQuantityTextView;
            TextView itemPriceTextView;

            public ViewHolder(View itemView) {
                super(itemView);
                itemNameTextView = itemView.findViewById(R.id.itemName);
                itemQuantityTextView = itemView.findViewById(R.id.itemQuantity);
                itemPriceTextView = itemView.findViewById(R.id.itemPrice);
            }
        }
    }
}
