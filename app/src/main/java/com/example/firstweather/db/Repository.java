package com.example.firstweather.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.firstweather.db.dao.DAOUtil;
import com.example.firstweather.db.model.City;
import com.example.firstweather.db.model.County;
import com.example.firstweather.db.model.HeWeather;
import com.example.firstweather.db.model.Province;
import com.example.firstweather.db.shp.SHPUtil;
import com.example.firstweather.http.HttpUtil;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class Repository {
    private DAOUtil daoUtil;
    private HttpUtil httpUtil;
    private SHPUtil shpUtil;

    public Repository(Context context) {
        //shpUtil = SHPUtil.getINSTANCE(context);
        daoUtil = new DAOUtil(context);
        httpUtil = new HttpUtil();

    }

    //插入数据
    public void insertProvince(Province... provinces) {
        new Thread(()->{
            daoUtil.insertProvince(provinces);
        }).start();
    }
    public void insertProvince(List<Province> provinces) {
        new Thread(()->{
            daoUtil.insertProvince(provinces);
        }).start();
    }
    public void insertCity(City... cities) {
        new Thread(()-> {
            daoUtil.insertCity(cities);
        }).start();
    }
    public void insertCity(List<City> cities) {
        new Thread(()-> {
            daoUtil.insertCity(cities);
        }).start();
    }
    public void insertCounty(County... counties) {
        new Thread(()-> {
            daoUtil.insertCounty(counties);
        }).start();
    }
    public void insertCounty(List<County> counties) {
        new Thread(()-> {
            daoUtil.insertCounty(counties);
        }).start();
    }

    //从数据库获得数据
    public LiveData<List<Province>> getDatabaseProvince() {
        return daoUtil.getProvinceInfo();
    }
    public LiveData<List<City>> getDatabaseCity(int provinceId) {
        return daoUtil.getCityInfo(provinceId);
    }
    public LiveData<List<County>> getDatabaseCounty(int cityId) {
        return daoUtil.getCountyInfo(cityId);
    }
    //从网上获得数据
    public Observable<List<Province>> getServiceProvince(String china) {
        return httpUtil.getProvince(china);
    }
    public Observable<List<City>> getServiceCity(String provinceId) {
        //provinceId = "http://guolin.tech/api/china/6";
        return httpUtil.getCity(provinceId);
    }
    public Observable<List<County>> getServiceCounty(String provinceId, String cityId) {
        return httpUtil.getCounty(provinceId, cityId);
    }
    public Observable<HeWeather> getWeather(String weatherId) {
        return httpUtil.getWeather(weatherId);
    }
    public Observable<ResponseBody> getPic(String url) {
        return httpUtil.getPic(url);
    }
}
