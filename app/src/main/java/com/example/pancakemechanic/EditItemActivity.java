package com.example.pancakemechanic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditItemActivity extends AppCompatActivity {

    private PancakeInventoryDatabaseHelper pancakeInventoryDatabaseHelper;
    private int itemId;
    private EditText nameEditText;
    private EditText quantityEditText;
    private EditText priceEditText;
    private Button saveButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // Get the item ID from the intent extras
        itemId = getIntent().getIntExtra("itemId", -1);

        // Initialize DatabaseHelper
        pancakeInventoryDatabaseHelper = new PancakeInventoryDatabaseHelper(this);

        // Initialize EditText fields
        nameEditText = findViewById(R.id.editItemName);
        quantityEditText = findViewById(R.id.editItemQuantity);
        priceEditText = findViewById(R.id.editItemPrice);

        // Initialize buttons
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Set click listeners for buttons
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });

        // Load item details from the database
        loadItemDetails();
    }

    private void loadItemDetails() {
        // Retrieve the item from the database
        InventoryItem item = pancakeInventoryDatabaseHelper.getInventoryItem(itemId);

        if (item != null) {
            // Populate the EditText fields with item details
            nameEditText.setText(item.getName());
            quantityEditText.setText(String.valueOf(item.getQuantity()));
            priceEditText.setText(String.valueOf(item.getPrice()));
        } else {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void saveItem() {
        // Get the entered values from the EditText fields
        String name = nameEditText.getText().toString().trim();
        int quantity = Integer.parseInt(quantityEditText.getText().toString().trim());
        double price = Double.parseDouble(priceEditText.getText().toString().trim());

        // Update the item in the database
        boolean itemUpdated = pancakeInventoryDatabaseHelper.updateInventoryItem(itemId, name, quantity, price);

        if (itemUpdated) {
            Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update item", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteItem() {
        int rowsDeleted = pancakeInventoryDatabaseHelper.deleteInventoryItem(itemId);
        if (rowsDeleted > 0) {
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to delete item", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close the DatabaseHelper instance
        pancakeInventoryDatabaseHelper.close();
    }
}
