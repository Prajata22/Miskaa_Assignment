package com.applex.miskaa_assignment;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {CountryModel.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class RoomDB extends RoomDatabase {

    private static RoomDB database;
    private static final String DATABASE_NAME ="database";

    public synchronized static RoomDB getInstance(Context context){
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract DAOInterface daoInterface();
}