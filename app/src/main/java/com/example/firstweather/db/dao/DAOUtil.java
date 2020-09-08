package com.example.firstweather.db.dao;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.firstweather.db.WeatherDatabase;
import com.example.firstweather.db.model.City;
import com.example.firstweather.db.model.County;
import com.example.firstweather.db.model.Province;

import java.util.List;

import io.reactivex.Observable;

public class DAOUtil {

    //private DAOUtil INSTANCE;
    private static ProvinceDao provinceDao;
    private static CityDao cityDao;
    private static CountyDao countyDao;

//    public DAOUtil getINSTANCE(Context context) {
//        if (INSTANCE == null) {
//            INSTANCE = new DAOUtil(context);
//        }
//        return INSTANCE;
//    }

    public DAOUtil(Context context) {
        provinceDao = WeatherDatabase.getINSTANCE(context).getProvinceDao();
        cityDao = WeatherDatabase.getINSTANCE(context).getCityDao();
        countyDao = WeatherDatabase.getINSTANCE(context).getCountyDao();
    }

    public  void insertProvince(Province... provinces) {
        provinceDao.insert(provinces);
    }
    public  void insertProvince(List<Province> provinces) {
        provinceDao.insert(provinces);
    }
    public  void insertCity(City... cities) {
        cityDao.insert(cities);
    }
    public  void insertCity(List<City> cities) {
        cityDao.insert(cities);
    }
    public  void insertCounty(County... counties) {
        countyDao.insert(counties);
    }
    public  void insertCounty(List<County> counties) {
        countyDao.insert(counties);
    }

    public LiveData<List<Province>> getProvinceInfo() {
        return provinceDao.getProvince();
    }
    public LiveData<List<City>> getCityInfo(int provinceId) {
        return cityDao.getCity(provinceId);
    }
    public LiveData<List<County>> getCountyInfo(int cityId) {
        return countyDao.getCounty(cityId);
    }

}
