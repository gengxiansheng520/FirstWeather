package com.example.firstweather.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.firstweather.App;
import com.example.firstweather.R;
import com.example.firstweather.databinding.ActivityWeatherBinding;
import com.example.firstweather.databinding.ForecastItemBinding;
import com.example.firstweather.db.model.County;
import com.example.firstweather.db.model.Forecast;
import com.example.firstweather.db.model.HeWeather;
import com.example.firstweather.db.model.Weather;
import com.example.firstweather.ui.chooseProvince.ChooseViewModel;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherActivity extends AppCompatActivity {

    public ActivityWeatherBinding binding;
    private ChooseViewModel chooseViewModel;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWeatherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        compositeDisposable = new CompositeDisposable();
        chooseViewModel = new ViewModelProvider(this).get(ChooseViewModel.class);
        if (App.getPic() != null) {
            String bingPic = App.getPic();
            Glide.with(this).load(bingPic).into(binding.imageView);
        } else {
            loadPic();
        }
        if (App.getWeather() != null) {
            String weatherString = App.getWeather();
            Gson gson = new Gson();
            Weather weather = (Weather) gson.fromJson(weatherString,Weather.class);
            showWeather(weather);
        } else {
            Intent intent = getIntent();
            County county = intent.getParcelableExtra("county");
            chooseViewModel.setCounty(county);
            requestWeather(county);
        }
        binding.title.navButton.setOnClickListener(view -> {
            binding.drawerLayout.openDrawer(GravityCompat.START);
        });
        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                County county1 = chooseViewModel.getCounty();
                requestWeather(county1);
            }
        });
    }

    public void requestWeather(County county) {
        binding.weatherLayout.setVisibility(View.INVISIBLE);
        Disposable disposable = chooseViewModel.getWeather(county.getWeather_id()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(s->{
                    HeWeather heWeather = s;
                    if (heWeather.weatherList != null) {
                        Weather weather = heWeather.weatherList.get(0);
                        Gson gson = new Gson();
                        String weatherString = gson.toJson(weather);
                        App.saveWeather(weatherString);
                        showWeather(weather);
                    }
                });
        compositeDisposable.add(disposable);
    }
    void showWeather(Weather weather) {
        binding.title.titleCity.setText(weather.basic.location);
        binding.title.titleUpdateTime.setText(weather.basic.update.loc.split(" ")[1]);
        binding.now.degreeText.setText(weather.now.tmp+"℃");
        binding.now.weatherInfoText.setText(weather.now.cond.txt);
        binding.forecast.forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            LayoutInflater inflater = LayoutInflater.from(this);
            View view = inflater.inflate(R.layout.forecast_item,binding.forecast.forecastLayout,false);
            ForecastItemBinding itemBinding = ForecastItemBinding.bind(view);
            itemBinding.dateText.setText(forecast.date);
            itemBinding.infoText.setText(forecast.cond.txt_d);
            itemBinding.maxText.setText(forecast.tmp.max);
            itemBinding.minText.setText(forecast.tmp.min);
            binding.forecast.forecastLayout.addView(view);
        }
        binding.aqi.aqiText.setText(weather.aqi.city.aqi);
        binding.aqi.pm25Text.setText(weather.aqi.city.pm25);
        binding.suggestion.carWashText.setText(weather.suggestion.cw.txt);
        binding.suggestion.comfortText.setText((weather.suggestion.comf.txt));
        binding.suggestion.sportText.setText(weather.suggestion.sport.txt);
        
        binding.weatherLayout.setVisibility(View.VISIBLE);
        binding.swipeRefresh.setRefreshing(false);
    }
    void loadPic() {
        //http://guolin.tech/api/bing_pic
        Disposable disposable = chooseViewModel.getPic("bing_pic").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(s->{
                    String bingPic = new String(s.bytes());
                    Glide.with(WeatherActivity.this).load(bingPic).into(binding.imageView);
                });
        compositeDisposable.add(disposable);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}