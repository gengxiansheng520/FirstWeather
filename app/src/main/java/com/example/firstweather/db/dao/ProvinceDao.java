package com.example.firstweather.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.firstweather.db.model.Province;

import java.util.List;

import io.reactivex.Observable;

@Dao
public interface ProvinceDao {

    @Insert
    void insert(Province... provinces);

    @Insert
    void insert(List<Province> provinces);

    @Update
    void update(Province... provinces);

    @Delete
    void delete(Province... provinces);

    @Query("select * from province_table order by autoId asc")
    LiveData<List<Province>> getProvince();

    @Query("delete from province_table")
    void deleteAll();

}
