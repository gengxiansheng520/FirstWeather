package com.example.firstweather.db.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "city_table")
public class City {
    @PrimaryKey(autoGenerate = true)
    private int autoId;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private int id;
    //@Ignore
    @ColumnInfo
    private int province_id;

    public City(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getAutoId() {
        return autoId;
    }

    public void setAutoId(int autoId) {
        this.autoId = autoId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }
}
