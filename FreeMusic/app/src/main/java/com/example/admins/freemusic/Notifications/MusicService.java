package com.example.admins.freemusic.Notifications;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.admins.freemusic.ultis.MusicHandler;

/**
 * Created by Admins on 12/9/2017.
 */

public class MusicService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MusicHandler.PlayPause();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        MusicNotification.builder.setOngoing(false);
        MusicNotification.notificationManager.cancelAll();

    }
}
