package com.ankita.weather.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.ankita.weather.models.WeatherResponses;
import com.ankita.weather.persistence.WeatherDao;
import com.ankita.weather.persistence.WeatherDatabase;
import com.ankita.weather.requests.ServiceGenerator;
import com.ankita.weather.responses.ApiResponse;
import com.ankita.weather.AppExecutors;
import com.ankita.weather.utils.NetworkBoundResource;
import com.ankita.weather.utils.Resource;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class WeatherListRepository {

    private static final String TAG = "WeatherListRepository";

    private static WeatherListRepository instance;
    private WeatherDao weatherDao;
    private LiveData<List<WeatherResponses>> allResponses;

    public static WeatherListRepository getInstance(Context context){
        if(instance == null){
            instance = new WeatherListRepository(context);
        }
        return instance;
    }

    private WeatherListRepository(Context context) {
        weatherDao = WeatherDatabase.getInstance(context).getWeatherDao();
        allResponses = weatherDao.searchWeather();
    }

    public LiveData<List<WeatherResponses>> getAllResponses() {
        return allResponses;
    }

    public LiveData<Resource<List<WeatherResponses>>> getWeatherDetails(final String lat, final String lon, final String apiKey){

            return new NetworkBoundResource<List<WeatherResponses>, WeatherResponses>(AppExecutors.getInstance()){

                @Override
                protected void saveCallResult(@NonNull WeatherResponses item) {

                    if(item != null){
                        String  currentDateTimeString = DateFormat.getDateTimeInstance()
                                .format(new Date());

                        WeatherResponses weatherResponses = new WeatherResponses(item.getCoord(),item.getResults(),item.getBase(),item.getMain(),
                                item.getVisibility(),item.getWind(),item.getClouds(),item.getDt(),item.getSys(),item.getTimezone(),item.getId(),item.getName(),item.getCod(),currentDateTimeString);

                        weatherResponses.setDate(currentDateTimeString);
                        int index = 0;
                        for(long rowid: weatherDao.insertWeather(weatherResponses)){
                            if(rowid == -1){
                                Log.d(TAG, "saveCallResult: CONFLICT... This Data is already in the cache");
                                // if the data is already exists... Replace
                                weatherDao.deleteAll( );
                                weatherDao.insertWeather(weatherResponses);
                            }
                            index++;
                        }
                    }
                }


                @Override
                protected boolean shouldFetch(@Nullable List<WeatherResponses> data) {
                    return true;
                }

                @NonNull
                @Override
                protected LiveData<List<WeatherResponses>> loadFromDb() {
                    return weatherDao.searchWeather();
                }

                @NonNull
                @Override
                protected LiveData<ApiResponse<WeatherResponses>> createCall() {
                    return ServiceGenerator.getWeatherApi()
                            .getCurrentWeatherData(
                                    lat,
                                    lon,
                                    apiKey
                            );
                }
            }.getAsLiveData();
        }


}
