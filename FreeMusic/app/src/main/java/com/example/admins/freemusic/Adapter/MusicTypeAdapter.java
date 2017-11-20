package com.example.admins.freemusic.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admins.freemusic.Databases.MusicTypeModel;
import com.example.admins.freemusic.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admins on 11/20/2017.
 */

public class MusicTypeAdapter extends RecyclerView.Adapter<MusicTypeAdapter.MusicTypeViewHolder> {
    List<MusicTypeModel> musicTypeModelList;

    public MusicTypeAdapter(List<MusicTypeModel> musicTypeModelList) {
        this.musicTypeModelList = musicTypeModelList;
    }

    //create view
    @Override
    public MusicTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list_music_type, parent, false);

        return new MusicTypeViewHolder(view);
    }

    //set data
    @Override
    public void onBindViewHolder(MusicTypeViewHolder holder, int position) {
        holder.setData(musicTypeModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return musicTypeModelList.size();
    }

    public class MusicTypeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_music_type)
        ImageView imageView;
        @BindView(R.id.tv_music_type)
        TextView textView;

        public MusicTypeViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void setData(MusicTypeModel musicTypeModel) {
            imageView.setImageResource(musicTypeModel.imageID);
            textView.setText(musicTypeModel.key);
        }
    }


}