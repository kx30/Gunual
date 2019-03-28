package com.example.nikolay.gunual.category;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.nikolay.gunual.preview.PreviewActivity;
import com.example.nikolay.gunual.R;
import com.example.nikolay.gunual.about_us.AboutUsActivity;
import com.example.nikolay.gunual.favorite.FavoriteActivity;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    private static final String TAG = "CategoryActivity";

    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<Integer> mImages = new ArrayList<>();
    private String[] mCategoryOfWeapons = {"Pistols", "Submachine guns", "Rifles", "Carbines", "Sniper rifles", "Machine guns", "Shotguns"};
    private Integer[] mDrawables = {R.drawable.pistol, R.drawable.submachine_gun, R.drawable.rifle,
            R.drawable.carbine, R.drawable.sniper_rifle, R.drawable.machine_gun, R.drawable.shotgun};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                Intent favoriteIntent = new Intent(this, FavoriteActivity.class);
                startActivity(favoriteIntent);
                break;
            case R.id.action_about_us:
                Intent aboutIntent = new Intent(this, AboutUsActivity.class);
                startActivity(aboutIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        initToolbar();

        addContext();
        Log.d(TAG, "onCreate: started.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("value", MODE_PRIVATE);
        boolean isSkipped = sharedPreferences.getBoolean("isSkipped", false);
        if (!isSkipped) {
            Intent intent = new Intent(CategoryActivity.this, PreviewActivity.class);
            startActivity(intent);
        }
    }

    private void addContext() {

        for (int i = 0; i < mCategoryOfWeapons.length; i++) {
            mTitles.add(mCategoryOfWeapons[i]);
            mImages.add(mDrawables[i]);
        }

        initRecyclerView();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        Log.d(TAG, "initToolbar: initialized.");
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recycler view");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        CategoryAdapter adapter = new CategoryAdapter(this, mTitles, mImages);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}