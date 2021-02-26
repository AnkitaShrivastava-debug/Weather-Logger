package com.ankita.weatherlogger.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "sys")
public class Sys  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long sysId;

    @SerializedName("type")
    @Expose
    @ColumnInfo(name ="type")
    private int type;

    /*@SerializedName("id")
    @Expose
    @ColumnInfo(name ="id")
    private int id;*/

    @SerializedName("message")
    @Expose
    @ColumnInfo(name ="message")
    private double message;

    @SerializedName("country")
    @Expose
    @ColumnInfo(name ="country")
    private String country;

    @SerializedName("sunrise")
    @Expose
    @ColumnInfo(name ="sunrise")
    private Long sunrise;

    @SerializedName("sunset")
    @Expose
    @ColumnInfo(name ="sunset")
    private Long sunset;

    public Sys(int type, double message, String country, Long sunrise, Long sunset) {
        this.type = type;
        //this.id = id;
        this.message = message;
        this.country = country;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public long getSysId() {
        return sysId;
    }

    public void setSysId(long sysId) {
        this.sysId = sysId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

   /* public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getSunrise() {
        return sunrise;
    }

    public void setSunrise(Long sunrise) {
        this.sunrise = sunrise;
    }

    public Long getSunset() {
        return sunset;
    }

    public void setSunset(Long sunset) {
        this.sunset = sunset;
    }
}
