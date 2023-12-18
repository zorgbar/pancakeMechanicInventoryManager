package com.example.pancakemechanic.utils.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.pancakemechanic.utils.adapter.PancakeCartListConverter;

import java.util.List;

@Entity(tableName = "tickets")
@TypeConverters(PancakeCartListConverter.class)
public class Ticket {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private List<PancakeCart> pancakeCarts;
    private boolean completed; // New field for ticket completion status

    public Ticket(List<PancakeCart> pancakeCarts) {
        this.pancakeCarts = pancakeCarts;
        this.completed = false; // Initialize completion status to false by default
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<PancakeCart> getPancakeCarts() {
        return pancakeCarts;
    }

    public void setPancakeCarts(List<PancakeCart> pancakeCarts) {
        this.pancakeCarts = pancakeCarts;
    }

    public String getTitle() {
        // Implement the logic to return the ticket title
        return "Ticket #" + getId();
    }

    public double getTotalItemPrice() {
        return getId();
    }


    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
