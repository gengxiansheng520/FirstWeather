package com.example.firstweather.db.model;

public class Basic {

    public String location;

    public Update update;

    public class Update {
        public String loc;
        public String utc;

        @Override
        public String toString() {
            return "Update{" +
                    "loc='" + loc + '\'' +
                    ", utc='" + utc + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Basic{" +
                "location='" + location + '\'' +
                ", update=" + update +
                '}';
    }
}
