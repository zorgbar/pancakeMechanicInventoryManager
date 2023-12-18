package com.example.pancakemechanic.Admin;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.pancakemechanic.repository.CartRepo;
import com.example.pancakemechanic.utils.model.Ticket;

import java.util.List;

public class AdminViewModel extends ViewModel {
    private CartRepo ticketRepository;
    private LiveData<List<Ticket>> ticketsLiveData;

    public void init(Application application) {
        ticketRepository = new CartRepo(application);
        ticketsLiveData = ticketRepository.getAllTickets();
    }

    public LiveData<List<Ticket>> getTicketsLiveData() {
        return ticketsLiveData;
    }
}
