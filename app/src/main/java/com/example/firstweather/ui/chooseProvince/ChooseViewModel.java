package com.example.firstweather.ui.chooseProvince;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.firstweather.db.Repository;
import com.example.firstweather.db.model.City;
import com.example.firstweather.db.model.County;
import com.example.firstweather.db.model.Province;

import java.util.List;

import io.reactivex.Observable;

public class ChooseViewModel extends AndroidViewModel {

    private Repository repository;
    private Province province;
    private City city;
    private County county;

    public ChooseViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application.getApplicationContext());
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    //从数据库获得数据
    public LiveData<List<Province>> getDatabaseProvince() {
        return repository.getDatabaseProvince();
    }
    public LiveData<List<City>> getDatabaseCity(int provinceId) {
        return repository.getDatabaseCity(provinceId);
    }
    public LiveData<List<County>> getDatabaseCounty(int cityId) {
        return repository.getDatabaseCounty(cityId);
    }
    //从网上获得数据
    public Observable<List<Province>> getServiceProvince(String china) {
        return repository.getServiceProvince(china);
    }
    public Observable<List<City>> getServiceCity(String provinceId) {
        return repository.getServiceCity(provinceId);
    }
    public Observable<List<County>> getServiceCounty(String provinceId, String cityId) {
        return repository.getServiceCounty(provinceId, cityId);
    }

    //向数据库插入数据
    public void insertProvince(List<Province> provinces) {
        new Thread(()->{
            repository.insertProvince(provinces);
        }).start();
    }

    public void insertCity(List<City> cities) {
        new Thread(()-> {
            repository.insertCity(cities);
        }).start();
    }

    public void insertCounty(List<County> counties) {
        new Thread(()-> {
            repository.insertCounty(counties);
        }).start();
    }

}
