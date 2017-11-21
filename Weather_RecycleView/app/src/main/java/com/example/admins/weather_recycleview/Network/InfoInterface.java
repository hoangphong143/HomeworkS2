package com.example.admins.weather_recycleview.Network;

import com.example.admins.weather_recycleview.Network.InfoJSON;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Admins on 11/20/2017.
 */

public interface InfoInterface {
    @GET("data/2.5/forecast/daily")
    Call<InfoJSON> getInfo(
            @Query("q") String cityName,
            @Query("appid") String appid);

}