package com.example.pancakemechanic.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pancakemechanic.dao.CartDAO;
import com.example.pancakemechanic.utils.model.PancakeCart;
import com.example.pancakemechanic.utils.model.Ticket;

@Database(entities = {PancakeCart.class, Ticket.class}, version = 2)
public abstract class CartDatabase extends RoomDatabase {

    public abstract CartDAO cartDAO();
    private static CartDatabase instance;

    public static synchronized CartDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                            , CartDatabase.class , "Pancake")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
