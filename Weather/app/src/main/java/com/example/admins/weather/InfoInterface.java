package com.example.admins.weather;

import android.icu.text.IDNA;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Admins on 11/16/2017.
 */

public interface InfoInterface {
    @GET("data/2.5/weather")
    Call<InfoJSON> getInfo(
            @Query("q") String cityName,
            @Query("appid") String appid);

}
