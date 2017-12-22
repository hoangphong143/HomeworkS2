package com.example.admins.freemusic.Fragments;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admins.freemusic.Adapter.TopSongAdater;
import com.example.admins.freemusic.Databases.DatabaseHandle;
import com.example.admins.freemusic.Databases.MusicTypeModel;
import com.example.admins.freemusic.Databases.TopSongModel;
import com.example.admins.freemusic.Events.OnClickMusicTypeEvent;
import com.example.admins.freemusic.Events.OnUpdateRvFav;
import com.example.admins.freemusic.NetWorks.MusicInterface;
import com.example.admins.freemusic.NetWorks.RetrofitInstance;
import com.example.admins.freemusic.NetWorks.TopSongResponse;
import com.example.admins.freemusic.R;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopSongFragment extends Fragment {
    public static final String TAG = TopSongFragment.class.toString();
    @BindView(R.id.tv_music_type)
    TextView tvMusicType;
    @BindView(R.id.iv_favourite)
    ImageView ivFav;
    @BindView(R.id.iv_music_type)
    ImageView ivMusicType;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_song)
    RecyclerView rvSong;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.app_bar)
    AppBarLayout appBarLayout;
    @BindView(R.id.avLoad)
    AVLoadingIndicatorView avLoad;
    public TopSongAdater topSongAdater;
    public static List<TopSongModel> topSongModels = new ArrayList<>();

    public MusicTypeModel musicTypeModel;


    public TopSongFragment() {
        // Required empty public constructor
    }


    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_top_song, container, false);
        EventBus.getDefault().register(this);
        setupUI(view);
        loadData();

        return view;


    }

    private void loadData() {
        topSongModels.clear();
        MusicInterface musicInterface = RetrofitInstance.getInstance().create(MusicInterface.class);
        musicInterface.getTopSongs(musicTypeModel.id).enqueue(new Callback<TopSongResponse>() {
            @Override
            public void onResponse(Call<TopSongResponse> call, Response<TopSongResponse> response) {
                avLoad.hide();

                List<TopSongResponse.EntryJSON> entryJSONSList = response.body().feed.entry;

                for (int i = 0; i < entryJSONSList.size(); i++) {
                    TopSongModel topSongModel = new TopSongModel();
                    topSongModel.singer = entryJSONSList.get(i).artis.label;
                    topSongModel.song = entryJSONSList.get(i).name.label;
                    topSongModel.smallImage = entryJSONSList.get(i).image.get(2).label;
                    topSongModels.add(topSongModel);
                    topSongAdater.notifyItemChanged(i);

                }
//                topSongAdater.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<TopSongResponse> call, Throwable t) {
                Toast.makeText(getContext(), " NO connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Subscribe(sticky = true)
    public void OnReceivedONMusicTypeClick(OnClickMusicTypeEvent onClickMusicTypeEvent) {
        musicTypeModel = onClickMusicTypeEvent.musicTypeModel;

    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        tvMusicType.setText(musicTypeModel.key);
        Picasso.with(getContext()).load(musicTypeModel.imageID).into(ivMusicType);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    toolbar.setBackground(getResources().getDrawable(R.drawable.custom_gradient_text_bot_to_top));
                } else {
                    toolbar.setBackground(null);
                }
            }
        });

        topSongAdater = new TopSongAdater(getContext(), topSongModels);
        rvSong.setAdapter(topSongAdater);
        rvSong.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSong.setItemAnimator(new SlideInLeftAnimator());
        rvSong.getItemAnimator().setAddDuration(300);
        avLoad.show();

        if (musicTypeModel.isFavourite) {
            ivFav.setColorFilter(Color.RED);

        } else {
            ivFav.setColorFilter(Color.WHITE);
        }

        ivFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandle.updateFavourite(musicTypeModel);
                if (musicTypeModel.isFavourite) {
                    ivFav.setColorFilter(Color.RED);

                } else {
                    ivFav.setColorFilter(Color.WHITE);
                }
                EventBus.getDefault().postSticky(new OnUpdateRvFav());
            }
        });


    }
}
