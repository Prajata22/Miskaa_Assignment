package com.applex.miskaa_assignment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    String BASE_URL = "https://restcountries.eu/rest/v2/region/";

    @GET("asia")
    Call<ArrayList<CountryModel>> getCountryList();
}