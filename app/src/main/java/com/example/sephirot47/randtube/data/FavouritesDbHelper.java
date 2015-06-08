package com.example.sephirot47.randtube.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavouritesDbHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 7;

    static final String DATABASE_NAME = "favourites.db";

    public FavouritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " +
                FavouritesContract.FavouriteEntry.TABLE_NAME + " (" +
                FavouritesContract.FavouriteEntry.COLUMN_URL + " TEXT PRIMARY KEY, " +
                FavouritesContract.FavouriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                FavouritesContract.FavouriteEntry.COLUMN_THUMBNAIL_PATH + " TEXT NOT NULL " +
                " );";
        sqLiteDatabase.execSQL(SQL_CREATE_LOCATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavouritesContract.FavouriteEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
