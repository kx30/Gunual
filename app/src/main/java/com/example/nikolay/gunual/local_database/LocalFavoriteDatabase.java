package com.example.nikolay.gunual.local_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalFavoriteDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Favorites.db";
    private static final String TABLE_NAME = "favorites";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TITLE";
    private static final String COL_3 = "COUNTRY";
    private static final String COL_4 = "YEAR_OF_PRODUCTION";
    private static final String COL_5 = "TYPE_OF_BULLET";
    private static final String COL_6 = "EFFECTIVE_RANGE";
    private static final String COL_7 = "MUZZLE_VELOCITY";
    private static final String COL_8 = "LENGTH";
    private static final String COL_9 = "BARREL_LENGTH";
    private static final String COL_10 = "WEIGHT";
    private static final String COL_11 = "FEED_SYSTEM";
    private static final String COL_12 = "DESCRIPTION";
    private static final String COL_13 = "IMAGE_URL";

    public LocalFavoriteDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +
                " (ID integer primary key autoincrement, TITLE text, COUNTRY text, YEAR_OF_PRODUCTION text, TYPE_OF_BULLET text," +
                " EFFECTIVE_RANGE text, MUZZLE_VELOCITY text, LENGTH text, BARREL_LENGTH text, " +
                "WEIGHT text, FEED_SYSTEM text, DESCRIPTION text, IMAGE_URL text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addToFavorites(String title, String country, String typeOfBullet, String yearOfProduction, String effectiveRange,
                              String muzzleVelocity, String length, String barrelLength, String weight, String feedSystem,
                              String description, String imageUrl) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, title);
        contentValues.put(COL_3, country);
        contentValues.put(COL_4, yearOfProduction);
        contentValues.put(COL_5, typeOfBullet);
        contentValues.put(COL_6, effectiveRange);
        contentValues.put(COL_7, muzzleVelocity);
        contentValues.put(COL_8, length);
        contentValues.put(COL_9, barrelLength);
        contentValues.put(COL_10, weight);
        contentValues.put(COL_11, feedSystem);
        contentValues.put(COL_12, description);
        contentValues.put(COL_13, imageUrl);
        long result = database.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public void removeFromFavorites(String title) {
        SQLiteDatabase database = getReadableDatabase();
        String query = String.format("DELETE FROM " + TABLE_NAME + " WHERE TITLE LIKE " + "'" + title + "'");
        database.execSQL(query);
    }

    public boolean isFavorite(String title) {
        SQLiteDatabase database = this.getReadableDatabase();
        String query = String.format("SELECT * FROM " + TABLE_NAME + " WHERE TITLE='%s';", title);
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public Cursor getFavoritesWeapons() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = String.format("SELECT * FROM " + TABLE_NAME);
        Cursor res = database.rawQuery(query, null);
        return res;
    }
}