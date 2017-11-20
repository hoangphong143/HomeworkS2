package com.example.admins.freemusic.NetWorks;

import com.example.admins.freemusic.NetWorks.MusicTypeResponseJSON;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Admins on 11/15/2017.
 */

public interface MusicTypesInterface {
    @GET("api")
    Call<MusicTypeResponseJSON> getMusicTypes();

}
