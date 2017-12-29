/*
 * Copyright (C) 2017 Jared Rummler
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

package com.jaredrummler.android.nanodegree.movies.ui.movies.tasks;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jaredrummler.android.nanodegree.movies.BuildConfig;
import com.jaredrummler.android.nanodegree.movies.tmdb.TmdbApiClient;
import com.jaredrummler.android.nanodegree.movies.tmdb.config.MovieSortOrder;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.MoviesResponse;
import com.jaredrummler.android.nanodegree.movies.ui.movies.MoviesView;
import com.jaredrummler.android.nanodegree.movies.utils.Prefs;

import java.io.IOException;
import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Response;

/**
 * An {@link AsyncTask} to fetch movies using The Movie DB API.
 */
public class FetchMoviesTask extends AsyncTask<MovieSortOrder, Void, MoviesResponse> {

    private static final String TAG = "FetchMoviesTask";

    private final WeakReference<MoviesView> moviesView;

    public FetchMoviesTask(@NonNull MoviesView callback) {
        moviesView = new WeakReference<>(callback);
    }

    @Override
    protected MoviesResponse doInBackground(MovieSortOrder... params) {
        MovieSortOrder sortOrder = params.length == 0 ? Prefs.DEFAULT_SORT_ORDER : params[0];

        Call<MoviesResponse> call = TmdbApiClient.INSTANCE.fetchMovies(
                sortOrder.path, BuildConfig.TMDB_API_KEY
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
    protected void onPostExecute(MoviesResponse response) {
        MoviesView callback = moviesView.get();
        if (callback != null) {
            if (response != null && response.getResults() != null) {
                callback.onFetchedMovies(response.getResults());
            } else {
                callback.onFailedToFetchMovies(null);
            }
        }
    }

}
