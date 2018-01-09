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

package com.jaredrummler.android.nanodegree.movies.ui.movies;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.jaredrummler.android.nanodegree.movies.BuildConfig;
import com.jaredrummler.android.nanodegree.movies.tmdb.TmdbApiClient;
import com.jaredrummler.android.nanodegree.movies.tmdb.db.MovieContract.MovieEntry;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Movie;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.MoviesResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Response;

public class MovieLoader extends AsyncTaskLoader<MoviesResponse> {

    private static final String TAG = "MovieLoader";

    private final MovieOrder movieOrder;
    private MoviesResponse response;

    public MovieLoader(@NonNull Context context, @NonNull MovieOrder movieOrder) {
        super(context);
        this.movieOrder = movieOrder;
    }

    @Override
    protected void onStartLoading() {
        if (response == null) {
            forceLoad();
        } else {
            deliverResult(response);
        }
    }

    @Nullable
    @Override
    public MoviesResponse loadInBackground() {
        if (movieOrder == MovieOrder.FAVORITES) {
            List<Movie> movies = getMoviesFromDatabase();
            if (movies == null) return null;
            MoviesResponse response = new MoviesResponse();
            response.setPage(1);
            response.setTotalPages(1);
            response.setResults(movies);
            response.setTotalResults(movies.size());
            return response;
        }

        Call<MoviesResponse> call = TmdbApiClient.INSTANCE.fetchMovies(
                movieOrder.path, BuildConfig.TMDB_API_KEY
        );

        try {
            Response<MoviesResponse> response = call.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(TAG, "Error getting response from API", e);
        }

        return null;
    }

    @Override
    public void deliverResult(@Nullable MoviesResponse data) {
        response = data;
        super.deliverResult(data);
    }

    private List<Movie> getMoviesFromDatabase() {
        Cursor cursor = getContext().getContentResolver().query(MovieEntry.CONTENT_URI,
                null, null, null, MovieEntry._ID + " DESC");
        try {
            if (cursor == null) {
                return null;
            }
            List<Movie> movies = new ArrayList<>();
            while (cursor.moveToNext()) {
                Movie movie = fromDatabase(cursor);
                movies.add(movie);
            }
            return movies;
        } catch (SQLiteException e) {
            Log.e(TAG, "Error loading movies from database", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    private Movie fromDatabase(Cursor cursor) {
        Movie movie = new Movie();
        movie.setPosterPath(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_POSTER_PATH)));
        movie.setAdult(cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_ADULT)) > 0);
        movie.setOverview(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_OVERVIEW)));
        movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_RELEASE_DATE)));
        List<Integer> ids = new ArrayList<>();
        String genre = cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_GENRE_IDS));
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(genre);
        while (matcher.find()) {
            ids.add(Integer.parseInt(matcher.group(0)));
        }
        movie.setGenreIds(ids);
        movie.setId(cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_ID)));
        movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_ORIGINAL_TITLE)));
        movie.setOriginalLanguage(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_ORIGINAL_LANGUAGE)));
        movie.setTitle(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_TITLE)));
        movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(MovieEntry.COLUMN_BACKDROP_PATH)));
        movie.setPopularity(cursor.getDouble(cursor.getColumnIndex(MovieEntry.COLUMN_POPULARITY)));
        movie.setVoteCount(cursor.getInt(cursor.getColumnIndex(MovieEntry.COLUMN_VOTE_COUNT)));
        movie.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(MovieEntry.COLUMN_VOTE_AVERAGE)));
        return movie;
    }

}
