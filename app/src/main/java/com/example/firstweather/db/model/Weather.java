package com.example.firstweather.db.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    public Basic basic;

    public String status;

    public Now now;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

    public Aqi aqi;

    public Suggestion suggestion;

    @Override
    public String toString() {
        return "Weather{" +
                "basic=" + basic +
                ", status='" + status + '\'' +
                ", now=" + now +
                ", forecastList=" + forecastList +
                ", aqi=" + aqi +
                ", suggestion=" + suggestion +
                '}';
    }
}
