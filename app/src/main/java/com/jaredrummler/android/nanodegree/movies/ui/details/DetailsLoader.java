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

package com.jaredrummler.android.nanodegree.movies.ui.details;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.jaredrummler.android.nanodegree.movies.BuildConfig;
import com.jaredrummler.android.nanodegree.movies.tmdb.TmdbApiClient;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Movie;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.MovieDetails;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class DetailsLoader extends AsyncTaskLoader<MovieDetails> {

    private MovieDetails movieDetails;
    private final Movie movie;

    public DetailsLoader(Context context, @NonNull Movie movie) {
        super(context);
        this.movie = movie;
    }

    @Override
    protected void onStartLoading() {
        if (movieDetails == null) {
            forceLoad();
        } else {
            deliverResult(movieDetails);
        }
    }

    @Override
    public MovieDetails loadInBackground() {
        Call<MovieDetails> call = TmdbApiClient.INSTANCE.fetchDetails(
                movie.getId(), BuildConfig.TMDB_API_KEY, "videos,reviews"
        );

        MovieDetails details = null;
        try {
            Response<MovieDetails> response = call.execute();
            details = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return details;
    }

    @Override
    public void deliverResult(MovieDetails data) {
        movieDetails = data;
        super.deliverResult(data);
    }

}
