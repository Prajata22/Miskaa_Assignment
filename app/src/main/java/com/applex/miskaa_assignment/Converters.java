package com.applex.miskaa_assignment;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {

    @TypeConverter
    public static ArrayList<String> fromString1(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromStringArrayList(ArrayList<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static String fromLanguageModelArrayList(ArrayList<LanguageModel> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }

    @TypeConverter
    public static ArrayList<LanguageModel> fromString2(String value) {
        Type listType = new TypeToken<ArrayList<LanguageModel>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }
}