package com.example.admins.freemusic.Databases;

/**
 * Created by Admins on 11/25/2017.
 */

public class TopSongModel {
    public String song;
    public String singer;
    public String smallImage;
    public String url;
    public String largeImage;
    public boolean isDownloaded;



    public TopSongModel(String song, String signer, String url ) {
        this.song = song;
        this.singer = signer;
        this.url = url;

    }

    public TopSongModel() {
    }


}



