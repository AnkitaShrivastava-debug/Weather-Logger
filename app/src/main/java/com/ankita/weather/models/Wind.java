package com.ankita.weather.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "wind")
public class Wind  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long windId;

    @SerializedName("speed")
    @Expose
    @ColumnInfo(name ="speed")
    private Double speed;

    @SerializedName("deg")
    @Expose
    @ColumnInfo(name ="deg")
    private Double deg;

    public Wind(Double speed, Double deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public long getWindId() {
        return windId;
    }

    public void setWindId(long windId) {
        this.windId = windId;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }
}
