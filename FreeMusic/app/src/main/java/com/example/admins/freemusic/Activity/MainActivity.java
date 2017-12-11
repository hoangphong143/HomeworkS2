package com.example.admins.freemusic.Activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.admins.freemusic.Adapter.ViewPagerAdapter;
import com.example.admins.freemusic.Databases.TopSongModel;
import com.example.admins.freemusic.Events.OnClickMusicTypeEvent;
import com.example.admins.freemusic.Events.OnClickTopSongEvent;
import com.example.admins.freemusic.Fragments.PlayerFragment;
import com.example.admins.freemusic.NetWorks.MusicInterface;
import com.example.admins.freemusic.R;
import com.example.admins.freemusic.ultis.MusicHandler;
import com.example.admins.freemusic.ultis.Ultis;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.toString();
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.rl_mini)
    RelativeLayout rlMini;
    @BindView(R.id.sb_mini)
    SeekBar sbMini;
    @BindView(R.id.iv_song)
    ImageView ivSong;
    @BindView(R.id.tv_singer)
    TextView tvSinger;
    @BindView(R.id.tv_song)
    TextView tvSong;
    @BindView(R.id.fb_mini)
    FloatingActionButton fbPlay;
    @BindView(R.id.rl_main1)
    RelativeLayout rlMain1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setupUI();
        EventBus.getDefault().register(this);


    }

    @Subscribe(sticky = true)
    public void OnReceivedTopSong(OnClickTopSongEvent onClickTopSongEvent) {
        TopSongModel topSongModel = onClickTopSongEvent.topSongModel;
        Log.d(TAG, "OnReceivedTopSong: " + topSongModel.song);
        rlMini.setVisibility(View.VISIBLE);

        tvSinger.setText(topSongModel.signer);
        tvSong.setText(topSongModel.song);

        if (topSongModel.isDownloaded) {
            MusicHandler.playMusic(this, topSongModel);
            Log.d(TAG, "OnReceivedTopSong: ");
        } else {

            Picasso.with(this).load(topSongModel.smallImage).transform(new CropCircleTransformation()).into(ivSong);
            MusicHandler.getSearchSong(topSongModel, this);
        }

        MusicHandler.updateUIRealTime(sbMini, fbPlay, ivSong, null, null);
    }

    private void setupUI() {
        ButterKnife.bind(this);

        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_dashboard_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_favorite_border_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_arrow_downward_black_24dp));

        tabLayout.getTabAt(0).getIcon().setAlpha(255);
        tabLayout.getTabAt(1).getIcon().setAlpha(100);
        tabLayout.getTabAt(2).getIcon().setAlpha(100);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setAlpha(255);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setAlpha(100);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }


        });
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        sbMini.setPadding(0, 0, 0, 0);

        fbPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicHandler.PlayPause();

            }
        });
        rlMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ultis.openFragment(getSupportFragmentManager(), R.id.rl_main1, new PlayerFragment());
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            super.onBackPressed();

        } else {
            moveTaskToBack(true);
        }
    }
}

