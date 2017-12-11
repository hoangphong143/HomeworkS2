package com.example.admins.freemusic.NetWorks;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admins on 11/25/2017.
 */

public class TopSongResponse {

    public FeedJSON feed;

    public class FeedJSON {
        public List<EntryJSON> entry;
    }

    public class EntryJSON {
        @SerializedName("im:name")
        public NameJSON name;
        @SerializedName("im:image")
        public List<ImageJSON> image;
        @SerializedName("im:artist")
        public ArtisJSON artis;



    }

    public class NameJSON {
        public String label;
    }



    public class ImageJSON {
        public String label;
    }

    public class ArtisJSON {
        public String label;
    }
}