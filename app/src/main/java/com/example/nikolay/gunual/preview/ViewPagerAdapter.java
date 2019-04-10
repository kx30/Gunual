package com.example.nikolay.gunual.preview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.nikolay.gunual.R;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private String[] mInformationAboutApplication = {"Просматривать короче оружия", "Покупать их ага", "И патроны тоже"};
    private Integer[] mPageImages = {R.drawable.preview_weapon, R.drawable.preview_buy_weapon, R.drawable.preview_buy_ammo};

    ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        DemoFragment demoFragment = new DemoFragment();
        i++;
        Bundle bundle = new Bundle();
        bundle.putString("message", mInformationAboutApplication[i - 1]);
        bundle.putInt("image", mPageImages[i - 1]);
        demoFragment.setArguments(bundle);
        return demoFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}