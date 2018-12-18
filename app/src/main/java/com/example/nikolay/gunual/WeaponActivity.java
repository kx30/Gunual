package com.example.nikolay.gunual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.ArrayList;

public class WeaponActivity extends AppCompatActivity {

    private static final String TAG = "WeaponActivity";

    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Integer> mImages = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon);

        initToolbar();
        addItems();
        Log.d(TAG, "onCreate: started.");
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        Log.d(TAG, "initToolbar: initialized.");
    }

    private void addItems() {
        try {

            Intent intent = getIntent();
            String value = intent.getStringExtra("Weapon");
            Log.d(TAG, "addItems: " + value);

            if (value.equals("Pistol")) {
                Log.d(TAG, "addItems: MEOEOOWOEWOEOWEOWOE");
                mTitles.add("1");
                mDescriptions.add("1");
                mImages.add(R.drawable.image);

                mTitles.add("2");
                mDescriptions.add("123124124124124124234234124124124124124124125125543545f34546747568755765326875443f52636");
                mImages.add(R.drawable.image);

                mTitles.add("3");
                mDescriptions.add("3");
                mImages.add(R.drawable.image);

                mTitles.add("4");
                mDescriptions.add("4");
                mImages.add(R.drawable.image);

                mTitles.add("5");
                mDescriptions.add("5");
                mImages.add(R.drawable.image);

                mTitles.add("6");
                mDescriptions.add("6");
                mImages.add(R.drawable.image);

                mTitles.add("7");
                mDescriptions.add("7");
                mImages.add(R.drawable.image);
                initRecyclerView();
            } if (value.equals("Submachine gun")) {
                Log.d(TAG, "addItems: " + value);
            }
            if (value.equals("Rifle")) {
                Log.d(TAG, "addItems: " + value);
            }
            if (value.equals("Carbine")) {
                Log.d(TAG, "addItems: " + value);
            }
            if (value.equals("Sniper rifle")) {
                Log.d(TAG, "addItems: " + value);
            }
            if (value.equals("Machine gun")) {
                Log.d(TAG, "addItems: " + value);
            }
            if (value.equals("Shotgun")) {
                Log.d(TAG, "addItems: " + value);
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in Weapon Activity");
        }
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recycler view");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        WeaponAdapter adapter = new WeaponAdapter(this, mTitles, mDescriptions, mImages);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}