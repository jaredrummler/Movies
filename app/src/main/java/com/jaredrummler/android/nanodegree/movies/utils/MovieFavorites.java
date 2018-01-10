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

package com.jaredrummler.android.nanodegree.movies.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;

import com.jaredrummler.android.nanodegree.movies.tmdb.db.MovieContract.MovieEntry;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Movie;

import java.util.Arrays;

import static com.jaredrummler.android.nanodegree.movies.tmdb.db.MovieContract.MovieEntry.CONTENT_URI;

public class MovieFavorites {

    private final Context context;

    public MovieFavorites(Context context) {
        this.context = context.getApplicationContext();
    }

    /**
     * Check if a movie is in the favorites database.
     *
     * @param movie The movie
     * @return {@code true} if the movie is a favorite
     */
    public boolean isFavorite(Movie movie) {
        try {
            ContentResolver resolver = context.getContentResolver();
            String[] projection = {MovieEntry.COLUMN_MID};
            String selection = MovieEntry.COLUMN_MID + "=?";
            String[] selectionArgs = {Integer.toString(movie.getId())};
            Cursor query = resolver.query(CONTENT_URI,
                    projection, selection, selectionArgs, null);
            if (query == null) return false;
            try {
                return query.moveToFirst();
            } finally {
                query.close();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Save a movie to the movies DB.
     *
     * @param movie The movie to favorite
     */
    public void save(Movie movie) {
        ContentResolver resolver = context.getContentResolver();
        try {
            ContentValues values = new ContentValues();
            values.put(MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
            values.put(MovieEntry.COLUMN_ADULT, movie.getAdult());
            values.put(MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
            values.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            values.put(MovieEntry.COLUMN_GENRE_IDS, Arrays.toString(movie.getGenreIds().toArray()));
            values.put(MovieEntry.COLUMN_MID, movie.getId());
            values.put(MovieEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
            values.put(MovieEntry.COLUMN_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
            values.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
            values.put(MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdropPath());
            values.put(MovieEntry.COLUMN_POPULARITY, movie.getPopularity());
            values.put(MovieEntry.COLUMN_VOTE_COUNT, movie.getVoteCount());
            values.put(MovieEntry.COLUMN_VIDEO, movie.getVideo());
            values.put(MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
            resolver.insert(CONTENT_URI, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove a movie from the favorites DB.
     *
     * @param movie The movie to remove
     * @return {@code true} if the movie was successfully deleted from the database.
     */
    public boolean remove(Movie movie) {
        ContentResolver resolver = context.getContentResolver();
        String where = MovieEntry.COLUMN_MID + "=?";
        String[] whereClause = {Integer.toString(movie.getId())};
        int delete = resolver.delete(CONTENT_URI, where, whereClause);
        return delete > 0;
    }

}
