package com.applex.miskaa_assignment.Utilities;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.TypeConverters;

import com.applex.miskaa_assignment.Interfaces.DAOInterface;
import com.applex.miskaa_assignment.Models.CountryModel;

@Database(entities = {CountryModel.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RoomDatabase extends androidx.room.RoomDatabase {

    private static RoomDatabase database;
    private static final String DATABASE_NAME ="database";

    public synchronized static RoomDatabase getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract DAOInterface daoInterface();
}