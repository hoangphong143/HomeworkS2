package com.example.admins.weather_recycleview.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admins.weather_recycleview.Network.InfoModel;
import com.example.admins.weather_recycleview.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Admins on 11/21/2017.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoViewHolder> {
    List<InfoModel> list;

    public InfoAdapter(List<InfoModel> list) {
        this.list = list;
    }

    @Override
    public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item, parent, false);
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfoViewHolder holder, int position) {
        holder.setData(list.get(position), position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class InfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_info)
        ImageView ivInfo;
        @BindView(R.id.tv_day)
        TextView tvDay;
        @BindView(R.id.tv_des)
        TextView tvDes;
        @BindView(R.id.tv_main)
        TextView tvMain;

        public InfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(InfoModel infoModel, int position) {
            tvDay.setText("Day "+(position+1));
            tvDes.setText("Description: " +infoModel.description);
            tvMain.setText("Weather: "+infoModel.main);



            switch (infoModel.main) {
                case "Clear":
                    ivInfo.setImageResource(R.drawable.artb_clear);
                    break;
                case "Snow":
                    ivInfo.setImageResource(R.drawable.art_snow);
                    break;
                case "Rain":
                    ivInfo.setImageResource(R.drawable.artb_rain);
                    break;


            }


        }
    }
}
