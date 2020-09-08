package com.example.firstweather.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.firstweather.db.dao.CityDao;
import com.example.firstweather.db.dao.CountyDao;
import com.example.firstweather.db.dao.ProvinceDao;
import com.example.firstweather.db.model.City;
import com.example.firstweather.db.model.County;
import com.example.firstweather.db.model.Province;

@Database(entities = {Province.class, City.class, County.class}, version = 1, exportSchema =  false)
public abstract class WeatherDatabase extends RoomDatabase {
    private static WeatherDatabase INSTANCE;

    public static WeatherDatabase getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,WeatherDatabase.class,"weather_table")
                    .build();
        }
        return INSTANCE;
    }
    public abstract ProvinceDao getProvinceDao();
    public abstract CityDao getCityDao();
    public abstract CountyDao getCountyDao();
}
