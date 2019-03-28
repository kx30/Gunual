package com.example.nikolay.gunual.models;

import android.graphics.drawable.Drawable;

public class Weapon {

    private String mTitle;
    private String mCountry;
    private String mYearOfProduction;
    private String mTypeOfBullet;
    private String mEffectiveRange;
    private String mMuzzleVelocity;
    private String mLength;
    private String mBarrelLength;
    private String mWeight;
    private String mFeedSystem;
    private String mDescription;
    private String mImageUrl;

    private int mDrawable;

    public Weapon() {
    }

    public Weapon(String title, String country, String yearOfProduction, String typeOfBullet, String effectiveRange, String muzzleVelocity, String length, String barrelLength, String weight, String feedSystem, String description, String imageUrl) {
        this.mTitle = title;
        this.mCountry = country;
        this.mYearOfProduction = yearOfProduction;
        this.mTypeOfBullet = typeOfBullet;
        this.mEffectiveRange = effectiveRange;
        this.mMuzzleVelocity = muzzleVelocity;
        this.mLength = length;
        this.mBarrelLength = barrelLength;
        this.mWeight = weight;
        this.mFeedSystem = feedSystem;
        this.mDescription = description;
        this.mImageUrl = imageUrl;
    }

    public int getDrawable() {
        return mDrawable;
    }

    public void setDrawable(int drawable) {
        mDrawable = drawable;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getCountry() {
        return mCountry;
    }

    public String getYearOfProduction() {
        return mYearOfProduction;
    }

    public String getTypeOfBullet() {
        return mTypeOfBullet;
    }

    public String getEffectiveRange() {
        return mEffectiveRange;
    }

    public String getMuzzleVelocity() {
        return mMuzzleVelocity;
    }

    public String getLength() {
        return mLength;
    }

    public String getBarrelLength() {
        return mBarrelLength;
    }

    public String getWeight() {
        return mWeight;
    }

    public String getFeedSystem() {
        return mFeedSystem;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}