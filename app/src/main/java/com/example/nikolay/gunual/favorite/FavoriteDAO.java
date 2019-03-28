package com.example.nikolay.gunual.favorite;


import android.content.Intent;

import com.example.nikolay.gunual.R;
import com.example.nikolay.gunual.models.Weapon;
import com.google.gson.Gson;

import java.util.ArrayList;

public interface FavoriteDAO {

    default String returnerFavoriteSharedPreferencesString(Boolean isFavorite, String sharedValue, String weaponPosition, Intent data, ArrayList<Weapon> weapons) {
        for (int i = 0; i < weapons.size(); i++) {
            if (data.getStringExtra("url").equals(weapons.get(i).getImageUrl())) {
                Gson gson = new Gson();

                if (!isFavorite) {
                    if (sharedValue.contains(weaponPosition)) {
                        // If the first object in the string
                        if (sharedValue.indexOf(weaponPosition) - 1 == 0) {
                            sharedValue = sharedValue.replace(weaponPosition, "");
                            if (sharedValue.charAt(1) == ',') {
                                sharedValue = sharedValue.substring(2);
                                sharedValue = "[" + sharedValue;
                            } else {
                                sharedValue = "";
                            }
                        } else if (sharedValue.charAt(sharedValue.indexOf(weaponPosition) + weaponPosition.length()) == ']') {
                            // If the last object in the string
                            sharedValue = sharedValue.replace(weaponPosition, "");
                            sharedValue = sharedValue.substring(0, sharedValue.length() - 2);
                            sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), "]").toString();
                        } else {
                            sharedValue = sharedValue.substring(0, sharedValue.indexOf(weaponPosition)) +
                                    sharedValue.substring(
                                            sharedValue.indexOf(weaponPosition) + weaponPosition.length() + 1,
                                            sharedValue.length());
                        }
                        weapons.get(i).setDrawable(R.drawable.unfavorite_star);
                    }
                } else {
                    weapons.get(i).setDrawable(R.drawable.favorite_star);
                    if (sharedValue.equals("")) {
                        sharedValue += "[" + gson.toJson(weapons.get(i));
                        sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), "]").toString();
                    } else {
                        sharedValue = sharedValue.substring(0, sharedValue.length() - 1);
                        sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), ",").toString();
                        sharedValue += gson.toJson(weapons.get(i));
                        sharedValue = new StringBuffer(sharedValue).insert(sharedValue.length(), "]").toString();
                    }
                }
            }
        }
        return sharedValue;
    }
}
