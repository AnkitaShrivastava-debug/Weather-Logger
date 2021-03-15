package com.ankita.weather.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ankita.weather.persistence.ListConverter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "weatherResponse")
public class WeatherResponses implements Serializable {

    @PrimaryKey (autoGenerate = true)
    private long weatherResponsesId;

    @SerializedName("coord")
    @Expose
    @Embedded
    private Coord coord;

    @SerializedName("date")
    @Expose
    @ColumnInfo(name ="date")
    private String date;

    @SerializedName("weather")
    @Expose
    @ColumnInfo(name ="weather")
    @TypeConverters(ListConverter.class)
    private List<WeatherList> results;

    @SerializedName("base")
    @Expose
    @ColumnInfo(name ="base")
    private String base;

    @SerializedName("main")
    @Expose
    @Embedded
    private Main main;

    @SerializedName("visibility")
    @Expose
    @ColumnInfo(name ="visibility")
    private String visibility;

    @SerializedName("wind")
    @Expose
    @Embedded
    private Wind wind;

    @SerializedName("clouds")
    @Expose
    @Embedded
    private Clouds clouds;

    @SerializedName("dt")
    @Expose
    @ColumnInfo(name ="dt")
    private Long dt;

    @SerializedName("sys")
    @Expose
    @Embedded
    private Sys sys;

    @SerializedName("timezone")
    @Expose
    @ColumnInfo(name ="timezone")
    private Long timezone;

    @SerializedName("id")
    @Expose
    @ColumnInfo(name ="id")
    private int id;

    @SerializedName("name")
    @Expose
    @ColumnInfo(name ="name")
    private String name;

    @SerializedName("cod")
    @Expose
    @ColumnInfo(name ="cod")
    private long cod;

    public WeatherResponses(Coord coord, List<WeatherList> results, String base, Main main, String visibility, Wind wind, Clouds clouds, Long dt, Sys sys, Long timezone, int id, String name, long cod,String date) {
        this.weatherResponsesId = weatherResponsesId;
        this.coord = coord;
        this.results = results;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.timezone = timezone;
        this.id = id;
        this.name = name;
        this.cod = cod;
        this.date = date;
    }


    public long getWeatherResponsesId() {
        return weatherResponsesId;
    }

    public void setWeatherResponsesId(long weatherResponsesId) {
        this.weatherResponsesId = weatherResponsesId;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<WeatherList> getResults() {
        return results;
    }

    public void setResults(List<WeatherList> results) {
        this.results = results;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Long getTimezone() {
        return timezone;
    }

    public void setTimezone(Long timezone) {
        this.timezone = timezone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCod() {
        return cod;
    }

    public void setCod(long cod) {
        this.cod = cod;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /*@SerializedName("coord")
    public Coord coord;
    @SerializedName("sys")
    public Sys sys;
    @SerializedName("weather")
    public ArrayList<com.ankita.weatherlogger.WeatherResponse.Weather> weather = new ArrayList<com.ankita.weatherlogger.WeatherResponse.Weather>();
    @SerializedName("main")
    public Main main;
    @SerializedName("wind")
    public Wind wind;
    @SerializedName("rain")
    public Rain rain;
    @SerializedName("clouds")
    public Clouds clouds;
    @SerializedName("dt")
    public float dt;
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("cod")
    public float cod;*/
}
