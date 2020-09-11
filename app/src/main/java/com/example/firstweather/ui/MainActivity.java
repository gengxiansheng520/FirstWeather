package com.example.firstweather.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toolbar;

import com.example.firstweather.App;
import com.example.firstweather.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //若有缓存,直接跳转到天气界面
        if (App.getWeather() != null) {
            Intent intent = new Intent(this,WeatherActivity.class);
            startActivity(intent);
        }



    }
}