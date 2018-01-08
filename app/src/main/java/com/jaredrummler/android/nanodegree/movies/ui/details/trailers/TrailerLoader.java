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

package com.jaredrummler.android.nanodegree.movies.ui.details.trailers;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.jaredrummler.android.nanodegree.movies.BuildConfig;
import com.jaredrummler.android.nanodegree.movies.tmdb.TmdbApi;
import com.jaredrummler.android.nanodegree.movies.tmdb.TmdbApiClient;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Movie;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Trailer;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.TrailerResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class TrailerLoader extends AsyncTaskLoader<List<Trailer>> {

    public static final int TRAILER_LOADER_ID = 100;

    private List<Trailer> trailers;
    private final Movie movie;

    public TrailerLoader(Context context, Movie movie) {
        super(context);
        this.movie = movie;
    }

    @Override
    protected void onStartLoading() {
        if (this.trailers != null) {
            deliverResult(trailers);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Trailer> loadInBackground() {
        TmdbApi api = TmdbApiClient.INSTANCE;
        Call<TrailerResponse> call = api.fetchTrailers(movie.getId(), BuildConfig.TMDB_API_KEY);
        try {
            Response<TrailerResponse> response = call.execute();
            if (response.isSuccessful()) {
                TrailerResponse body = response.body();
                if (body != null) {
                    return body.getTrailers();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(@Nullable List<Trailer> data) {
        this.trailers = data;
        super.deliverResult(data);
    }
}
