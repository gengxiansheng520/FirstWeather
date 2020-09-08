package com.example.firstweather.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "province_table")
public class Province implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int autoId;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private int id;

    protected Province(Parcel in) {
        autoId = in.readInt();
        name = in.readString();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(autoId);
        dest.writeString(name);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Province> CREATOR = new Creator<Province>() {
        @Override
        public Province createFromParcel(Parcel in) {
            return new Province(in);
        }

        @Override
        public Province[] newArray(int size) {
            return new Province[size];
        }
    };

    @Override
    public String toString() {
        return "Province{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public Province(String name, int id) {
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
}
