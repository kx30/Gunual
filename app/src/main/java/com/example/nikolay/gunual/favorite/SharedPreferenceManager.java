package com.example.nikolay.gunual.favorite;

import android.support.v7.app.AppCompatActivity;

import com.example.nikolay.gunual.models.Weapon;
import com.google.gson.Gson;

public abstract class SharedPreferenceManager extends AppCompatActivity {

    public String removeTheFavoriteFromSharedPreference(String weaponPosition, String sharedValue) {
        // If the object at the beginning of the string
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

    public String addTheFavoriteToSharedPreference(String sharedValue, Weapon weapon) {
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
    }

}
