package com.example.nikolay.gunual;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weapon);

        initToolbar();
        initRecyclerView();
        db = FirebaseFirestore.getInstance();

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
                                            Log.d(TAG, "onSuccess: " + mWeapons);
                                        }
                                        sortItems(mWeapons);
                                        mAdapter.notifyDataSetChanged();
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
            mAdapter.notifyDataSetChanged();
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
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new WeaponAdapter(this, mWeapons, getExtra());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private String getExtra() {
        Intent extra = getIntent();

        return extra.getStringExtra("Weapon");
    }

}


//todo create pool-refresh
//todo add image
