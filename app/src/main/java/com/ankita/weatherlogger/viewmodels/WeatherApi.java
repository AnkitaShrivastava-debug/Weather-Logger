package com.ankita.weatherlogger.viewmodels;

import androidx.lifecycle.LiveData;

import com.ankita.weatherlogger.models.WeatherResponses;
import com.ankita.weatherlogger.responses.ApiResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    @GET("data/2.5/weather?")
    LiveData<ApiResponse<WeatherResponses>> getCurrentWeatherData(
            @Query("lat") String lat,
            @Query("lon") String lon,
            @Query("APPID") String app_id
    );



}
