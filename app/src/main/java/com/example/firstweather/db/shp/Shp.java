package com.example.firstweather.db.shp;

import android.content.Context;
import android.content.SharedPreferences;

public class Shp {

    private static Shp INSTANCE;
    private SharedPreferences sharedPreferences;

    public static Shp getINSTANCE(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new Shp(context);
        }
        return INSTANCE;
    }

    public void saveShpString(String name,String s) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name,s);
        editor.apply();
    }
    public String getShpString(String name) {
        return sharedPreferences.getString(name,null);
    }


    private Shp(Context context) {
        sharedPreferences = context.getSharedPreferences("weather",Context.MODE_PRIVATE);
    }


}
