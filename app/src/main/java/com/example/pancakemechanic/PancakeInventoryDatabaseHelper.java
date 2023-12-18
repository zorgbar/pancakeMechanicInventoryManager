package com.example.pancakemechanic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PancakeInventoryDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "inventory.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_INVENTORY = "inventory";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_PRICE = "price";

    public PancakeInventoryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DatabaseHelper", "onCreate() method called");

        // Create the inventory table if it doesn't exist
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_INVENTORY + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                COLUMN_PRICE + " REAL NOT NULL)";
        db.execSQL(createTableQuery);

        // Insert sample data
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "Item 1");
        values.put(COLUMN_QUANTITY, 10);
        values.put(COLUMN_PRICE, 9.99);
        db.insert(TABLE_INVENTORY, null, values);

        // Retrieve and log the inventory items
        Cursor cursor = db.query(TABLE_INVENTORY, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndexOrThrow(COLUMN_ID);
            int nameIndex = cursor.getColumnIndexOrThrow(COLUMN_NAME);
            int quantityIndex = cursor.getColumnIndexOrThrow(COLUMN_QUANTITY);
            int priceIndex = cursor.getColumnIndexOrThrow(COLUMN_PRICE);

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            int quantity = cursor.getInt(quantityIndex);
            double price = cursor.getDouble(priceIndex);

            Log.d("DatabaseHelper", "Item ID: " + id + ", Name: " + name + ", Quantity: " + quantity + ", Price: " + price);
        }

        cursor.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableQuery = "DROP TABLE IF EXISTS " + TABLE_INVENTORY;
        db.execSQL(dropTableQuery);
        onCreate(db);
    }

    public List<InventoryItem> getAllInventoryItems() {
        List<InventoryItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID, COLUMN_NAME, COLUMN_QUANTITY, COLUMN_PRICE};
        Cursor cursor = db.query(TABLE_INVENTORY, projection, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
            InventoryItem item = new InventoryItem(id, name, quantity, price);
            itemList.add(item);
        }
        cursor.close();
        db.close();
        return itemList;
    }

    public long addInventoryItem(InventoryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, item.getName());
        values.put(COLUMN_QUANTITY, item.getQuantity());
        values.put(COLUMN_PRICE, item.getPrice());
        long itemId = db.insert(TABLE_INVENTORY, null, values);
        db.close();
        return itemId;
    }

    public int deleteInventoryItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(itemId)};
        int rowsAffected = db.delete(TABLE_INVENTORY, selection, selectionArgs);
        db.close();
        return rowsAffected;
    }

    public InventoryItem getInventoryItem(int itemId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_ID, COLUMN_NAME, COLUMN_QUANTITY, COLUMN_PRICE};
        String selection = COLUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(itemId)};
        Cursor cursor = db.query(TABLE_INVENTORY, projection, selection, selectionArgs, null, null, null);
        InventoryItem item = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));
            item = new InventoryItem(id, name, quantity, price);
        }
        cursor.close();
        db.close();
        return item;
    }
    public boolean updateInventoryItem(int itemId, String name, int quantity, double price) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        values.put("price", price);

        int rowsAffected = db.update("inventory", values, "id=?", new String[]{String.valueOf(itemId)});
        db.close();

        return rowsAffected > 0;
    }
}
