package com.example.admins.freemusic.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admins.freemusic.Adapter.MusicTypeAdapter;
import com.example.admins.freemusic.Databases.DatabaseHandle;
import com.example.admins.freemusic.Events.OnUpdateRvFav;
import com.example.admins.freemusic.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    @BindView(R.id.rv_fav)
    RecyclerView rvFav;


    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, view);
        rvFav.setAdapter(new MusicTypeAdapter(DatabaseHandle.getFavouriteSong(),getContext()));
        rvFav.setLayoutManager(new GridLayoutManager(getContext(), 2));
        EventBus.getDefault().register(this);
        return view;
    }
    @Subscribe(sticky = true)
    public void update(OnUpdateRvFav onUpdateRvFav){
        rvFav.setAdapter(new MusicTypeAdapter(DatabaseHandle.getFavouriteSong(),getContext()));

    }

}
