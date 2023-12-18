package com.example.pancakemechanic.utils.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pancake_table")
public class PancakeCart {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String pancakeName, pancakeCategoryName;
    private int pancakeImage;
    private double pancakePrice;

    private int quantity;
    private double totalItemPrice;


    public String getPancakeName() {
        return pancakeName;
    }

    public void setPancakeName(String pancakeName) {
        this.pancakeName = pancakeName;
    }

    public String getPancakeCategoryName() {
        return pancakeCategoryName;
    }

    public void setPancakeCategoryName(String pancakeCategoryName) {
        this.pancakeCategoryName = pancakeCategoryName;
    }

    public int getPancakeImage() {
        return pancakeImage;
    }

    public void setPancakeImage(int pancakeImage) {
        this.pancakeImage = pancakeImage;
    }

    public double getPancakePrice() {
        return pancakePrice;
    }

    public void setPancakePrice(double pancakePrice) {
        this.pancakePrice = pancakePrice;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(double totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }
}
