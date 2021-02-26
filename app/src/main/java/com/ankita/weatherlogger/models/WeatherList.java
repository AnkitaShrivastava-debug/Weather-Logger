package com.ankita.weatherlogger.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "weatherList")
public class WeatherList  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long weatherListId;

    /*@SerializedName("id")
    @Expose
    @ColumnInfo(name ="id")
    private Long id;*/

    @SerializedName("main")
    @Expose
    @ColumnInfo(name ="main")
    private String main;

    @SerializedName("description")
    @Expose
    @ColumnInfo(name ="description")
    private String description;

    @SerializedName("icon")
    @Expose
    @ColumnInfo(name ="icon")
    private String icon;

    @Ignore
    public WeatherList() {
    }

    public WeatherList( String main, String description, String icon) {
       // this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }

    public long getWeatherListId() {
        return weatherListId;
    }

    public void setWeatherListId(long weatherListId) {
        this.weatherListId = weatherListId;
    }

   /* public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "WeatherList{" +
                "weatherListId=" + weatherListId +

                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
