package com.example.pancakemechanic;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddItemActivity extends AppCompatActivity {

    private EditText itemNameEditText;
    private EditText itemQuantityEditText;
    private EditText itemPriceEditText;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Initialize views
        itemNameEditText = findViewById(R.id.editTextItemName);
        itemQuantityEditText = findViewById(R.id.editTextItemQuantity);
        itemPriceEditText = findViewById(R.id.editTextItemPrice);
        saveButton = findViewById(R.id.saveButton);

        // Set click listener for save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    private void saveItem() {
        // Get input values
        String itemName = itemNameEditText.getText().toString().trim();
        String itemQuantityStr = itemQuantityEditText.getText().toString().trim();
        String itemPriceStr = itemPriceEditText.getText().toString().trim();

        // Validate input values
        if (itemName.isEmpty() || itemQuantityStr.isEmpty() || itemPriceStr.isEmpty()) {
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int itemQuantity = Integer.parseInt(itemQuantityStr);
        double itemPrice = Double.parseDouble(itemPriceStr);

        // Create a new InventoryItem object
        InventoryItem item = new InventoryItem(0, itemName, itemQuantity, itemPrice);

        // Add the item to the database
        PancakeInventoryDatabaseHelper pancakeInventoryDatabaseHelper = new PancakeInventoryDatabaseHelper(this);
        long itemId = pancakeInventoryDatabaseHelper.addInventoryItem(item);

        if (itemId != -1) {
            Toast.makeText(this, "Item added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Finish the activity and go back to MainActivity
        } else {
            Toast.makeText(this, "Failed to add item", Toast.LENGTH_SHORT).show();
        }
    }
}

