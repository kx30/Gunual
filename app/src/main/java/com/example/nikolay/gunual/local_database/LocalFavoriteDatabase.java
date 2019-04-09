package com.example.nikolay.gunual.local_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalFavoriteDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Favorites.db";
    public static final String TABLE_NAME = "favorites";
    public static final String COL_1 = "TITLE";
    public static final String COL_2 = "COUNTRY";
    public static final String COL_3 = "TYPE_OF_BULLET";
    public static final String COL_4 = "YEAR_OF_PRODUCTION";

    public LocalFavoriteDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase database = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME + " (TITLE text, COUNTRY text, TYPE_OF_BULLET text, YEAR_OF_PRODUCTION text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String title, String country, String typeOfBullet, String yearOfProduction) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, title);
        contentValues.put(COL_2, country);
        contentValues.put(COL_3, typeOfBullet);
        contentValues.put(COL_4, yearOfProduction);
        long result = database.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }
}
