package com.ankita.weather.requests;

import com.ankita.weather.utils.Constants;
import com.ankita.weather.utils.LiveDataCallAdapterFactory;
import com.ankita.weather.viewmodels.WeatherApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)

                    .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                    .addConverterFactory(GsonConverterFactory.create());

    // Returns a singleton instance of Retrofit
    private static Retrofit retrofit = retrofitBuilder.build();

    // Use the retrofit instance to create another instance of the API.
    private static WeatherApi weatherApi = retrofit.create(WeatherApi.class);

    // Public method to access the API
    public static WeatherApi getWeatherApi(){
        return weatherApi;
    }
}
