package com.example.admins.freemusic.ultis;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admins.freemusic.Databases.TopSongModel;
import com.example.admins.freemusic.Fragments.PlayerFragment;
import com.example.admins.freemusic.NetWorks.MusicInterface;
import com.example.admins.freemusic.NetWorks.RetrofitInstance;
import com.example.admins.freemusic.NetWorks.SearchSongJSON;
import com.example.admins.freemusic.Notifications.MusicNotification;
import com.example.admins.freemusic.R;

import hybridmediaplayer.HybridMediaPlayer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Admins on 11/29/2017.
 */

public class MusicHandler {
    private static final String TAG =MusicHandler.class.toString() ;
    public static HybridMediaPlayer hybridMediaPlayer;
    private static boolean keepUpdating= true;

    public static void getSearchSong(final TopSongModel topSongModel, final Context context) {
        MusicInterface musicInterface = RetrofitInstance.getInstance().create(MusicInterface.class);
        musicInterface.getSearchSong(topSongModel.song + " " + topSongModel.singer)

                .enqueue(new Callback<SearchSongJSON>() {
                    @Override
                    public void onResponse(Call<SearchSongJSON> call, Response<SearchSongJSON> response) {
                        if (response.code() == 200) {
                            topSongModel.url = response.body().data.url;
                            topSongModel.largeImage = response.body().data.thumbnail;

                            playMusic(context, topSongModel);

                        } else if (response.code() == 500) {
                            Toast.makeText(context, " Loi", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<SearchSongJSON> call, Throwable t) {
                        Toast.makeText(context, " Mat mang ", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    public static void playMusic(Context context, final TopSongModel topSongModel) {

        if (hybridMediaPlayer != null) {
            hybridMediaPlayer.pause();
            hybridMediaPlayer.release();

        }
        Log.d(TAG, "playMusic: "+topSongModel.url);
        hybridMediaPlayer = HybridMediaPlayer.getInstance(context);
        hybridMediaPlayer.setDataSource(topSongModel.url);

        hybridMediaPlayer.prepare();
        hybridMediaPlayer.setOnPreparedListener(new HybridMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(HybridMediaPlayer hybridMediaPlayer) {
                hybridMediaPlayer.play();
            }
        });

        hybridMediaPlayer.setOnCompletionListener(new HybridMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(HybridMediaPlayer hybridMediaPlayer) {
                PlayerFragment.setData(topSongModel,1);
            }
        });

        MusicNotification.setupNOtification(context, topSongModel);
    }

    public static void PlayPause() {

        if (hybridMediaPlayer.isPlaying()) {
            hybridMediaPlayer.pause();
        } else {
            hybridMediaPlayer.play();

        }
        MusicNotification.updateNotification();

    }
    public static void updateUIRealTime(final SeekBar seekBar,
                                        final FloatingActionButton floatingActionButton,
                                        final ImageView imageView, final TextView tvCurrent,
                                        final  TextView tvDuration) {
        final android.os.Handler handler= new android.os.Handler();
        Runnable runnable= new Runnable() {
            @Override
            public void run() {

                if( keepUpdating&& hybridMediaPlayer!=null){

                    seekBar.setMax(hybridMediaPlayer.getDuration());
                    seekBar.setProgress(hybridMediaPlayer.getCurrentPosition());

                    if( hybridMediaPlayer.isPlaying()){
                        floatingActionButton.setImageResource(R.drawable.ic_pause_black_24dp);
                    } else {
                        floatingActionButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    }
                    Ultis.roteImage(imageView, hybridMediaPlayer.isPlaying());

                    if(tvCurrent!=null){
                        tvCurrent.setText(Ultis.convertTime(hybridMediaPlayer.getCurrentPosition()));
                        tvDuration.setText(Ultis.convertTime(hybridMediaPlayer.getDuration()));
                    }
                }
                handler.postDelayed(this, 100);

            }
        };
        runnable.run();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                keepUpdating= false;

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                hybridMediaPlayer.seekTo(seekBar.getProgress());
                keepUpdating=true;

            }
        });

    }

}
