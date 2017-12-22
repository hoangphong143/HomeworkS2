package com.example.admins.freemusic.Databases;

import io.realm.RealmObject;

/**
 * Created by Admins on 11/20/2017.
 */

public class MusicTypeModel extends RealmObject {
    public String key;
    public String id;
    public int imageID;

    public boolean isFavourite;
}
