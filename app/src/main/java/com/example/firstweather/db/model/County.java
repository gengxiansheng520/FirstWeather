package com.example.firstweather.db.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "county_table")
public class County {
    @PrimaryKey(autoGenerate = true)
    private int autoId;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private int id;
    @ColumnInfo
    private String weather_id;
    @ColumnInfo
    private int city_id;

    public County(String name, int id, String weather_id) {
        this.name = name;
        this.id = id;
        this.weather_id = weather_id;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }
}
