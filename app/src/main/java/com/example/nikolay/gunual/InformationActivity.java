package com.example.nikolay.gunual;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

public class InformationActivity extends AppCompatActivity {

    private static final String TAG = "InformationActivity";
    private TextView countryTextView;
    private TextView yearOfProductionTextView;
    private TextView typeOfBulletTextView;
    private TextView maxRangeTextView;
    private TextView effectiveRangeTextView;
    private TextView feedSystemTextView;
    private TextView lengthTextView;
    private TextView barrelLengthTextView;
    private TextView loadedWeightTextView;
    private TextView unloadedWeightTextView;
    private TextView rapidFireTextView;
    private TextView costTextView;


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
        initTextViews();

        Intent intent = getIntent();
        String extra = intent.getStringExtra("weapon");
        Log.d(TAG, "onCreate: " + extra);
        initToolbar();

        getExtras();
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

    private void initTextViews() {
        countryTextView = findViewById(R.id.country_text_view);
        yearOfProductionTextView = findViewById(R.id.year_of_production_text_view);
        typeOfBulletTextView = findViewById(R.id.type_of_bullet_text_view);
        maxRangeTextView = findViewById(R.id.max_range_text_view);
        effectiveRangeTextView = findViewById(R.id.effective_range_text_view);
        feedSystemTextView = findViewById(R.id.feed_system_text_view);
        lengthTextView = findViewById(R.id.length_text_view);
        barrelLengthTextView = findViewById(R.id.barrel_length_text_view);
        loadedWeightTextView = findViewById(R.id.weight_loaded_text_view);
        unloadedWeightTextView = findViewById(R.id.weight_unloaded_text_view);
        rapidFireTextView = findViewById(R.id.rapid_fire_text_view);
        costTextView = findViewById(R.id.cost_text_view);
    }

    private void getExtras() {

        String country = "", yearOfProduction = "", typeOfBullet = "", maxRange = "",
                effectiveRange = "", feedSystem = "", length = "", barrelLength = "",
                loadedWeight = "", unloadedWeight = "", rapidFire = "", cost = "";

        try {
            Bundle arguments = getIntent().getExtras();
            if (arguments != null) {
                country = arguments.get("country").toString();
                yearOfProduction = arguments.get("yearOfProduction").toString();
                typeOfBullet = arguments.get("typeOfBullet").toString();
                maxRange = arguments.get("maxRange").toString();
                effectiveRange = arguments.get("effectiveRange").toString();
                feedSystem = arguments.get("feedSystem").toString();
                length = arguments.get("length").toString();
                barrelLength = arguments.get("barrelLength").toString();
                loadedWeight = arguments.get("loadedWeight").toString();
                unloadedWeight = arguments.get("unloadedWeight").toString();
                rapidFire = arguments.get("rapidFire").toString();
                cost = arguments.get("cost").toString();
            }
            countryTextView.setText(country);
            yearOfProductionTextView.setText(yearOfProduction);
            typeOfBulletTextView.setText(typeOfBullet);
            maxRangeTextView.setText(maxRange);
            effectiveRangeTextView.setText(effectiveRange);
            feedSystemTextView.setText(feedSystem);
            lengthTextView.setText(length);
            barrelLengthTextView.setText(barrelLength);
            loadedWeightTextView.setText(loadedWeight);
            unloadedWeightTextView.setText(unloadedWeight);
            rapidFireTextView.setText(rapidFire);
            costTextView.setText(cost);
        } catch (Exception e) {
            Log.d(TAG, "Error!" + e.getMessage());
        }
    }

}

//todo to conclusion from database
