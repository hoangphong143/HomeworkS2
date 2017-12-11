package com.example.admins.freemusic.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admins.freemusic.Databases.TopSongModel;
import com.example.admins.freemusic.Events.OnClickTopSongEvent;
import com.example.admins.freemusic.Notifications.MusicNotification;
import com.example.admins.freemusic.R;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Admins on 11/25/2017.
 */

public class TopSongAdater extends RecyclerView.Adapter<TopSongAdater.TopSongViewHolder> {
    Context context;
    List<TopSongModel>topSongModelList;

    public TopSongAdater(Context context, List<TopSongModel> topSongModelList) {
        this.context = context;
        this.topSongModelList = topSongModelList;
    }

    @Override
    public TopSongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(context);
        View itemView= layoutInflater.inflate(R.layout.item_list_box_song, parent, false);
        return new TopSongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopSongViewHolder holder, int position) {
        holder.setData(topSongModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return topSongModelList.size();
    }

    public class TopSongViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_song)
        ImageView ivSong;
        @BindView(R.id.tv_song)
        TextView tvSong;
        @BindView(R.id.tv_singer)
        TextView tvSinger;

        View view;


        public TopSongViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view= itemView;
        }



        public void setData(final TopSongModel topSongModel) {
            Picasso.with(context).load(topSongModel.smallImage).transform(new CropCircleTransformation()).into(ivSong);
            tvSong.setText(topSongModel.song);
            tvSinger.setText(topSongModel.signer);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().postSticky(new OnClickTopSongEvent(topSongModel));

                }
            });
        }
    }
}
