package com.example.pancakemechanic.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Update;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.pancakemechanic.utils.model.PancakeCart;
import com.example.pancakemechanic.utils.model.Ticket;

import java.util.List;

@Dao
public interface CartDAO {
    @Insert
    void insertCartItem(PancakeCart pancakeCart);

    @Delete
    void deleteCartItem(PancakeCart pancakeCart);

    @Query("UPDATE pancake_table SET quantity=:quantity WHERE id=:id")
    void updateQuantity(int id, int quantity);

    @Query("UPDATE pancake_table SET totalItemPrice=:totalItemPrice WHERE id=:id")
    void updatePrice(int id, double totalItemPrice);

    @Query("DELETE FROM pancake_table")
    void deleteAllItems();

    @Insert
    void insertTicket(Ticket ticket);



    @Query("SELECT * FROM pancake_table")
    LiveData<List<PancakeCart>> getAllCartItems();

    @Query("SELECT * FROM tickets")
    LiveData<List<Ticket>> getAllTickets();

    @Update
    void updateTicket(Ticket ticket);

    @Delete
    void deleteTicket(Ticket ticket);




}
