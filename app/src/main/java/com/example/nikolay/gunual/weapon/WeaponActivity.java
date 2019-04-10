package com.example.nikolay.gunual.weapon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikolay.gunual.R;
import com.example.nikolay.gunual.filter.FilterActivity;
import com.example.nikolay.gunual.local_database.LocalFavoriteDatabase;
import com.example.nikolay.gunual.models.Weapon;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeaponActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "WeaponActivity";
    private static final int FAVORITE_REQUEST = 1;
    private static final int FILTER_REQUEST = 2;

    private WeaponAdapter mAdapter;
    private ArrayList<Weapon> mWeapons = new ArrayList<>();
    private FloatingActionButton mFloatingActionButton;
    private FirebaseFirestore db;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private LocalFavoriteDatabase mLocalFavoriteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon);
        mFloatingActionButton = findViewById(R.id.filter_button);
        mLocalFavoriteDatabase = new LocalFavoriteDatabase(this);
        mFloatingActionButton.setOnClickListener(view -> {

            ArrayList<String> countries = new ArrayList<>();
            ArrayList<String> ammo = new ArrayList<>();
            Weapon weapon = mWeapons.get(0);
            countries.add(weapon.getCountry());
            try {
                ammo.add(0, weapon.getTypeOfBullet());
            } catch (Exception e) {
                ammo.add(0, weapon.getTypeOfBullet().substring(0, weapon.getTypeOfBullet().indexOf(" ")));
            }

            for (int i = 1; i < mWeapons.size(); i++) {
                Weapon filteredWeapon = mWeapons.get(i);
                for (int j = 0; j < countries.size(); j++) {
                    String country = filteredWeapon.getCountry();
                    if (!countries.contains(country) && !country.equals("")) {
                        countries.add(country);
                    }
                }
                for (int j = 0; j < ammo.size(); j++) {
                    try {
                        String substring = filteredWeapon.getTypeOfBullet().substring(0, filteredWeapon.getTypeOfBullet().indexOf(" "));
                        if (!ammo.contains(substring) && !substring.equals("")) {
                            ammo.add(substring);
                        }
                    } catch (Exception e) {
                        if (!ammo.contains(filteredWeapon.getTypeOfBullet()) && !filteredWeapon.getTypeOfBullet().equals("")) {
                            ammo.add(filteredWeapon.getTypeOfBullet());
                        }
                    }
                }
            }

            Intent intent = new Intent(WeaponActivity.this, FilterActivity.class);
            intent.putExtra("countries", countries);
            intent.putExtra("ammo", ammo);
            startActivityForResult(intent, FILTER_REQUEST);
        });
        initToolbar();
        mProgressBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.recycler_view);
        initRecyclerView();

        swipeContent();
        db = FirebaseFirestore.getInstance();
        addItems();

        Log.d(TAG, "onCreate: started.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weapon_activity_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        List<Weapon> filteredWeapons = new ArrayList<>();

        for (Weapon item : mWeapons) {
            if (item.getTitle().toLowerCase().contains(s)) {
                filteredWeapons.add(item);
            }
        }
        mAdapter.updateList(filteredWeapons);
        return true;
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
        if (requestCode == FILTER_REQUEST & resultCode == RESULT_OK) {
            ArrayList<Weapon> weapons = new ArrayList<>();
            String country = data.getStringExtra("country");
            String ammo = data.getStringExtra("ammo");
            if (country != null && ammo != null) {
                for (int i = 0; i < mWeapons.size(); i++) {
                    if (mWeapons.get(i).getCountry().equals(country) && mWeapons.get(i).getTypeOfBullet().contains(ammo)) {
                        weapons.add(mWeapons.get(i));
                    }
                }
            } else if (country != null) {
                for (int i = 0; i < mWeapons.size(); i++) {
                    if (mWeapons.get(i).getCountry().equals(country)) {
                        weapons.add(mWeapons.get(i));
                    }
                }
            } else {
                for (int i = 0; i < mWeapons.size(); i++) {
                    if (mWeapons.get(i).getTypeOfBullet().contains(ammo)) {
                        weapons.add(mWeapons.get(i));
                    }
                }
            }
            if (weapons.size() == 0) {
                Toast.makeText(this, "Nothing not found", Toast.LENGTH_SHORT).show();
            }

            mAdapter = new WeaponAdapter(this, weapons);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setAdapter(mAdapter);
        } else if (requestCode == FILTER_REQUEST && resultCode == RESULT_CANCELED) {
            mAdapter = new WeaponAdapter(this, mWeapons);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        Log.d(TAG, "initToolbar: initialized.");
    }

    private void addItems() {
        try {
            String value = getWeaponCategory();
            db.collection("weapons")
                    .document("kind_of_weapon")
                    .collection(value)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                mWeapons.add(new Weapon(
                                        d.getString("title"),
                                        d.getString("country"),
                                        d.getString("year_of_production"),
                                        d.getString("type_of_bullet"),
                                        d.getString("effective_range"),
                                        d.getString("muzzle_velocity"),
                                        d.getString("length"),
                                        d.getString("barrel_length"),
                                        d.getString("weight"),
                                        d.getString("feed_system"),
                                        d.getString("description"),
                                        d.getString("image_url")
                                ));
                            }

                            sortItems(mWeapons);

                            mAdapter.notifyDataSetChanged();
                            mProgressBar.setVisibility(View.GONE);
                            mFloatingActionButton.show();
                        }
                    })
                    .addOnFailureListener(e -> Toast.makeText(WeaponActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        } catch (Exception e) {
            Log.d(TAG, "Error in Weapon Activity");
        }
    }

    private void sortItems(ArrayList<Weapon> weaponList) {
        Collections.sort(weaponList, (w1, w2) -> w1.getTitle().compareTo(w2.getTitle()));
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recycler view");
        mAdapter = new WeaponAdapter(this, mWeapons, getWeaponCategory());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private String getWeaponCategory() {
        Intent extra = getIntent();
        return extra.getStringExtra("Weapon");
    }

    private boolean hasConnection(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }

    private void swipeContent() {
        ImageView noConnectionImageView = findViewById(R.id.no_connection_image_view);
        TextView noConnectionMainText = findViewById(R.id.no_connection_main_text);
        TextView noConnectionDescriptionText = findViewById(R.id.no_connection_description_text);

        if (!hasConnection(this)) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
            mFloatingActionButton.hide();
            Log.d(TAG, "swipeContent: no connection");

            noConnectionImageView.setVisibility(View.VISIBLE);
            noConnectionMainText.setVisibility(View.VISIBLE);
            noConnectionDescriptionText.setVisibility(View.VISIBLE);
        }
        if (hasConnection(this)) {
            mRecyclerView.setVisibility(View.VISIBLE);
            Log.d(TAG, "swipeContent: connection!");
            noConnectionImageView.setVisibility(View.INVISIBLE);
            noConnectionMainText.setVisibility(View.INVISIBLE);
            noConnectionDescriptionText.setVisibility(View.INVISIBLE);
        }
    }
}
