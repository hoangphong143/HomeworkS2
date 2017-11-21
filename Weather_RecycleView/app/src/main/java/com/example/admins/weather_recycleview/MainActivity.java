package com.example.admins.weather_recycleview;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admins.weather_recycleview.Adapter.InfoAdapter;
import com.example.admins.weather_recycleview.Network.InfoInterface;
import com.example.admins.weather_recycleview.Network.InfoJSON;
import com.example.admins.weather_recycleview.Network.InfoModel;
import com.example.admins.weather_recycleview.Network.RetrofitInstance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.toString();
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.bt_show)
    Button btShow;
    @BindView(R.id.rv_info)
    RecyclerView rvInfo;
    Context context;

    List<InfoJSON.ObjectJSON> list = new ArrayList<>();
    List<InfoModel> infoModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        ButterKnife.bind(this);

        btShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final InfoInterface infoInterface = RetrofitInstance.getInstance().create(InfoInterface.class);
                infoInterface.getInfo(etName.getText().toString(), "75543130563ee64f1587446c3d3ee026").enqueue(new Callback<InfoJSON>() {
                    @Override
                    public void onResponse(Call<InfoJSON> call, Response<InfoJSON> response) {
                        if (response.isSuccessful()) {
                            list = response.body().list;
                            for (InfoJSON.ObjectJSON item : list) {
                                String main = item.weather.get(0).main;
                                String des = item.weather.get(0).description;
                                infoModelList.add(new InfoModel(des, main));
                                InfoAdapter infoAdapter = new InfoAdapter(infoModelList);
                                rvInfo.setAdapter(infoAdapter);
                                rvInfo.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                infoAdapter.notifyDataSetChanged();


                            }

                        } else {
                            Toast.makeText(MainActivity.this, "Cannot find that city", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<InfoJSON> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "FAIL", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
    }
}
