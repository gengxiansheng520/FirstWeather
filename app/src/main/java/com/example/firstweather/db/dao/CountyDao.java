package com.example.firstweather.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.firstweather.db.model.County;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface CountyDao {
    @Insert
    void insert(County... counties);

    @Insert
    void insert(List<County> counties);

    @Update
    void update(County... counties);

    @Delete
    void delete(County... counties);

    @Query("select * from county_table where city_id like :cityId order by autoId asc")
    LiveData<List<County>> getCounty(int cityId);

    @Query("delete from county_table")
    void deleteAll();
}
