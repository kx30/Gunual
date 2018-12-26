package com.example.nikolay.gunual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class WeaponActivity extends AppCompatActivity {

    private static final String TAG = "WeaponActivity";
    WeaponAdapter mAdapter;
    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mDescriptions = new ArrayList<>();
    private ArrayList<Integer> mImages = new ArrayList<>();
    private String[] mCategoryOfWeapons = {"Pistol", "Submachine gun", "Rifle", "Carbine", "Sniper rifle", "Machnine gun", "Shotgun"};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent extra = getIntent();
        String value = extra.getStringExtra("Weapon");
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(WeaponActivity.this, AddWeaponsInDataBaseActivity.class);
            for (int i = 0; i < mCategoryOfWeapons.length; i++) {
                if (value.equals(mCategoryOfWeapons[i])) {
                    Toast.makeText(this, value, Toast.LENGTH_SHORT).show();
                    intent.putExtra("Weapon", mCategoryOfWeapons[i]);
                }
            }
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon);

        initToolbar();
        initRecyclerView();

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
            Intent extra = getIntent();
            String value = extra.getStringExtra("Weapon");
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
            }
            if (value.equals("Submachine gun")) {
                mTitles.add("2");
                mDescriptions.add("1");
                mImages.add(R.drawable.image);

                mTitles.add("2");
                mDescriptions.add("112312");
                mImages.add(R.drawable.image);

                mTitles.add("3");
                mDescriptions.add("3");
                mImages.add(R.drawable.image);

                mTitles.add("6");
                mDescriptions.add("62222");
                mImages.add(R.drawable.image);

                mTitles.add("7");
                mDescriptions.add("7");
                mImages.add(R.drawable.image);
            }
            if (value.equals("Rifle")) {
                mTitles.add("1");
                mDescriptions.add("1144411");
                mImages.add(R.drawable.image);
            }

            if (value.equals("Carbine")) {
                Log.d(TAG, "Carbine");
            }

            if (value.equals("Sniper rifle")) {
                Log.d(TAG, "Sniper rifle");
            }

            if (value.equals("Machine gun")) {
                Log.d(TAG, "Machine gun");
            }

            if (value.equals("Shotgun")) {
                Log.d(TAG, "Shotgun");
            }
            mAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.d(TAG, "Error in Weapon Activity");
        }
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recycler view");
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new WeaponAdapter(this, mTitles, mDescriptions, mImages);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}