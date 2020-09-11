package com.example.firstweather.http;

import com.example.firstweather.db.model.City;
import com.example.firstweather.db.model.County;
import com.example.firstweather.db.model.HeWeather;
import com.example.firstweather.db.model.Province;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class Http {
    private static Http INSTANCE;
    private Retrofit retrofit;
    private MyService myService;

    public static Http getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Http();
        }

        return INSTANCE;
    }
    private Http() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://guolin.tech/api/")
                .build();
        myService = retrofit.create(MyService.class);
    }

    public Observable<List<Province>> getProvince(String china) {
        return myService.getProvince(china);
    }
    public Observable<List<City>> getCity(String province_id) {
        return myService.getCity(province_id);
    }
    public Observable<List<County>> getCounty(String province_id, String city_id) {
        return myService.getCounty(province_id, city_id);
    }
    public Observable<HeWeather> getWeather(String weatherId) {
        return myService.getWeather(weatherId);
    }
    public Observable<ResponseBody> getPic(String url) {
        return myService.getPic(url);
    }


}
