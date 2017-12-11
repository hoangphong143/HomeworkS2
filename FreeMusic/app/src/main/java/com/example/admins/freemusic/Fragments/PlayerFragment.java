package com.example.admins.freemusic.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.admins.freemusic.Databases.TopSongModel;
import com.example.admins.freemusic.Events.OnClickTopSongEvent;
import com.example.admins.freemusic.R;
import com.example.admins.freemusic.ultis.DownloadHandler;
import com.example.admins.freemusic.ultis.MusicHandler;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;


public class PlayerFragment extends Fragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_down)
    ImageView ivDown;
    @BindView(R.id.tv_song_name)
    TextView tvSongName;
    @BindView(R.id.tv_singer_name)
    TextView tvSingerName;
    @BindView(R.id.iv_song)
    ImageView ivSong;
    @BindView(R.id.tv_current_time)
    TextView tvCurrent;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.iv_previous)
    ImageView ivPre;
    @BindView(R.id.iv_next)
    ImageView ivNext;
    @BindView(R.id.fb_play)
    FloatingActionButton fbPlay;
    @BindView(R.id.sb_song)
    SeekBar sb;
    public PlayerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_player, container,
                false);


        setupUI(view);
        EventBus.getDefault().register(this);

        return view;
    }

    private void setupUI(View view) {

        ButterKnife.bind(this, view);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        fbPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicHandler.PlayPause();
            }
        });

    }

    @Subscribe(sticky = true)
    public void onMiniPlayerClicked(OnClickTopSongEvent onClickTopSongEvent){
        final TopSongModel topSongModel= onClickTopSongEvent.topSongModel;

        tvSongName.setText(topSongModel.song);
        tvSingerName.setText(topSongModel.signer);
        Picasso.with(getContext()).load(topSongModel.largeImage).transform(new CropCircleTransformation()).into(ivSong);


        MusicHandler.updateUIRealTime(sb, fbPlay, ivSong, tvCurrent, tvDuration);
        ivDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadHandler.DownloadSong(getContext(),topSongModel );
            }
        });

    }
}
