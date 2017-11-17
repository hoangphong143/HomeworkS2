package com.example.admins.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    EditText etCityName;
    TextView tvShow;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCityName = findViewById(R.id.et_city);
        tvShow = findViewById(R.id.tv_show);
        tvInfo = findViewById(R.id.tv_info);
        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InfoInterface infoInterface = RetrofitInstance.getInstance().create(InfoInterface.class);
                infoInterface.getInfo(etCityName.getText().toString(), "75543130563ee64f1587446c3d3ee026").enqueue(new Callback<InfoJSON>() {
                    @Override
                    public void onResponse(Call<InfoJSON> call, Response<InfoJSON> response) {
                        if (response.isSuccessful()) {
                            InfoJSON infoJSON = response.body();
                            float humidity = infoJSON.main.humidity;
                            float pressure = infoJSON.main.pressure;
                            float temperature = infoJSON.main.temp - 273;
                            tvInfo.setText(" humidity " + humidity + "\n" +
                                    " pressure " + pressure + "\n" + " temperature " + temperature + "*C");


                        } else {
                            Toast.makeText(MainActivity.this, "Cannot find this city", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<InfoJSON> call, Throwable t) {

                    }
                });

            }
        });


    }
}