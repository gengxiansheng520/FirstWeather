package com.example.firstweather.db.model;

import com.google.gson.annotations.SerializedName;

public class Aqi {

    @SerializedName("city")
    public CityInfo city;

    public class CityInfo {
        public String aqi;
        public String pm25;
    }

}
