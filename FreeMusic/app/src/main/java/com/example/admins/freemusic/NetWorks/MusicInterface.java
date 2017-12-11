package com.example.admins.freemusic.NetWorks;

import com.example.admins.freemusic.NetWorks.MusicTypeResponseJSON;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Admins on 11/15/2017.
 */

public interface MusicInterface {
    @GET("api")
    Call<MusicTypeResponseJSON> getMusicTypes();

    @GET("https://itunes.apple.com/us/rss/topsongs/limit=50/genre={id}/explicit=true/json")
    Call<TopSongResponse> getTopSongs(@Path("id") String id);

    @GET("https://tk-gx.herokuapp.com/api/audio")
    Call<SearchSongJSON> getSearchSong(@Query("search_terms") String search);

}




