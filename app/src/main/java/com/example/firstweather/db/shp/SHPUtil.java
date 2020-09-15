package com.example.firstweather.db.shp;

import android.content.Context;
import android.content.SharedPreferences;

public class SHPUtil {
    private Shp shp;

    public SHPUtil(Context context) {
        shp = Shp.getINSTANCE(context);

    }
    public void savePic(String pic) {
        shp.saveShpString("pic",pic);
    }
    public void saveWeatherId(String weatherId) {
        shp.saveShpString("weatherId",weatherId);
    }
    public void saveCounty(String county) {
        shp.saveShpString("county",county);
    }
    public void saveWeather(String weather) {
        shp.saveShpString("weather",weather);
    }
    public String getPic() {
        return shp.getShpString("pic");
    }
    public String getWeatherId() {
        return shp.getShpString("weatherId");
    }
    public String getWeather() {
        return shp.getShpString("weather");
    }
    public String getCounty() {
        return shp.getShpString("county");
    }



}
