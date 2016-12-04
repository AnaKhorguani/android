package com.example.ana.finalproject.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsPagerAdapter  extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    Context context;
    public TabsPagerAdapter(Context context, FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.context=context;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new recentFragment(context);
            case 1:
                // Games fragment activity
                return new  contactsFragment(context);
            case 2:
                // Movies fragment activity
                return new settingsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return mNumOfTabs;
    }
}
