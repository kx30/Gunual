package com.example.nikolay.gunual;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class InformationActivity extends AppCompatActivity {

    private static final String TAG = "InformationActivity";

    private TextView mTitleTextView;
    private TextView mCountryTextView;
    private TextView mYearOfProductionTextView;
    private TextView mTypeOfBulletTextView;
    private TextView mMuzzleVelocityTextView;
    private TextView mEffectiveRangeTextView;
    private TextView mFeedSystemTextView;
    private TextView mLengthTextView;
    private TextView mBarrelLengthTextView;
    private TextView mWeightTextView;
    private TextView mDescriptionTextView;

    private ImageView mImageView;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        initWidgets();
        initToolbar();

        getExtras();

        isFieldEmpty();

        Log.d(TAG, "onCreate: created.");
    }


    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        Log.d(TAG, "initToolbar: initialized.");
    }


    private void initWidgets() {
        mCountryTextView = findViewById(R.id.country_text_view);
        mTitleTextView = findViewById(R.id.title_text_view);
        mYearOfProductionTextView = findViewById(R.id.year_of_production_text_view);
        mTypeOfBulletTextView = findViewById(R.id.type_of_bullet_text_view);
        mMuzzleVelocityTextView = findViewById(R.id.muzzle_velocity_text_view);
        mEffectiveRangeTextView = findViewById(R.id.effective_range_text_view);
        mFeedSystemTextView = findViewById(R.id.feed_system_text_view);
        mLengthTextView = findViewById(R.id.length_text_view);
        mBarrelLengthTextView = findViewById(R.id.barrel_length_text_view);
        mWeightTextView = findViewById(R.id.weight_text_view);
        mDescriptionTextView = findViewById(R.id.description_text_view);
        mImageView = findViewById(R.id.image);
    }


    private void getExtras() {
        String title = "", country = "", yearOfProduction = "", typeOfBullet = "", maxRange = "",
                effectiveRange = "", feedSystem = "", length = "", barrelLength = "",
                weight = "", imageUrl = "", description = "";

        Bundle arguments = getIntent().getExtras();
        country = arguments.get("country").toString();
        title = arguments.get("title").toString();
        yearOfProduction = arguments.get("year_of_production").toString();
        typeOfBullet = arguments.get("type_of_bullet").toString();
        maxRange = arguments.get("muzzle_velocity").toString();
        effectiveRange = arguments.get("effective_range").toString();
        feedSystem = arguments.get("feed_system").toString();
        length = arguments.get("length").toString();
        barrelLength = arguments.get("barrel_length").toString();
        weight = arguments.get("weight").toString();
        imageUrl = arguments.get("image_url").toString();
        description = arguments.get("description").toString();

        if (imageUrl.equals("")) {
            mImageView.setImageResource(R.drawable.not_available_image);
        } else {
            Glide.with(this)
                    .asBitmap()
                    .load("https:" + imageUrl)
                    .into(mImageView);
        }

        mCountryTextView.setText(country);
        mTitleTextView.setText(title);
        mYearOfProductionTextView.setText(yearOfProduction);
        mTypeOfBulletTextView.setText(typeOfBullet);
        mMuzzleVelocityTextView.setText(maxRange);
        mEffectiveRangeTextView.setText(effectiveRange);
        mFeedSystemTextView.setText(feedSystem);
        mLengthTextView.setText(length);
        mBarrelLengthTextView.setText(barrelLength);
        mWeightTextView.setText(weight);
        mDescriptionTextView.setText(description);

    }


    private void isFieldEmpty() {

        if (mWeightTextView.getText().equals("")) {
            TableRow weightTableRow = (TableRow) findViewById(R.id.weight_table_row);
            weightTableRow.setVisibility(View.GONE);
        }

        if (mYearOfProductionTextView.getText().equals("")) {
            TableRow yearOfProductionTableRow = (TableRow) findViewById(R.id.year_of_production_table_row);
            yearOfProductionTableRow.setVisibility(View.GONE);
        }

        if (mTypeOfBulletTextView.getText().equals("")) {
            TableRow typeOfBulletTableRow = (TableRow) findViewById(R.id.type_of_bullet_table_row);
            typeOfBulletTableRow.setVisibility(View.GONE);
        }

        if (mMuzzleVelocityTextView.getText().equals("")) {
            TableRow muzzleVelocityTableRow = (TableRow) findViewById(R.id.muzzle_velocity_table_row);
            muzzleVelocityTableRow.setVisibility(View.GONE);
        }

        if (mEffectiveRangeTextView.getText().equals("")) {
            TableRow effectiveRangeTableRow = (TableRow) findViewById(R.id.effective_range_table_row);
            effectiveRangeTableRow.setVisibility(View.GONE);
        }

        if (mFeedSystemTextView.getText().equals("")) {
            TableRow feedSystemTableRow = (TableRow) findViewById(R.id.feed_system_table_row);
            feedSystemTableRow.setVisibility(View.GONE);
        }
        if (mLengthTextView.getText().equals("")) {
            TableRow lengthTableRow = (TableRow) findViewById(R.id.length_table_row);
            lengthTableRow.setVisibility(View.GONE);
        }

        if (mBarrelLengthTextView.getText().equals("")) {
            TableRow barrelLengthTableRow = (TableRow) findViewById(R.id.barrel_length_table_row);
            barrelLengthTableRow.setVisibility(View.GONE);
        }

    }

}