package com.example.firstweather.db.model;

import com.example.firstweather.db.model.Weather;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HeWeather {

    @SerializedName("HeWeather")
    public List<Weather> weatherList;

    @Override
    public String toString() {
        return "HeWeather{" +
                "weatherList=" + weatherList +
                '}';
    }
}
