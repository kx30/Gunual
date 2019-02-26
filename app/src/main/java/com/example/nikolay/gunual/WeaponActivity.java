package com.example.nikolay.gunual;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class WeaponActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "WeaponActivity";

    private WeaponAdapter mAdapter;
    private ArrayList<Weapon> mWeapons = new ArrayList<>();
    private String[] mCategoryOfWeapons = {"Pistol", "Submachine gun", "Rifle", "Carbine", "Sniper rifle", "Machine gun", "Shotgun"};
    private FirebaseFirestore db;
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

                        if (!isFavorite) {
                            if (sharedValue.contains(weaponPosition)) {
                                // If first object in string
                                if (sharedValue.indexOf(weaponPosition) - 1 == 0) {
                                    sharedValue = sharedValue.replace(weaponPosition, "");
                                    if (sharedValue.charAt(1) == ',') {
                                        sharedValue = sharedValue.substring(2);
                                        sharedValue = "[" + sharedValue;
                                    } else {
                                        sharedValue = "";
                                    }
                                } else if (sharedValue.charAt(sharedValue.indexOf(weaponPosition) + weaponPosition.length()) == ']') {
                                    // If last object in string
                                    sharedValue = sharedValue.replace(weaponPosition, "");
                                    sharedValue = sharedValue.substring(0, sharedValue.length() - 2);
                                    sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), "]").toString();
                                } else {
                                    sharedValue = sharedValue.substring(0, sharedValue.indexOf(weaponPosition)) +
                                            sharedValue.substring(
                                                    sharedValue.indexOf(weaponPosition) + weaponPosition.length() + 1,
                                                    sharedValue.length());
                                }
                                mWeapons.get(i).setDrawable(R.drawable.unfavorite_star);
                            }
                        } else {
                            mWeapons.get(i).setDrawable(R.drawable.favorite_star);
                            if (sharedValue.equals("")) {
                                sharedValue += "[" + gson.toJson(mWeapons.get(i));
                                sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), "]").toString();
                            } else {
                                sharedValue = sharedValue.substring(0, sharedValue.length() - 1);
                                sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), ",").toString();
                                sharedValue += gson.toJson(mWeapons.get(i));
                                sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), "]").toString();
                            }
                        }

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
        setContentView(R.layout.activity_weapon);

        initToolbar();

        mProgressBar = findViewById(R.id.progress_bar);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        initRecyclerView(recyclerView);

        swipeContent(recyclerView);

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
            Intent extra = getIntent();
            String value = extra.getStringExtra("Weapon");

            for (int i = 0; i < mCategoryOfWeapons.length; i++) {
                if (value.equals(mCategoryOfWeapons[i])) {
                    Toast.makeText(this, mCategoryOfWeapons[i], Toast.LENGTH_SHORT).show();
                    db.collection("weapons")
                            .document("kind_of_weapon")
                            .collection(value)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
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

                                        for (int i = 0; i < mWeapons.size(); i++) {
                                            try {
                                                if (sharedValue.contains(mWeapons.get(i).getImageUrl())) {
                                                    mWeapons.get(i).setDrawable(R.drawable.favorite_star);
                                                } else {
                                                    mWeapons.get(i).setDrawable(R.drawable.unfavorite_star);
                                                }
                                            } catch (Exception e) {
                                                Log.d(TAG, "onSuccess: ERROR " + mWeapons.get(i).getTitle() + mWeapons.get(i)   );
                                            }
                                        }
                                        Log.d(TAG, "onSuccess: " + sharedValue);

                                        mAdapter.notifyDataSetChanged();
                                        mProgressBar.setVisibility(View.GONE);
                                    }
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

    private void initRecyclerView(RecyclerView recyclerView) {
        Log.d(TAG, "initRecyclerView: init recycler view");
        mAdapter = new WeaponAdapter(this, mWeapons, getExtra());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

    private void swipeContent(RecyclerView recyclerView) {
        ImageView noConnectionImageView = findViewById(R.id.no_connection_image_view);
        TextView noConnectionMainText = findViewById(R.id.no_connection_main_text);
        TextView noConnectionDescriptionText = findViewById(R.id.no_connection_description_text);

        if (!hasConnection(this)) {
            recyclerView.setVisibility(View.INVISIBLE);
            Log.d(TAG, "swipeContent: no connection");

            noConnectionImageView.setVisibility(View.VISIBLE);
            noConnectionMainText.setVisibility(View.VISIBLE);
            noConnectionDescriptionText.setVisibility(View.VISIBLE);
        }
        if (hasConnection(this)) {
            recyclerView.setVisibility(View.VISIBLE);
            Log.d(TAG, "swipeContent: connection!");

            noConnectionImageView.setVisibility(View.INVISIBLE);
            noConnectionMainText.setVisibility(View.INVISIBLE);
            noConnectionDescriptionText.setVisibility(View.INVISIBLE);
        }
    }
}