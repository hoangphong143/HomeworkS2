package com.example.admins.weather_recycleview.Network;

import java.util.List;

/**
 * Created by Admins on 11/20/2017.
 */

public class InfoJSON {
    public List<ObjectJSON>list;
    public class ObjectJSON{
        public List<WeatherInfo> weather;

        public class WeatherInfo{
            public String main;
            public String description;
        }
    }
}
