package com.example.admins.freemusic;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.admins.freemusic.Adapter.ViewPagerAdapter;
import com.example.admins.freemusic.NetWorks.MusicTypeResponseJSON;
import com.example.admins.freemusic.NetWorks.MusicTypesInterface;
import com.example.admins.freemusic.NetWorks.RetrofitInstance;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.toString();
    @BindView(R.id.tab)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();


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

    }
}
