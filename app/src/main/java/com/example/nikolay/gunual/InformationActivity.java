package com.example.nikolay.gunual;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

public class InformationActivity extends AppCompatActivity {

    private static final String TAG = "InformationActivity";

//    private LinearLayout mGallery;

    private TextView mCountryTextView;
    private TextView mYearOfProductionTextView;
    private TextView mTypeOfBulletTextView;
    private TextView mMuzzleVelocity;
    private TextView mEffectiveRangeTextView;
    private TextView mFeedSystemTextView;
    private TextView mLengthTextView;
    private TextView mBarrelLengthTextView;
    private TextView mLoadedWeightTextView;
    private TextView mUnloadedWeightTextView;
    private TextView mRapidFireTextView;
    private TextView mCostTextView;
    private TextView mDescriptionTextView;

    private ImageView mImageView;


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

        initWidgets();
        initToolbar();
//        initHorizontalScrollView();

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

//    private void initHorizontalScrollView() {
//        mGallery = findViewById(R.id.gallery);
//        LayoutInflater inflater = LayoutInflater.from(this);
//
//        for (int i = 0; i < 5; i++) {
//            View view = inflater.inflate(R.layout.image_item, mGallery, false);
//
//            ImageView imageView = view.findViewById(R.id.image_item);
//            imageView.setImageResource(R.drawable.ic_launcher_background);
//
//            mGallery.addView(view);
//        }
//    }

    private void initWidgets() {
        mCountryTextView = findViewById(R.id.country_text_view);
        mYearOfProductionTextView = findViewById(R.id.year_of_production_text_view);
        mTypeOfBulletTextView = findViewById(R.id.type_of_bullet_text_view);
        mMuzzleVelocity = findViewById(R.id.muzzle_velocity_text_view);
        mEffectiveRangeTextView = findViewById(R.id.effective_range_text_view);
        mFeedSystemTextView = findViewById(R.id.feed_system_text_view);
        mLengthTextView = findViewById(R.id.length_text_view);
        mBarrelLengthTextView = findViewById(R.id.barrel_length_text_view);
        mLoadedWeightTextView = findViewById(R.id.weight_loaded_text_view);
        mUnloadedWeightTextView = findViewById(R.id.weight_unloaded_text_view);
        mRapidFireTextView = findViewById(R.id.rapid_fire_text_view);
        mCostTextView = findViewById(R.id.cost_text_view);
        mDescriptionTextView = findViewById(R.id.description_text_view);
        mImageView = findViewById(R.id.image);
    }

    private void getExtras() {

        String country = "", yearOfProduction = "", typeOfBullet = "", maxRange = "",
                effectiveRange = "", feedSystem = "", length = "", barrelLength = "",
                loadedWeight = "", unloadedWeight = "", rapidFire = "", cost = "", imageUrl, description;

        try {
            Bundle arguments = getIntent().getExtras();
            if (arguments != null) {
                country = arguments.get("country").toString();
                yearOfProduction = arguments.get("yearOfProduction").toString();
                typeOfBullet = arguments.get("typeOfBullet").toString();
                maxRange = arguments.get("muzzleVelocity").toString();
                effectiveRange = arguments.get("effectiveRange").toString();
                feedSystem = arguments.get("feedSystem").toString();
                length = arguments.get("length").toString();
                barrelLength = arguments.get("barrelLength").toString();
                loadedWeight = arguments.get("loadedWeight").toString();
                unloadedWeight = arguments.get("unloadedWeight").toString();
                rapidFire = arguments.get("rapidFire").toString();
                cost = arguments.get("cost").toString();
                imageUrl = arguments.get("imageUrl").toString();
                description = arguments.get("description").toString();

                Glide.with(this)
                        .asBitmap()
                        .load(imageUrl)
                        .into(mImageView);

                mCountryTextView.setText(country);
                mYearOfProductionTextView.setText(yearOfProduction);
                mTypeOfBulletTextView.setText(typeOfBullet);
                mMuzzleVelocity.setText(maxRange);
                mEffectiveRangeTextView.setText(effectiveRange);
                mFeedSystemTextView.setText(feedSystem);
                mLengthTextView.setText(length);
                mBarrelLengthTextView.setText(barrelLength);
                mLoadedWeightTextView.setText(loadedWeight);
                mUnloadedWeightTextView.setText(unloadedWeight);
                mRapidFireTextView.setText(rapidFire);
                mCostTextView.setText(cost);
                mDescriptionTextView.setText(description);
            }


        } catch (Exception e) {
            Log.d(TAG, "Error!" + e.getMessage());
        }
    }

}
