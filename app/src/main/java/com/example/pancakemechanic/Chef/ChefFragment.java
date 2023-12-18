package com.example.pancakemechanic.Chef;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pancakemechanic.R;
import com.example.pancakemechanic.utils.model.Ticket;
import com.example.pancakemechanic.utils.model.PancakeCart;
import com.example.pancakemechanic.views.LoginActivity;

import java.util.List;
import java.util.ArrayList;

public class ChefFragment extends Fragment {
    private ChefViewModel chefViewModel;
    private TicketAdapter ticketAdapter;

    private ImageView logoutImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chefViewModel = new ViewModelProvider(this).get(ChefViewModel.class);
        chefViewModel.init(requireActivity().getApplication());
        ticketAdapter = new TicketAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chef, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.ticketRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(ticketAdapter);

        logoutImageView = rootView.findViewById(R.id.logoutIv);
        logoutImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChefFragment.this.getActivity(), LoginActivity.class);
            }
        });

        chefViewModel.getTicketsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Ticket>>() {
            @Override
            public void onChanged(List<Ticket> tickets) {
                // Update the ticket list in the adapter
                ticketAdapter.setTickets(tickets);
            }
        });

        return rootView;
    }

    private class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
        private List<Ticket> tickets = new ArrayList<>();

        public void setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_item, parent, false);
            return new TicketViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
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
            private Button deleteTicketButton;

            public TicketViewHolder(@NonNull View itemView) {
                super(itemView);
                ticketTitleTextView = itemView.findViewById(R.id.ticketTitleTextView);
                cartDetailsTextView = itemView.findViewById(R.id.cartDetailsTextView);
                deleteTicketButton = itemView.findViewById(R.id.hideTicketButton);

                deleteTicketButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Ticket ticket = tickets.get(position);
                            chefViewModel.deleteTicket(ticket);
                        }
                    }
                });
            }

            public void bind(Ticket ticket) {
                // Bind ticket data to the views
                ticketTitleTextView.setText(ticket.getTitle());

                StringBuilder cartDetails = new StringBuilder();
                List<PancakeCart> pancakeCarts = ticket.getPancakeCarts();
                for (PancakeCart pancakeCart : pancakeCarts) {
                    cartDetails.append(pancakeCart.getQuantity())
                            .append(" ")
                            .append(pancakeCart.getPancakeName())
                            .append("\n");
                }
                cartDetailsTextView.setText(cartDetails.toString());
            }
        }}}
