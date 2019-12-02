package com.placetracker.adapter;

import android.content.Context;


import com.placetracker.fragments.FirstSelfie;
import com.placetracker.fragments.LastSelfie;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MapAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public MapAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FirstSelfie fristSelfieFragment = new FirstSelfie();
                return fristSelfieFragment;
            case 1:
                LastSelfie lastSelfieFragment = new LastSelfie();
                return lastSelfieFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}