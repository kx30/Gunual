package com.example.nikolay.gunual.favorite;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public interface FavoriteDAO {

    void addToFavorite(String id);

    void removeFromFavorite(String id);

    boolean isFavorite(String id);
}

class SharedPreferencesFavoriteDAO implements FavoriteDAO {

    private static final String FAVORITE_IDS = "favorite_ids";
    private SharedPreferences preferences;

    public SharedPreferencesFavoriteDAO(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void addToFavorite(String id) {
        Set<String> favoriteIds = preferences.getStringSet(FAVORITE_IDS, new HashSet<>());
        favoriteIds.add(id);
        preferences.edit()
                .putStringSet(FAVORITE_IDS, favoriteIds)
                .commit();
    }

    @SuppressLint("ApplySharedPref")
    @Override
    public void removeFromFavorite(String id) {
        Set<String> favoriteIds = preferences.getStringSet(FAVORITE_IDS, new HashSet<>());
        favoriteIds.remove(id);
        preferences.edit()
                .putStringSet(FAVORITE_IDS, favoriteIds)
                .commit();
    }

    @Override
    public boolean isFavorite(String id) {
        Set<String> favoriteIds = preferences.getStringSet(FAVORITE_IDS, new HashSet<>());
        return favoriteIds.contains(id);
    }
}

class SharedPreferencesFavoriteDAOFactory {
    static public SharedPreferencesFavoriteDAO create(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Weapons", Activity.MODE_PRIVATE);
        return new SharedPreferencesFavoriteDAO(preferences);
    }
}