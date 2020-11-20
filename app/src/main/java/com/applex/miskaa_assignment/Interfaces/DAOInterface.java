package com.applex.miskaa_assignment.Interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.applex.miskaa_assignment.Models.CountryModel;
import java.util.ArrayList;
import java.util.List;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DAOInterface {

    @Insert(onConflict = REPLACE)
    void insert(ArrayList<CountryModel> countryModelArrayList);

    @Delete
    void reset(List<CountryModel> countryModelArrayList);

    @Query("SELECT * FROM country_details")
    List<CountryModel> getAll();
}
