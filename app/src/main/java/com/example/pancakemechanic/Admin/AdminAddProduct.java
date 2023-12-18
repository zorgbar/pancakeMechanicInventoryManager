package com.example.pancakemechanic.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pancakemechanic.DBHelperProducts;
import com.example.pancakemechanic.PancakeInventoryMainActivity;
import com.example.pancakemechanic.R;
import com.example.pancakemechanic.views.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminAddProduct extends AppCompatActivity {

    EditText name, price;
    Button add, view, delete, edit, InventoryManager; // Added button
    DBHelperProducts db;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminaddproduct);

        name = findViewById(R.id.edittext_name);
        price = findViewById(R.id.edittext_price);
        add = findViewById(R.id.button_add);
        view = findViewById(R.id.button_view);
        delete = findViewById(R.id.button_delete);
        edit = findViewById(R.id.button_edit);
        InventoryManager = findViewById(R.id.button_redirect); // Added button reference

        db = new DBHelperProducts(this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

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

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminAddProduct.this, ProductListActivity.class));
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = name.getText().toString();
                String priceTXT = price.getText().toString();

                Boolean checkinsertdata = db.insertproductdata(nameTXT, priceTXT);
                if (checkinsertdata == true) {
                    Toast.makeText(AdminAddProduct.this, "Product Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminAddProduct.this, "Product Not Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTxt = name.getText().toString();
                Boolean deleteproductdata = db.deleteproductdata(nameTxt);
                if (deleteproductdata == true) {
                    Toast.makeText(AdminAddProduct.this, "Product Deleted Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminAddProduct.this, "Product Not Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTxt = name.getText().toString();
                String priceTxt = price.getText().toString();
                Boolean editproductdata = db.updateproductdata(nameTxt, priceTxt);
                if (editproductdata == true) {
                    Toast.makeText(AdminAddProduct.this, "Product Updated Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminAddProduct.this, "Update Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        InventoryManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminAddProduct.this, PancakeInventoryMainActivity.class));
            }
        });
    }
}
