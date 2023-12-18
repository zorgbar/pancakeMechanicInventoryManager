package com.example.pancakemechanic.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.pancakemechanic.dao.CartDAO;
import com.example.pancakemechanic.database.CartDatabase;
import com.example.pancakemechanic.utils.model.PancakeCart;
import com.example.pancakemechanic.utils.model.Ticket;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CartRepo {

    private CartDAO cartDAO;
    private LiveData<List<PancakeCart>> allCartItemsLiveData;
    private LiveData<List<Ticket>> allTicketsLiveData;

    private Executor executor = Executors.newSingleThreadExecutor();

    public LiveData<List<PancakeCart>> getAllCartItemsLiveData() {
        return allCartItemsLiveData;
    }

    public LiveData<List<Ticket>> getAllTickets() {
        return allTicketsLiveData;
    }



    public CartRepo(Application application) {
        CartDatabase database = CartDatabase.getInstance(application);
        cartDAO = database.cartDAO();
        allCartItemsLiveData = cartDAO.getAllCartItems();
        allTicketsLiveData = cartDAO.getAllTickets();

    }

    public void insertCartItem(PancakeCart pancakeCart) {
        executor.execute(() -> cartDAO.insertCartItem(pancakeCart));
    }

    public void deleteCartItem(PancakeCart pancakeCart) {
        executor.execute(() -> cartDAO.deleteCartItem(pancakeCart));
    }

    public void deleteTicket(Ticket ticket) {
        executor.execute(() -> cartDAO.deleteTicket(ticket));
    }



    public void updateQuantity(int id, int quantity) {
        executor.execute(() -> cartDAO.updateQuantity(id, quantity));
    }

    public void updatePrice(int id, double price) {
        executor.execute(() -> cartDAO.updatePrice(id, price));
    }

    public void deleteAllCartItems() {
        executor.execute(cartDAO::deleteAllItems);
    }

    public void logTicketInDatabase(Ticket ticket) {
        executor.execute(() -> cartDAO.insertTicket(ticket));
    }

    public void updateTicket(Ticket ticket) {
        executor.execute(() -> cartDAO.updateTicket(ticket));
    }

}
