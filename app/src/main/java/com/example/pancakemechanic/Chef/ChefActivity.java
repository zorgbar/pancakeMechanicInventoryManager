package com.example.pancakemechanic.Chef;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;

import com.example.pancakemechanic.Admin.AdminAddProduct;
import com.example.pancakemechanic.MainActivity;
import com.example.pancakemechanic.R;
import com.example.pancakemechanic.views.CartActivity;
import com.example.pancakemechanic.views.LoginActivity;

import androidx.fragment.app.FragmentTransaction;

public class ChefActivity extends AppCompatActivity {
    private ImageView logoutImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef);

        logoutImageView = findViewById(R.id.logoutIv);

        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChefActivity.this, LoginActivity.class));
            }
        });


        if (savedInstanceState == null) {
            // Create a new instance of ChefFragment
            ChefFragment chefFragment = new ChefFragment();

            // Get the FragmentManager
            FragmentManager fragmentManager = getSupportFragmentManager();

            // Begin the fragment transaction
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Replace the fragment_container with the ChefFragment
            fragmentTransaction.replace(R.id.fragment_container, chefFragment);

            // Commit the transaction
            fragmentTransaction.commit();
        }
    }}

