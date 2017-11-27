package com.example.admins.freemusic.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.admins.freemusic.Adapter.MusicTypeAdapter;
import com.example.admins.freemusic.Databases.MusicTypeModel;
import com.example.admins.freemusic.NetWorks.MusicTypeResponseJSON;
import com.example.admins.freemusic.NetWorks.MusicInterface;
import com.example.admins.freemusic.NetWorks.RetrofitInstance;
import com.example.admins.freemusic.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicTypeBlankFragment extends Fragment {
    @BindView(R.id.rv_music_types)
    RecyclerView rvMusicType;
    private List<MusicTypeModel> musicTypeModelList = new ArrayList<>();
    Context context;
    MusicTypeAdapter musicTypeAdapter;


    private static final String TAG = MusicTypeBlankFragment.class.toString();

    public MusicTypeBlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_type, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this, view);
        context = getContext();


        musicTypeAdapter = new MusicTypeAdapter(musicTypeModelList, getContext());
        rvMusicType.setAdapter(musicTypeAdapter);

        GridLayoutManager gridLayoutManager= new GridLayoutManager(
                getContext(),2
        );
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3==0 ? 2 : 1);
            }
        });
        rvMusicType.setLayoutManager(gridLayoutManager);
        loadData();
        return view;
    }

    private void loadData() {
        MusicInterface musicTypesInterface = RetrofitInstance.getInstance().create(MusicInterface.class);
        musicTypesInterface.getMusicTypes().enqueue(new Callback<MusicTypeResponseJSON>() {
            @Override
            public void onResponse(Call<MusicTypeResponseJSON> call, Response<MusicTypeResponseJSON> response) {
                List<MusicTypeResponseJSON.SubjectJSON> list = response.body().subgenres;
                for (MusicTypeResponseJSON.SubjectJSON subjectJSON : list) {
                    MusicTypeModel musicTypeModel = new MusicTypeModel();
                    musicTypeModel.id = subjectJSON.id;
                    musicTypeModel.key = subjectJSON.translation_key;
                    musicTypeModel.imageID = getContext().getResources().getIdentifier(
                            "genre_x2_" + musicTypeModel.id,
                            "raw",
                            context.getPackageName()
                    );
                    musicTypeModelList.add(musicTypeModel);


                }
                musicTypeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<MusicTypeResponseJSON> call, Throwable t) {

            }
        });
    }

}
