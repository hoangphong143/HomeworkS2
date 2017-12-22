package com.example.admins.freemusic.Notifications;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.admins.freemusic.Activity.MainActivity;
import com.example.admins.freemusic.Databases.TopSongModel;
import com.example.admins.freemusic.R;
import com.example.admins.freemusic.ultis.MusicHandler;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Admins on 12/9/2017.
 */

public class MusicNotification {
    public static RemoteViews remoteViews;
    public static android.support.v4.app.NotificationCompat.Builder builder;
    public static NotificationManager notificationManager;
    public static void setupNOtification(Context context, TopSongModel topSongModel) {

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notificationayout);
        remoteViews.setTextViewText(R.id.tv_song, topSongModel.song);
        remoteViews.setTextViewText(R.id.tv_singer, topSongModel.singer);
        remoteViews.setImageViewResource(R.id.iv_play, R.drawable.ic_pause_black_24dp);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        builder = new android.support.v4.app.NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContent(remoteViews)
                .setContentIntent(pendingIntent)
                .setOngoing(true);


        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (topSongModel.isDownloaded) {
            
        } else {
            Picasso.with(context).load(topSongModel.smallImage).transform(new CropCircleTransformation()).into(remoteViews, R.id.iv_song, NOTIFICATION_ID, builder.build());
        }

//        Picasso.with(context).load(R.drawable.ic_pause_black_24dp).into(remoteViews, R.id.iv_play, NOTIFICATION_ID, builder.build());


        setOnCLickPlayPause(context);

        notificationManager.notify(NOTIFICATION_ID, builder.build());


    }

    public static final int NOTIFICATION_ID = 1;

    public static void updateNotification() {
        if (MusicHandler.hybridMediaPlayer.isPlaying()) {
            remoteViews.setImageViewResource(R.id.iv_play, R.drawable.ic_pause_black_24dp);
            builder.setOngoing(false);

        } else {
            remoteViews.setImageViewResource(R.id.iv_play, R.drawable.ic_play_arrow_black_24dp);
            builder.setOngoing(true);
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    private static void setOnCLickPlayPause(Context context) {
        Intent intent = new Intent(context, MusicService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        remoteViews.setOnClickPendingIntent(R.id.iv_play, pendingIntent);

    }
}
