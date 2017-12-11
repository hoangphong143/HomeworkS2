package com.example.admins.freemusic.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.admins.freemusic.Adapter.TopSongAdater;
import com.example.admins.freemusic.Databases.TopSongModel;
import com.example.admins.freemusic.R;
import com.example.admins.freemusic.ultis.DownloadHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;


/**
 * A simple {@link Fragment} subclass.
 */
public class DownloadFragment extends Fragment {
    private static final String TAG = DownloadHandler.class.toString();
    List<TopSongModel> downloadedSongs = new ArrayList<>();
    TopSongAdater downloadAdapter;
    @BindView(R.id.rv_download)
    RecyclerView rvDownload;


    public DownloadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_download, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        File file = new File(getContext().getExternalCacheDir().toString());

        for (File f : file.listFiles()) {
            if (f.isFile()) {
                String name = f.getName();
                String name1 = name.substring(0, name.lastIndexOf("."));
                String[] songInfo = name1.split("-");
                TopSongModel downloadedSong = new TopSongModel(songInfo[0],
                        songInfo[1],
                        getContext().getExternalCacheDir().toString() + "/" + name);
                Log.d(TAG, "setupUI: " + downloadedSong);

                downloadedSong.isDownloaded = true;

                downloadedSongs.add(downloadedSong);

            }

        }
        downloadAdapter = new TopSongAdater(getContext(), downloadedSongs);
        rvDownload.setAdapter(downloadAdapter);
        rvDownload.setLayoutManager(linearLayoutManager);
        rvDownload.setItemAnimator(new SlideInLeftAnimator());
        rvDownload.getItemAnimator().setAddDuration(300);


    }

}
