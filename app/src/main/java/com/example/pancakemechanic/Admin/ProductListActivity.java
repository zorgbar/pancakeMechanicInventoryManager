package com.example.pancakemechanic.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.pancakemechanic.DBHelperProducts;
import com.example.pancakemechanic.PancakeInventoryMainActivity;
import com.example.pancakemechanic.R;
import com.example.pancakemechanic.views.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> name,price;
    DBHelperProducts db;
    ProductAdapter adapter;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.menu:
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
                        startActivity(new Intent(getApplicationContext(), PancakeInventoryMainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.logout:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        db = new DBHelperProducts(this);
        name =  new ArrayList<>();
        price =  new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new ProductAdapter(this, name, price);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();
    }

    private void displaydata()
    {
        Cursor res = db.getdata();
        if(res.getCount()==0)
        {
            Toast.makeText(ProductListActivity.this, "No Entry Found", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            while(res.moveToNext()){
                name.add(res.getString(0));
                price.add(res.getString(1));
            }
        }

    }
}