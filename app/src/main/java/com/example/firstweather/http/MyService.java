package com.example.firstweather.http;

import com.example.firstweather.db.model.City;
import com.example.firstweather.db.model.County;
import com.example.firstweather.db.model.Province;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

//baseUrl: http://guolin.tech/api/china/12
public interface MyService {
    @GET("{china}")
    Observable<List<Province>> getProvince(@Path("china") String china);
    @GET("china/{province_id}")
    Observable<List<City>> getCity(@Path("province_id") String province_id);
//    @GET()
//    Observable<List<City>> getCity(@Url String url);
    @GET("china/{province_id}/{city_id}")
    Observable<List<County>> getCounty(@Path("province_id") String province_id, @Path("city_id") String city_id);
}
