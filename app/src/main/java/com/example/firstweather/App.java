package com.example.firstweather;

import android.app.Application;
import android.content.Context;

import com.example.firstweather.db.Repository;
import com.example.firstweather.db.model.County;
import com.example.firstweather.db.shp.SHPUtil;
import com.google.gson.Gson;

public class App extends Application {
    private static Context context;
    private static Repository repository;
    private static SHPUtil shpUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        repository = new Repository(context);
        shpUtil = new SHPUtil(context);
    }
    public static void savePic(String pic) {
        shpUtil.savePic(pic);
    }
    public static void saveWeather(String weather) {
        shpUtil.saveWeather(weather);
    }
    public static void saveWeatherId(String weatherId) {
        shpUtil.saveWeatherId(weatherId);
    }
    public static void saveCounty(County county) {
        Gson gson = new Gson();
        String countyString = gson.toJson(county);
        shpUtil.saveCounty(countyString);
    }

    public static String getPic() {
        return shpUtil.getPic();
    }
    public static String getWeatherId() {
        return shpUtil.getWeatherId();
    }
    public static String getWeather() { return shpUtil.getWeather(); }
    public static County getCounty() {
        Gson gson = new Gson();
       return gson.fromJson(shpUtil.getCounty(),County.class);
    }
    public static Context getContext() {
        return  context;
    }
    public static Repository getRepository() {
        return repository;
    }


}
