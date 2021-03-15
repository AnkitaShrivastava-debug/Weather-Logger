package com.ankita.weather.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "clouds")
public class Clouds  implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long cloudsId;

    @SerializedName("all")
    @Expose
    @ColumnInfo(name ="all")
    private Double all;

    public Clouds(Double all) {
        this.all = all;
    }

    public long getCloudsId() {
        return cloudsId;
    }

    public void setCloudsId(long cloudsId) {
        this.cloudsId = cloudsId;
    }

    public Double getAll() {
        return all;
    }

    public void setAll(Double all) {
        this.all = all;
    }
}
