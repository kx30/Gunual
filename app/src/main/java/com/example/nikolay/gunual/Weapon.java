package com.example.nikolay.gunual;

public class Weapon {

    private String title;
    private String country;
    private String yearOfProduction;
    private String typeOfBullet;
    private String effectiveRange;
    private String muzzleVelocity;
    private String length;
    private String barrelLength;
    private String loadedWeight;
    private String unloadedWeight;
    private String rapidFire;
    private String cost;
    private String feedSystem;
    private String description;

    public Weapon() { }

    public Weapon(String title, String country, String yearOfProduction, String typeOfBullet, String effectiveRange, String muzzleVelocity, String length, String barrelLength, String loadedWeight, String unloadedWeight, String rapidFire, String cost, String feedSystem, String description) {
        this.title = title;
        this.country = country;
        this.yearOfProduction = yearOfProduction;
        this.typeOfBullet = typeOfBullet;
        this.effectiveRange = effectiveRange;
        this.muzzleVelocity = muzzleVelocity;
        this.length = length;
        this.barrelLength = barrelLength;
        this.loadedWeight = loadedWeight;
        this.unloadedWeight = unloadedWeight;
        this.rapidFire = rapidFire;
        this.cost = cost;
        this.feedSystem = feedSystem;
        this.description = description;

    }

    public String getTitle() {
        return title;
    }

    public String getCountry() {
        return country;
    }

    public String getYearOfProduction() {
        return yearOfProduction;
    }

    public String getTypeOfBullet() {
        return typeOfBullet;
    }

    public String getEffectiveRange() {
        return effectiveRange;
    }

    public String getMuzzleVelocity() {
        return muzzleVelocity;
    }

    public String getLength() {
        return length;
    }

    public String getBarrelLength() {
        return barrelLength;
    }

    public String getLoadedWeight() {
        return loadedWeight;
    }

    public String getUnloadedWeight() {
        return unloadedWeight;
    }

    public String getRapidFire() {
        return rapidFire;
    }

    public String getCost() {
        return cost;
    }

    public String getFeedSystem() {
        return feedSystem;
    }

    public String getDescription() {
        return description;
    }
}