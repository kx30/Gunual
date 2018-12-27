package com.example.nikolay.gunual;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.firebase.firestore.FirebaseFirestore;

public class InformationActivity extends AppCompatActivity {

    private static final String TAG = "InformationActivity";

    private FirebaseFirestore db;

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
        setContentView(R.layout.activity_information);

        Intent intent = getIntent();
        String extra = intent.getStringExtra("Weapon");
        Log.d(TAG, "onCreate: " + extra);

        initToolbar();
        Log.d(TAG, "onCreate: created.");
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setTitle(R.string.app_name);
        Log.d(TAG, "initToolbar: initialized.");
    }

}

//todo to conclusion from database
