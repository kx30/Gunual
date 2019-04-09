package com.example.nikolay.gunual.information;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.nikolay.gunual.R;
import com.example.nikolay.gunual.browser.BrowserActivity;
import com.example.nikolay.gunual.local_database.LocalFavoriteDatabase;

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
    private LocalFavoriteDatabase mLocalFavoriteDatabase;

    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        mLocalFavoriteDatabase = new LocalFavoriteDatabase(this);
        initWidgets();
        initToolbar();
        getWeaponExtras();
        CheckFieldsForEmptiness();
        Log.d(TAG, "onCreate: created.");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.add_to_favorite) {
            if (isFavorite) {
                Toast.makeText(this, "Remove from favorite", Toast.LENGTH_SHORT).show();
//                mLocalFavoriteDatabase.addToFavorites()
                item.setTitle(R.string.add_to_favorite);
                isFavorite = false;
            } else {
                Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show();
                item.setTitle(R.string.remove_from_favorites);
                isFavorite = true;
            }
            Bundle arguments = getIntent().getExtras();
            String title = arguments.get("title").toString();
            Intent intent = new Intent();
            intent.putExtra("title", title);
            setResult(RESULT_OK, intent);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.information_menu, menu);

        Bundle argument = getIntent().getExtras();
        String title = argument.getString("title");
        Log.d(TAG, "onCreateOptionsMenu: " + title);

        MenuItem addToFavorite = menu.findItem(R.id.add_to_favorite);

        if (mLocalFavoriteDatabase.isFavorite(title)) {
            addToFavorite.setTitle(R.string.remove_from_favorites);
            isFavorite = true;
        }


        return true;
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

        Button buyTheGunButton = findViewById(R.id.buy_gun_button);
        buyTheGunButton.setOnClickListener(view -> {
            Bundle arguments = getIntent().getExtras();
            String title = arguments.getString("title");
            String url = "https://www.gunbroker.com/All/search?Keywords=" + title.replaceAll(" ", "%20");
            Intent intent = new Intent(InformationActivity.this, BrowserActivity.class);
            intent.putExtra("url", url);
            Log.d(TAG, "onClick: " + url);
            startActivity(intent);
        });

        Button buyAmmoButton = findViewById(R.id.buy_ammo_button);
        buyAmmoButton.setOnClickListener(view -> {
            Bundle arguments = getIntent().getExtras();
            String typeOfBullet = arguments.getString("type_of_bullet");
            try {
                typeOfBullet = typeOfBullet.substring(0, typeOfBullet.indexOf("/"));
            } catch (Exception e) {
                Log.d(TAG, "onOptionsItemSelected: " + e);
            }
            String url = "https://www.cheaperthandirt.com/search.do?query=" + typeOfBullet.replaceAll("×", "x") + "%20ammo";
            Intent intent = new Intent(InformationActivity.this, BrowserActivity.class);
            intent.putExtra("url", url);
            startActivity(intent);
        });
    }


    private void getWeaponExtras() {
        String title, country, yearOfProduction, typeOfBullet,
        maxRange, effectiveRange, feedSystem, length, barrelLength,
        weight, imageUrl, description;

        Bundle arguments = getIntent().getExtras();
        country = arguments.getString("country");
        title = arguments.getString("title");
        yearOfProduction = arguments.getString("year_of_production");
        typeOfBullet = arguments.getString("type_of_bullet");
        maxRange = arguments.getString("muzzle_velocity");
        effectiveRange = arguments.getString("effective_range");
        feedSystem = arguments.getString("feed_system");
        length = arguments.getString("length");
        barrelLength = arguments.getString("barrel_length");
        weight = arguments.getString("weight");
        imageUrl = arguments.getString("image_url");
        description = arguments.getString("description");

        Glide.with(this)
                .asBitmap()
                .load("https:" + imageUrl)
                .into(mImageView);

        if (mImageView.getDrawable() == null) {
            Glide.with(this)
                    .asBitmap()
                    .load("http:" + imageUrl)
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


    private void CheckFieldsForEmptiness() {
        if (mWeightTextView.getText().equals("")) {
            TableRow weightTableRow = findViewById(R.id.weight_table_row);
            weightTableRow.setVisibility(View.GONE);
        }
        if (mYearOfProductionTextView.getText().equals("")) {
            TableRow yearOfProductionTableRow = findViewById(R.id.year_of_production_table_row);
            yearOfProductionTableRow.setVisibility(View.GONE);
        }
        if (mTypeOfBulletTextView.getText().equals("")) {
            TableRow typeOfBulletTableRow = findViewById(R.id.type_of_bullet_table_row);
            typeOfBulletTableRow.setVisibility(View.GONE);
        }
        if (mMuzzleVelocityTextView.getText().equals("")) {
            TableRow muzzleVelocityTableRow = findViewById(R.id.muzzle_velocity_table_row);
            muzzleVelocityTableRow.setVisibility(View.GONE);
        }
        if (mEffectiveRangeTextView.getText().equals("")) {
            TableRow effectiveRangeTableRow = findViewById(R.id.effective_range_table_row);
            effectiveRangeTableRow.setVisibility(View.GONE);
        }
        if (mFeedSystemTextView.getText().equals("")) {
            TableRow feedSystemTableRow = findViewById(R.id.feed_system_table_row);
            feedSystemTableRow.setVisibility(View.GONE);
        }
        if (mLengthTextView.getText().equals("")) {
            TableRow lengthTableRow = findViewById(R.id.length_table_row);
            lengthTableRow.setVisibility(View.GONE);
        }
        if (mBarrelLengthTextView.getText().equals("")) {
            TableRow barrelLengthTableRow = findViewById(R.id.barrel_length_table_row);
            barrelLengthTableRow.setVisibility(View.GONE);
        }
    }
}