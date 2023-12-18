package com.example.pancakemechanic.utils.adapter;
import androidx.room.TypeConverter;

import com.example.pancakemechanic.utils.model.PancakeCart;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class PancakeCartListConverter {
    @TypeConverter
    public static List<PancakeCart> fromString(String value) {
        Type listType = new TypeToken<List<PancakeCart>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<PancakeCart> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
