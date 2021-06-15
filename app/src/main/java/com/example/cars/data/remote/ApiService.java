package com.example.cars.data.remote;

import com.example.cars.data.model.CarsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Esraa Rashad on 6/15/2021.
 * email : esraarashad56@yahoo.com
 */
public interface ApiService {
    @GET("/api/v1/cars?")
    Call<CarsModel> getCars(@Query("page") String page);
}
