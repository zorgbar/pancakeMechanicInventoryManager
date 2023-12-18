package com.example.pancakemechanic.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.pancakemechanic.Chef.ChefFragment;
import com.example.pancakemechanic.Chef.ChefViewModel;
import com.example.pancakemechanic.PancakeInventoryMainActivity;
import com.example.pancakemechanic.R;
import com.example.pancakemechanic.views.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminOrdersActivity extends AppCompatActivity {

    private ImageView logoutImageView;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_orders);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.orders);

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

        if (savedInstanceState == null) {
            // Create a new instance of ChefFragment
            AdminFragment adminFragment = new AdminFragment();

            // Get the FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();

            // Begin the fragment transaction
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Replace the fragment_container with the ChefFragment
            fragmentTransaction.replace(R.id.fragment_container, adminFragment);

            // Commit the transaction
            fragmentTransaction.commit();
        }
    }
}