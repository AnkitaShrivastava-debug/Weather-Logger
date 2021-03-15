package com.ankita.weather.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "coord")
public class Coord implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long coordId;

    @SerializedName("lon")
    @Expose
    @ColumnInfo(name ="lon")
    private Double lon;

    @SerializedName("lat")
    @Expose
    @ColumnInfo(name ="lat")
    private Double lat;

    @Ignore
    public Coord() {
    }

    public Coord( Double lon, Double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    public long getCoordId() {
        return coordId;
    }

    public void setCoordId(long coordId) {
        this.coordId = coordId;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
