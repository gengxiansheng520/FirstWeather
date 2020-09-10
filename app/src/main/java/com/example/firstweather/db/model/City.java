package com.example.firstweather.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "city_table")
public class City implements Parcelable {
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

    protected City(Parcel in) {
        autoId = in.readInt();
        name = in.readString();
        id = in.readInt();
        province_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(autoId);
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeInt(province_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

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
