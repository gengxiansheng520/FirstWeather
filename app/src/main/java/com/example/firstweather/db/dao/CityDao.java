package com.example.firstweather.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.firstweather.db.model.City;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface CityDao {
    @Insert
    void insert(City... cities);

    @Insert
    void insert(List<City> cities);

    @Update
    void update(City... cities);

    @Delete
    void delete(City... cities);

    @Query("select * from city_table where province_id like :provinceId order by autoId asc")
    LiveData<List<City>> getCity(int provinceId);

    @Query("delete from city_table")
    void deleteAll();
}
