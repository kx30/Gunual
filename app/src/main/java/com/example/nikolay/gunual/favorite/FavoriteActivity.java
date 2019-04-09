package com.example.nikolay.gunual.favorite;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import static android.content.Context.MODE_PRIVATE;

public class FavoriteActivity extends FavoriteSharedPreferencesDAO {

    private static final String TAG = "FavoriteActivity";
    private static final int FAVORITE_REQUEST = 1;
    private WeaponAdapter mAdapter;

    private ArrayList<Weapon> mWeapons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        initToolbar();
        loadData();
        sortItems(mWeapons);
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
                if (data.getStringExtra("url").equals(mWeapons.get(i).getImageUrl())) {
                    SharedPreferences sharedPreferences = getSharedPreferences("value", MODE_PRIVATE);
                    String sharedValue = sharedPreferences.getString("favorites", "");
                    Gson gson = new Gson();
                    String weaponPosition = gson.toJson(mWeapons.get(i));
                    if (sharedValue.contains(weaponPosition)) {
                        sharedValue = SharedPreferencesFavoriteDAOFactory.create(this).removeFromFavorite(weaponPosition, sharedValue);
//                        sharedValue = removeTheFavoriteFromSharedPreference(weaponPosition, sharedValue);
                        mWeapons.get(i).setDrawable(R.drawable.unfavorite_star);
                    } else {
                        mWeapons.get(i).setDrawable(R.drawable.favorite_star);
                        sharedValue = SharedPreferencesFavoriteDAOFactory.create(this).addToFavorite(weaponPosition, mWeapons.get(i));
//                        sharedValue = addTheFavoriteToSharedPreference(sharedValue, mWeapons.get(i));
                    }
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("favorites", sharedValue);
                    editor.apply();
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
        Collections.sort(weaponList, (w1, w2) -> w1.getTitle().compareTo(w2.getTitle()));
    }
}
