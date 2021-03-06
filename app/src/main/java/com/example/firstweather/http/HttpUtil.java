package com.example.firstweather.http;


import com.example.firstweather.db.model.City;
import com.example.firstweather.db.model.County;
import com.example.firstweather.db.model.HeWeather;
import com.example.firstweather.db.model.Province;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class HttpUtil {
    private Http http;
    //private MyService myService;
    public  HttpUtil() {
        http = Http.getINSTANCE();
    }

    public Observable<List<Province>> getProvince(String china) {
        return http.getProvince(china);
    }
    public Observable<List<City>> getCity(String province_id) {
        return http.getCity(province_id);
    }
    public Observable<List<County>> getCounty(String province_id, String city_id) {
        return http.getCounty(province_id, city_id);
    }
    public Observable<HeWeather> getWeather(String weatherId) {
        return http.getWeather(weatherId);
    }
    public Observable<ResponseBody> getPic(String url) {
        return http.getPic(url);
    }
}
