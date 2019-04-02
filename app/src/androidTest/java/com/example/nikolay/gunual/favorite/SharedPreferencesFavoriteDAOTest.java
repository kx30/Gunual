package com.example.nikolay.gunual.favorite;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SharedPreferencesFavoriteDAOTest {

    private SharedPreferencesFavoriteDAO mFavoriteDAO;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getContext();
        SharedPreferences preferences = context.getSharedPreferences("Test", Activity.MODE_PRIVATE);
        mFavoriteDAO = new SharedPreferencesFavoriteDAO(preferences);
    }

    @Test
    public void addToFavorite() {
        // DO

        String my_id = "my_id";
        String my_id_other = "my_id_other";

        mFavoriteDAO.addToFavorite(my_id);

        // CHECK

        Assert.assertTrue(mFavoriteDAO.isFavorite(my_id));
        Assert.assertFalse(mFavoriteDAO.isFavorite(my_id_other));
    }

    @Test
    public void removeFromFavorite() {
        // DO

        mFavoriteDAO.addToFavorite("my_id");
        Assert.assertTrue(mFavoriteDAO.isFavorite("my_id"));
        mFavoriteDAO.removeFromFavorite("my_id");

        // CHECK

        Assert.assertFalse(mFavoriteDAO.isFavorite("my_id"));
    }
}