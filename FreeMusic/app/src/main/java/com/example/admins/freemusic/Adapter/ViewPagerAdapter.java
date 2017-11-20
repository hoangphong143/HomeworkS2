package com.example.admins.freemusic.Adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admins.freemusic.Fragments.DownloadFragment;
import com.example.admins.freemusic.Fragments.FavouriteFragment;
import com.example.admins.freemusic.Fragments.MusicTypeBlankFragment;

/**
 * Created by Admins on 11/19/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new MusicTypeBlankFragment();
            case 1:
                return new FavouriteFragment();
            case 2:
                return new DownloadFragment();
        }

        return null;
    }


    @Override
    public int getCount() {
        return 3;
    }
}
