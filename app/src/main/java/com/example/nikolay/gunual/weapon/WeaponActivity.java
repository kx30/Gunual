package com.example.nikolay.gunual.weapon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.example.nikolay.gunual.models.Weapon;
import com.example.nikolay.gunual.favorite.FavoriteDAO;
import com.example.nikolay.gunual.filter.FilterActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WeaponActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, FavoriteDAO {

    private static final String TAG = "WeaponActivity";

    private WeaponAdapter mAdapter;
    private ArrayList<Weapon> mWeapons = new ArrayList<>();
    private String[] mCategoryOfWeapons = {"Pistol", "Submachine gun", "Rifle", "Carbine", "Sniper rifle", "Machine gun", "Shotgun"};
    private FloatingActionButton mFloatingActionButton;
    private FirebaseFirestore db;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

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

        ArrayList<Weapon> weapons = new ArrayList<>();

        for (Weapon item : mWeapons) {
            if (item.getTitle().toLowerCase().contains(s)) {
                weapons.add(item);
            }
        }

        mAdapter.updateList(weapons);
        return true;
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
            }
        }

        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
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
                    Toast.makeText(this, "Nothing", Toast.LENGTH_SHORT).show();
                }

                mAdapter = new WeaponAdapter(this, weapons);
                mRecyclerView.setAdapter(mAdapter);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                mAdapter = new WeaponAdapter(this, mWeapons);
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon);
        mFloatingActionButton = findViewById(R.id.filter_button);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> countries = new ArrayList<>();
                ArrayList<String> ammo = new ArrayList<>();
                countries.add(mWeapons.get(0).getCountry());
                try {
                    ammo.add(0, mWeapons.get(0).getTypeOfBullet());
                } catch (Exception e) {
                    ammo.add(0, mWeapons.get(0).getTypeOfBullet().substring(0, mWeapons.get(0).getTypeOfBullet().indexOf(" ")));
                }

                for (int i = 1; i < mWeapons.size(); i++) {
                    for (int j = 0; j < countries.size(); j++) {
                        if (!countries.contains(mWeapons.get(i).getCountry()) && !mWeapons.get(i).getCountry().equals("")) {
                            countries.add(mWeapons.get(i).getCountry());
                        }
                    }
                    for (int j = 0; j < ammo.size(); j++) {
                        try {
                            if (!ammo.contains(mWeapons.get(i).getTypeOfBullet().substring(0, mWeapons.get(i).getTypeOfBullet().indexOf(" ")))
                                    && !mWeapons.get(i).getTypeOfBullet().substring(0, mWeapons.get(i).getTypeOfBullet().indexOf(" ")).equals("")) {
                                ammo.add(mWeapons.get(i).getTypeOfBullet().substring(0, mWeapons.get(i).getTypeOfBullet().indexOf(" ")));
                            }
                        } catch (Exception e) {
                            if (!ammo.contains(mWeapons.get(i).getTypeOfBullet()) && !mWeapons.get(i).getTypeOfBullet().equals("")) {
                                ammo.add(mWeapons.get(i).getTypeOfBullet());
                            }
                        }
                    }
                }

                Intent intent = new Intent(WeaponActivity.this, FilterActivity.class);
                intent.putExtra("countries", countries);
                intent.putExtra("ammo", ammo);
                startActivityForResult(intent, 2);
            }
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
            String value = getExtra();

            for (int i = 0; i < mCategoryOfWeapons.length; i++) {
                if (value.equals(mCategoryOfWeapons[i])) {
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
                                    SharedPreferences sharedPreferences = getSharedPreferences("value", MODE_PRIVATE);
                                    String sharedValue = sharedPreferences.getString("favorites", "");

                                    for (int i1 = 0; i1 < mWeapons.size(); i1++) {
                                        try {
                                            if (sharedValue.contains(mWeapons.get(i1).getImageUrl())) {
                                                mWeapons.get(i1).setDrawable(R.drawable.favorite_star);
                                            } else {
                                                mWeapons.get(i1).setDrawable(R.drawable.unfavorite_star);
                                            }
                                        } catch (Exception e) {
                                            Log.d(TAG, "onSuccess: ERROR " + mWeapons.get(i1).getTitle() + mWeapons.get(i1));
                                        }
                                    }
                                    Log.d(TAG, "onSuccess: " + sharedValue);

                                    mAdapter.notifyDataSetChanged();
                                    mProgressBar.setVisibility(View.GONE);
                                    mFloatingActionButton.show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(WeaponActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in Weapon Activity");
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

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recycler view");
        mAdapter = new WeaponAdapter(this, mWeapons, getExtra());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private String getExtra() {
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
