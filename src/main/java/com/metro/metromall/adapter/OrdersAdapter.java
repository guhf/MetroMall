package com.metro.metromall.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.metro.metromall.fragments.tools.FragmentFactory;

/**
 * Created by guhf on 2017/11/9.
 */

public class OrdersAdapter extends FragmentStatePagerAdapter {
    public OrdersAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createForOrders(position);
    }

    @Override
    public int getCount() {
        return 4;
    }
}
