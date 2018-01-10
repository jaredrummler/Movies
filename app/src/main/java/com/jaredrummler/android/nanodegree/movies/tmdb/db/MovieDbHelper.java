/*
 * Copyright (C) 2018 Jared Rummler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jaredrummler.android.nanodegree.movies.tmdb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * The database to store favorite movies
 */
public class MovieDbHelper extends SQLiteOpenHelper implements MovieContract.MovieEntry {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_POSTER_PATH + " TEXT, " +
                COLUMN_ADULT + " INTEGER, " +
                COLUMN_OVERVIEW + " TEXT, " +
                COLUMN_RELEASE_DATE + " TEXT, " +
                COLUMN_GENRE_IDS + " TEXT, " +
                COLUMN_MID + " INTEGER NOT NULL, " +
                COLUMN_ORIGINAL_TITLE + " TEXT, " +
                COLUMN_ORIGINAL_LANGUAGE + " TEXT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_BACKDROP_PATH + " TEXT, " +
                COLUMN_POPULARITY + " REAL, " +
                COLUMN_VOTE_COUNT + " TEXT, " +
                COLUMN_VIDEO + " TEXT, " +
                COLUMN_VOTE_AVERAGE + " REAL" +
                ");";
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
