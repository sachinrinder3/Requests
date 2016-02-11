package com.example.android.requests.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;

/**
 * Created by tulsijain on 08/02/16.
 */
public class CustomPageAdapter extends PagerAdapter {
    private Context mcontext;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
