package com.example.nikolay.gunual.favorite;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.nikolay.gunual.R;
import com.example.nikolay.gunual.models.Weapon;
import com.example.nikolay.gunual.weapon.WeaponAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FavoriteActivity extends AppCompatActivity implements FavoriteDAO {

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                boolean isFavorite = data.getBooleanExtra("isFavorite", false);
                for (int i = 0; i < mWeapons.size(); i++) {
                    if (data.getStringExtra("url").equals(mWeapons.get(i).getImageUrl())) {

                        SharedPreferences sharedPreferences = getSharedPreferences("value", Context.MODE_PRIVATE);
                        String sharedValue = sharedPreferences.getString("favorites", "");

                        Gson gson = new Gson();
                        String weaponPosition = gson.toJson(mWeapons.get(i));

                        sharedValue = returnerFavoriteSharedPreferencesString(isFavorite, sharedValue, weaponPosition, data, mWeapons);


                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("favorites", sharedValue);
                        editor.apply();
                        mAdapter.notifyDataSetChanged();

                    }
                }
                Log.d(TAG, "onActivityResult: " + isFavorite);
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        initToolbar();

        loadData();
        sortItems(mWeapons);

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
        Type type = new TypeToken<ArrayList<Weapon>>() {
        }.getType();
        mWeapons = gson.fromJson(value, type);

        if (mWeapons == null) {
            mWeapons = new ArrayList<>();
        }
    }

    private void sortItems(ArrayList<Weapon> weaponList) {
        Collections.sort(weaponList, new Comparator<Weapon>() {
            @Override
            public int compare(Weapon w1, Weapon w2) {
                return w1.getTitle().compareTo(w2.getTitle());
            }
        });
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        mAdapter = new WeaponAdapter(this, mWeapons);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "initRecyclerView: recycler view initialized");
    }

}
