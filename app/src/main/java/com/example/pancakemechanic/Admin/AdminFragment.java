package com.example.pancakemechanic.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pancakemechanic.R;
import com.example.pancakemechanic.utils.model.PancakeCart;
import com.example.pancakemechanic.utils.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class AdminFragment extends Fragment {

    private AdminViewModel adminViewModel;
    private AdminFragment.TicketAdapter ticketAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminViewModel = new ViewModelProvider(this).get(AdminViewModel.class);
        adminViewModel.init(requireActivity().getApplication());
        ticketAdapter = new AdminFragment.TicketAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.orderRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(ticketAdapter);

        adminViewModel.getTicketsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Ticket>>() {
            @Override
            public void onChanged(List<Ticket> tickets) {
                // Update the ticket list in the adapter
                ticketAdapter.setTickets(tickets);
            }
        });

        return rootView;
    }

    private class TicketAdapter extends RecyclerView.Adapter<AdminFragment.TicketAdapter.TicketViewHolder> {
        private List<Ticket> tickets = new ArrayList<>();

        public void setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public AdminFragment.TicketAdapter.TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
            return new AdminFragment.TicketAdapter.TicketViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AdminFragment.TicketAdapter.TicketViewHolder holder, int position) {
            Ticket ticket = tickets.get(position);
            // Bind ticket data to the ViewHolder
            holder.bind(ticket);
        }

        @Override
        public int getItemCount() {
            return tickets.size();
        }

        public class TicketViewHolder extends RecyclerView.ViewHolder {
            private TextView ticketTitleTextView;
            private TextView cartDetailsTextView;
            private TextView cartPriceTextView;

            public TicketViewHolder(@NonNull View itemView) {
                super(itemView);
                ticketTitleTextView = itemView.findViewById(R.id.ticketTitleTextView);
                cartDetailsTextView = itemView.findViewById(R.id.cartDetailsTextView);
                cartPriceTextView = itemView.findViewById(R.id.cartPriceTextView);

            }

            public void bind(Ticket ticket) {
                // Bind ticket data to the views
                ticketTitleTextView.setText(ticket.getTitle());

                StringBuilder cartDetails = new StringBuilder();
                StringBuilder cartPrice = new StringBuilder();
                List<PancakeCart> pancakeCarts = ticket.getPancakeCarts();
                for (PancakeCart pancakeCart : pancakeCarts) {
                    cartDetails.append(pancakeCart.getQuantity())
                            .append(" ")
                            .append(pancakeCart.getPancakeName())
                            .append("\n")
                            .append(pancakeCart.getTotalItemPrice())
                            .append("\n");
                }
                cartDetailsTextView.setText(cartDetails.toString());
                cartPriceTextView.setText(cartPrice.toString());
            }
        }}}

