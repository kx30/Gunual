package com.example.nikolay.gunual.favorite;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.nikolay.gunual.models.Weapon;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public interface FavoriteDAO {
    String addToFavorite(String sharedValue, Weapon weapon);
    String removeFromFavorite(String weaponPosition, String sharedValue);
//    boolean isFavorite(String id);
}

class SharedPreferencesFavoriteDAO implements FavoriteDAO {

    private static final String FAVORITE_IDS = "favorite_ids";
    private SharedPreferences preferences;

    SharedPreferencesFavoriteDAO(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public String addToFavorite(String sharedValue, Weapon weapon) {
        Gson gson = new Gson();
        if (sharedValue.equals("")) {
            sharedValue += "[" + gson.toJson(weapon);
            sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), "]").toString();
        } else {
            sharedValue = sharedValue.substring(0, sharedValue.length() - 1);
            sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), ",").toString();
            sharedValue += gson.toJson(weapon);
            sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), "]").toString();
        }
        return sharedValue;
//        Set<String> favoriteIds = preferences.getStringSet(FAVORITE_IDS, new HashSet<>());
//        favoriteIds.add(id);
//        preferences.edit()
//                .putStringSet(FAVORITE_IDS, favoriteIds)
//                .commit();
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public String removeFromFavorite(String weaponPosition, String sharedValue) {
        if (sharedValue.indexOf(weaponPosition) - 1 == 0) {
            sharedValue = sharedValue.replace(weaponPosition, "");
            if (sharedValue.charAt(1) == ',') {
                sharedValue = sharedValue.substring(2);
                sharedValue = "[" + sharedValue;
            } else {
                sharedValue = "";
            }
        } // If the object at the ending of the string
        else if (sharedValue.charAt(sharedValue.indexOf(weaponPosition) + weaponPosition.length()) == ']') {
            sharedValue = sharedValue.replace(weaponPosition, "");
            sharedValue = sharedValue.substring(0, sharedValue.length() - 2);
            sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), "]").toString();
        } else {
            sharedValue = sharedValue.substring(0, sharedValue.indexOf(weaponPosition)) +
                    sharedValue.substring(
                            sharedValue.indexOf(weaponPosition) + weaponPosition.length() + 1);
        }
        return sharedValue;
    }
//        Set<String> favoriteIds = preferences.getStringSet(FAVORITE_IDS, new HashSet<>());
//        favoriteIds.remove(id);
//        preferences.edit()
//                .putStringSet(FAVORITE_IDS, favoriteIds)
//                .commit();

//    @Override
//    public boolean isFavorite(String id) {
//        Set<String> favoriteIds = preferences.getStringSet(FAVORITE_IDS, new HashSet<>());
//        return favoriteIds.contains(id);
//    }
}

class SharedPreferencesFavoriteDAOFactory {
    static public SharedPreferencesFavoriteDAO create(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Weapons", Activity.MODE_PRIVATE);
        return new SharedPreferencesFavoriteDAO(preferences);
    }
}