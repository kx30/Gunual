package com.example.nikolay.gunual;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    private static final String TAG = "FavoriteActivity";

    private WeaponAdapter mAdapter;
    private ArrayList<Weapon> mWeapons = new ArrayList<>();

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        initToolbar();

        try {
            loadData();
        } catch (Exception e) {
            Log.d(TAG, "onCreate: " + e);
        }


        initRecyclerView(recyclerView);

        SharedPreferences sharedPreferences = getSharedPreferences("value", MODE_PRIVATE);
        String value = sharedPreferences.getString("favorites", "");

        Log.d(TAG, "onCreate: VALUE: " + value);

        Log.d(TAG, "onCreate: created.");
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Log.d(TAG, "initToolbar: Toolbar initialized.");
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("value", MODE_PRIVATE);
        Gson gson = new Gson();
        String value = sharedPreferences.getString("favorites", "");
        Type type = new TypeToken<ArrayList<Weapon>>() {}.getType();
        mWeapons = gson.fromJson(value, type);

        if (mWeapons == null) {
            mWeapons = new ArrayList<>();
        }
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        mAdapter = new WeaponAdapter(this, mWeapons);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "initRecyclerView: recycler view initialized");
    }

}
