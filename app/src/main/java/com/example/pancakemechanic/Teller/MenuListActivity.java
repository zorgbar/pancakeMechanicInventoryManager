package com.example.pancakemechanic.Teller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pancakemechanic.Admin.ProductAdapter;
import com.example.pancakemechanic.DBHelperProducts;
import com.example.pancakemechanic.R;

import java.util.ArrayList;

public class MenuListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<String> name,price;
    DBHelperProducts db;
    MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        db = new DBHelperProducts(this);
        name =  new ArrayList<>();
        price =  new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new MenuAdapter(this, name, price);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        displaydata();
    }

    private void displaydata()
    {
        Cursor res = db.getdata();
        if(res.getCount()==0)
        {
            Toast.makeText(com.example.pancakemechanic.Teller.MenuListActivity.this, "No Menu", Toast.LENGTH_SHORT).show();
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