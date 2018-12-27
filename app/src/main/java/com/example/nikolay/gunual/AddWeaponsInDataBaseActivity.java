package com.example.nikolay.gunual;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddWeaponsInDataBaseActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "AddWeaponsInDataBaseAct";

    private EditText mTitleEditText;
    private EditText mCountryEditText;
    private EditText mYearOfProductionEditText;
    private EditText mTypeOfBulletEditText;
    private EditText mEffectiveRangeEditText;
    private EditText mMaxRangeEditText;
    private EditText mLengthEditText;
    private EditText mBarrelLengthEditText;
    private EditText mLoadedWeightEditText;
    private EditText mUnloadedWeightEditText;
    private EditText mRapidFireEditText;
    private EditText mCostEditText;
    private EditText mFeedSystemEditText;

    private FirebaseFirestore db;

    @Override
    public void onClick(View view) {
        saveWeapon();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weapons_in_data_base);

        db = FirebaseFirestore.getInstance();

        mTitleEditText = findViewById(R.id.weapon_title_edit_text);
        mCountryEditText = findViewById(R.id.country_edit_text);
        mYearOfProductionEditText = findViewById(R.id.year_of_production_edit_text);
        mTypeOfBulletEditText = findViewById(R.id.type_of_bullet_edit_text);
        mEffectiveRangeEditText = findViewById(R.id.effective_range_edit_text);
        mMaxRangeEditText = findViewById(R.id.max_range_edit_text);
        mLengthEditText = findViewById(R.id.length_edit_text);
        mBarrelLengthEditText = findViewById(R.id.barrel_length_edit_text);
        mLoadedWeightEditText = findViewById(R.id.weight_loaded_edit_text);
        mUnloadedWeightEditText = findViewById(R.id.weight_unloaded_edit_text);
        mRapidFireEditText = findViewById(R.id.rapid_fire_edit_text);
        mCostEditText = findViewById(R.id.cost_edit_text);
        mFeedSystemEditText = findViewById(R.id.feed_system_edit_text);

        findViewById(R.id.save_data_button).setOnClickListener(this);

        Intent intent = getIntent();
        Log.d(TAG, "onCreate: " + intent.getStringExtra("Weapon"));
        Log.d(TAG, "onCreate: Created.");
    }

    private void saveWeapon() {
        Log.d(TAG, "saveWeapon: Called.");
        String title = mTitleEditText.getText().toString().trim();
        String country = mCountryEditText.getText().toString().trim();
        String yearOfProduction = mYearOfProductionEditText.getText().toString().trim();
        String typeOfBullet = mTypeOfBulletEditText.getText().toString().trim();
        String effectiveRange = mEffectiveRangeEditText.getText().toString().trim();
        String maxRange = mMaxRangeEditText.getText().toString().trim();
        String length = mLengthEditText.getText().toString().trim();
        String barrelLength = mBarrelLengthEditText.getText().toString().trim();
        String loadedWeight = mLoadedWeightEditText.getText().toString().trim();
        String unloadedWeight = mUnloadedWeightEditText.getText().toString().trim();
        String rapidFire = mRapidFireEditText.getText().toString().trim();
        String cost = mCostEditText.getText().toString().trim();
        String feedSystem = mFeedSystemEditText.getText().toString().trim();
        String description = "";

        CollectionReference dbWeapons = db.collection("weapons")
                .document("kind_of_weapon")
                .collection(getIntent().getStringExtra("Weapon"));

        Log.d(TAG, "saveWeapon: " + getIntent().getStringExtra("Weapon"));
        
        Weapon weapon = new Weapon(
                title,
                country,
                yearOfProduction,
                typeOfBullet,
                effectiveRange,
                maxRange,
                length,
                barrelLength,
                loadedWeight,
                unloadedWeight,
                rapidFire,
                cost,
                feedSystem,
                description
        );
        
        dbWeapons.add(weapon)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(AddWeaponsInDataBaseActivity.this, "Weapon added!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddWeaponsInDataBaseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        Log.d(TAG, "saveWeapon: Executed.");
    }

}

//todo to fill database
//todo to make images
//todo to make check on empty fields