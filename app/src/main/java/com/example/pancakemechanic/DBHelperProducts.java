package com.example.pancakemechanic;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelperProducts extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="pancakes";
    public static final String TABLE_NAME ="products";


    public DBHelperProducts(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE products (name TEXT primary key, price TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public Boolean insertproductdata(String name, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",price);
        long res = db.insert("products", null,contentValues);
        if(res==-1){
            return false;
        }
        else{
            return true;
        }
    }

    public Boolean updateproductdata(String name, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("price",price);
        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE name = ?",new String[] {name});
        if(cursor.getCount()>0) {

            long res = db.update("products", contentValues, "name=?", new String[]{name});
            if (res == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }

    public Boolean deleteproductdata(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM products WHERE name = ?",new String[] {name});
        if(cursor.getCount()>0) {

            long res = db.delete("products", "name=?", new String[]{name});
            if (res == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }
    }
    public Cursor getdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM products", null);
        return cursor;
    }
}

