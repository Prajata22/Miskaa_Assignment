package com.applex.miskaa_assignment;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "country_details")
public class CountryModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int ID;

    private String name, capital, flag, region, subregion;
    private ArrayList<String> borders;
    private ArrayList<LanguageModel> languages;
    private long population;

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public ArrayList<String> getBorders() {
        return borders;
    }

    public void setBorders(ArrayList<String> borders) {
        this.borders = borders;
    }

    public ArrayList<LanguageModel> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<LanguageModel> languages) {
        this.languages = languages;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}