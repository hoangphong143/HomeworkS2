package com.example.admins.freemusic;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by Admins on 12/16/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
    }
}
