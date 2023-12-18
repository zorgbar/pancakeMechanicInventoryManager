package com.example.pancakemechanic.Chef;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;

import com.example.pancakemechanic.repository.CartRepo;
import com.example.pancakemechanic.utils.model.Ticket;

import android.app.Application;
import java.util.List;

public class ChefViewModel extends ViewModel {
    private CartRepo ticketRepository;
    private LiveData<List<Ticket>> ticketsLiveData;

    public void init(Application application) {
        ticketRepository = new CartRepo(application);
        ticketsLiveData = ticketRepository.getAllTickets();
    }

    public LiveData<List<Ticket>> getTicketsLiveData() {
        return ticketsLiveData;
    }

    public void hideCompletedTicket(Ticket ticket) {
        ticket.setCompleted(true);
        ticketRepository.updateTicket(ticket);
    }

    public void deleteTicket(Ticket ticket) {
        ticketRepository.deleteTicket(ticket);
    }
}