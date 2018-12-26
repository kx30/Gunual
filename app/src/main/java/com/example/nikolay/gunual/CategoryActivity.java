package com.example.nikolay.gunual;

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

public class CategoryActivity extends AppCompatActivity {

    private static final String TAG = "CategoryActivity";

    private ArrayList<String> mTitles = new ArrayList<>();
    private ArrayList<String> mSubtitles = new ArrayList<>();
    private ArrayList<Integer> mImages = new ArrayList<>();

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
                Toast.makeText(this, "favorite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_compare:
                Toast.makeText(this, "to compare", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_about_us:
                Toast.makeText(this, "about us", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        addContext();
        Log.d(TAG, "onCreate: started.");
    }

    private void addContext() {
        mTitles.add("1");
        mSubtitles.add("1");
        mImages.add(R.drawable.image);

        mTitles.add("2");
        mSubtitles.add("2");
        mImages.add(R.drawable.image);

        mTitles.add("3");
        mSubtitles.add("3");
        mImages.add(R.drawable.image);

        mTitles.add("4");
        mSubtitles.add("4");
        mImages.add(R.drawable.image);

        mTitles.add("5");
        mSubtitles.add("5");
        mImages.add(R.drawable.image);

        mTitles.add("6");
        mSubtitles.add("6");
        mImages.add(R.drawable.image);

        mTitles.add("7");
        mSubtitles.add("7");
        mImages.add(R.drawable.image);

        initRecyclerView();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        Log.d(TAG, "initToolbar: initialized.");
    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recycler view");
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        CategoryActivityAdapter adapter = new CategoryActivityAdapter(this, mTitles, mSubtitles, mImages);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
