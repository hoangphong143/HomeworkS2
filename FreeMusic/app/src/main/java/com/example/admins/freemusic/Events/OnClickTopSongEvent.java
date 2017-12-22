package com.example.admins.freemusic.Events;

import com.example.admins.freemusic.Databases.TopSongModel;

/**
 * Created by Admins on 11/29/2017.
 */

public class OnClickTopSongEvent {
    public TopSongModel topSongModel;


    public OnClickTopSongEvent(TopSongModel topSongModel) {
        this.topSongModel = topSongModel;

    }


}
