package com.example.nikolay.gunual.favorite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nikolay.gunual.R;
import com.example.nikolay.gunual.local_database.LocalFavoriteDatabase;
import com.example.nikolay.gunual.models.Weapon;
import com.example.nikolay.gunual.weapon.WeaponAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;

public class FavoriteActivity extends FavoriteSharedPreferencesDAO {

    private static final String TAG = "FavoriteActivity";
    private static final int FAVORITE_REQUEST = 1;
    private WeaponAdapter mAdapter;

    private ArrayList<Weapon> mWeapons = new ArrayList<>();
    private LocalFavoriteDatabase mLocalFavoriteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        mLocalFavoriteDatabase = new LocalFavoriteDatabase(this);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        initToolbar();
        loadWeaponsFromDatabase();
        sortWeapons(mWeapons);
        initRecyclerView(recyclerView);
        Log.d(TAG, "onCreate: created.");
    }

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
        if (requestCode == FAVORITE_REQUEST && resultCode == RESULT_OK) {
            for (int i = 0; i < mWeapons.size(); i++) {
                if (data.getStringExtra("title").equals(mWeapons.get(i).getTitle())) {
                    if (mLocalFavoriteDatabase.isFavorite(mWeapons.get(i).getTitle())) {
                        mLocalFavoriteDatabase.removeFromFavorites(mWeapons.get(i).getTitle());
                    } else {
                        mLocalFavoriteDatabase.addToFavorites(
                                mWeapons.get(i).getTitle(),
                                mWeapons.get(i).getCountry(),
                                mWeapons.get(i).getYearOfProduction(),
                                mWeapons.get(i).getTypeOfBullet(),
                                mWeapons.get(i).getEffectiveRange(),
                                mWeapons.get(i).getMuzzleVelocity(),
                                mWeapons.get(i).getLength(),
                                mWeapons.get(i).getBarrelLength(),
                                mWeapons.get(i).getWeight(),
                                mWeapons.get(i).getFeedSystem(),
                                mWeapons.get(i).getDescription(),
                                mWeapons.get(i).getImageUrl());
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Log.d(TAG, "initToolbar: Toolbar initialized.");
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        mAdapter = new WeaponAdapter(this, mWeapons);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "initRecyclerView: recycler view initialized");
    }

    private void loadWeaponsFromDatabase() {
        Cursor res = mLocalFavoriteDatabase.getFavoritesWeapons();
        if (res.getCount() == 0) {
            Toast.makeText(this, "Favorite's list is empty!", Toast.LENGTH_SHORT).show();
        }
        while (res.moveToNext()) {
            mWeapons.add(new Weapon(
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7),
                    res.getString(8),
                    res.getString(9),
                    res.getString(10),
                    res.getString(11),
                    res.getString(12)
            ));
        }
    }

    private void sortWeapons(ArrayList<Weapon> weaponList) {
        Collections.sort(weaponList, (w1, w2) -> w1.getTitle().compareTo(w2.getTitle()));
    }
}
