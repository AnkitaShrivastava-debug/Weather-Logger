package com.ankita.weatherlogger.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "main")
public class Main  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long mainId;

    @SerializedName("temp")
    @Expose
    @ColumnInfo(name ="temp")
    private Double temp;

    @SerializedName("feels_like")
    @Expose
    @ColumnInfo(name ="feels_like")
    private Double feels_like;

    @SerializedName("temp_min")
    @Expose
    @ColumnInfo(name ="temp_min")
    private Double temp_min;

    @SerializedName("temp_max")
    @Expose
    @ColumnInfo(name ="temp_max")
    private Double temp_max;

    @SerializedName("pressure")
    @Expose
    @ColumnInfo(name ="pressure")
    private Double pressure;

    @SerializedName("humidity")
    @Expose
    @ColumnInfo(name ="humidity")
    private Double humidity;

    public Main(Double temp, Double feels_like, Double temp_min, Double temp_max, Double pressure, Double humidity) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public long getMainId() {
        return mainId;
    }

    public void setMainId(long mainId) {
        this.mainId = mainId;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(Double feels_like) {
        this.feels_like = feels_like;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
}
