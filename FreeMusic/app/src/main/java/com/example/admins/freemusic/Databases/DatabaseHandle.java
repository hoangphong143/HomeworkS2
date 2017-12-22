package com.example.admins.freemusic.Databases;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Admins on 12/16/2017.
 */

public class DatabaseHandle {
    private static Realm realm = Realm.getDefaultInstance();

    public static void addMusicType(MusicTypeModel musicTypeModel) {
        realm.beginTransaction();
        realm.copyToRealm(musicTypeModel);
        realm.commitTransaction();
    }

    public static List<MusicTypeModel> getMusicType() {
        return realm.where(MusicTypeModel.class).findAll();
    }

    public static void updateFavourite(MusicTypeModel musicTypeModel) {
        realm.beginTransaction();
        musicTypeModel.isFavourite = !musicTypeModel.isFavourite;
        realm.commitTransaction();
    }
    public static List<MusicTypeModel> getFavouriteSong(){
        return realm.where(MusicTypeModel.class).equalTo("isFavourite", true).findAll();
    }
}
