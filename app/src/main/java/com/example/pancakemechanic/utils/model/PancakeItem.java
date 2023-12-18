package com.example.pancakemechanic.utils.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PancakeItem implements Parcelable {

    private String pancakeName, pancakeCategoryName;
    private int pancakeImage;
    private double pancakePrice;

    public PancakeItem(String pancakeName, String pancakeCategoryName, int pancakeImage, double pancakePrice) {
        this.pancakeName = pancakeName;
        this.pancakeCategoryName = pancakeCategoryName;
        this.pancakeImage = pancakeImage;
        this.pancakePrice = pancakePrice;
    }

    protected PancakeItem(Parcel in) {
        pancakeName = in.readString();
        pancakeCategoryName = in.readString();
        pancakeImage = in.readInt();
        pancakePrice = in.readDouble();
    }

    public static final Creator<PancakeItem> CREATOR = new Creator<PancakeItem>() {
        @Override
        public PancakeItem createFromParcel(Parcel in) {
            return new PancakeItem(in);
        }

        @Override
        public PancakeItem[] newArray(int size) {
            return new PancakeItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pancakeName);
        parcel.writeString(pancakeCategoryName);
        parcel.writeInt(pancakeImage);
        parcel.writeDouble(pancakePrice);
    }
}
