package com.example.firstweather.db.model;

public class Forecast {

    public String date;

    public Cond cond;

    public Tmp tmp;

    public class Cond {
        public String txt_d;
    }
    public class Tmp {
        public String max;
        public String min;
    }
}
