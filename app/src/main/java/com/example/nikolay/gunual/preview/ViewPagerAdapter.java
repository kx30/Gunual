package com.example.nikolay.gunual.preview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        //todo align text by center
        String[] previewText = {"Просматривать короче оружие", "Покупать их ага", "И патроны тоже"};
        DemoFragment demoFragment = new DemoFragment();
        i += 1;
        Bundle bundle = new Bundle();
        bundle.putString("message", previewText[i - 1]);
        demoFragment.setArguments(bundle);
        return demoFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}