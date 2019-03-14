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

    private Button mBuyTheGunButton;
    private Button mBuyAmmoButton;

    private ImageView mImageView;

    private boolean isFavorite;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        if (item.getItemId() == R.id.add_to_favorite) {
            if (isFavorite) {
                Toast.makeText(this, "Remove from favorite", Toast.LENGTH_SHORT).show();
                item.setTitle(R.string.add_to_favorite);
                isFavorite = false;
            } else {
                Toast.makeText(this, "Added to favorite", Toast.LENGTH_SHORT).show();
                item.setTitle(R.string.remove_from_favorites);
                isFavorite = true;
            }
            Bundle arguments = getIntent().getExtras();
            String imageUrl = arguments.get("image_url").toString();
            Intent intent = new Intent();
            intent.putExtra("isFavorite", isFavorite);
            intent.putExtra("url", imageUrl);
            setResult(RESULT_OK, intent);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.information_menu, menu);

        Bundle argument = getIntent().getExtras();
        Integer drawable = argument.getInt("drawable");

        MenuItem addToFavorite = menu.findItem(R.id.add_to_favorite);
        if (drawable == R.drawable.favorite_star) {
            addToFavorite.setTitle(R.string.remove_from_favorites);
            isFavorite = true;
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

        mBuyTheGunButton = findViewById(R.id.buy_gun_button);
        mBuyTheGunButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle arguments = getIntent().getExtras();
                String title = arguments.getString("title");
                String url = "https://www.gunbroker.com/All/search?Keywords=" + title.replaceAll(" ", "%20");
                Intent intent = new Intent(InformationActivity.this, BrowserActivity.class);
                intent.putExtra("url", url);
                Log.d(TAG, "onClick: " + url);
                startActivity(intent);
            }
        });

        mBuyAmmoButton = findViewById(R.id.buy_ammo_button);
        mBuyAmmoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
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


    private void isFieldEmpty() {

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