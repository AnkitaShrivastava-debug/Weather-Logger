package com.ankita.weather.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ankita.weather.models.WeatherResponses;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface WeatherDao {

    @Insert(onConflict = REPLACE)
    long[] insertWeather(WeatherResponses... weather);

    @Query("DELETE FROM weatherResponse")
    void deleteAll();

    @Query("SELECT * FROM weatherResponse")
    LiveData<List<WeatherResponses>> searchWeather ();
}
