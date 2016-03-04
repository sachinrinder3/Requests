package com.example.android.requests.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.Menu;

import com.example.android.requests.R;
import com.example.android.requests.fragments.ImageFragment;

public  class CustomPageAdapter extends FragmentPagerAdapter {
    int ITEMS = 10;
    public CustomPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return ITEMS;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show image
                return ImageFragment.init(position);
            case 1: // Fragment # 1 - This will show image
                return ImageFragment.init(position);
            default:// Fragment # 2-9 - Will show list
                return ImageFragment.init(position);
        }
    }
}