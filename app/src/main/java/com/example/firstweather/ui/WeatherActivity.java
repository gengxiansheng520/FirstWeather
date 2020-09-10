package com.example.firstweather.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.firstweather.R;
import com.example.firstweather.databinding.ActivityWeatherBinding;
import com.example.firstweather.db.model.County;
import com.example.firstweather.db.model.Weather;

public class WeatherActivity extends AppCompatActivity {

    public ActivityWeatherBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
//        County county = intent.getParcelableExtra("county");
//        requestWeather(county);
    }

    public void requestWeather(County county) {
        //showWeather(weather);
    }
    void showWeather(Weather weather) {

    }
}