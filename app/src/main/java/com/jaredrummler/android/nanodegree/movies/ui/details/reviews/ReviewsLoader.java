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

package com.jaredrummler.android.nanodegree.movies.ui.details.reviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.jaredrummler.android.nanodegree.movies.BuildConfig;
import com.jaredrummler.android.nanodegree.movies.tmdb.TmdbApi;
import com.jaredrummler.android.nanodegree.movies.tmdb.TmdbApiClient;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Movie;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Review;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.ReviewResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class ReviewsLoader extends AsyncTaskLoader<List<Review>> {

    public static final int REVIEWS_LOADER_ID = 101;

    private List<Review> reviews;
    private final Movie movie;

    public ReviewsLoader(Context context, Movie movie) {
        super(context);
        this.movie = movie;
    }

    @Override
    protected void onStartLoading() {
        if (this.reviews != null) {
            deliverResult(reviews);
        } else {
            forceLoad();
        }
    }

    @Override
    public List<Review> loadInBackground() {
        TmdbApi api = TmdbApiClient.INSTANCE;
        Call<ReviewResponse> call = api.fetchReviews(movie.getId(), BuildConfig.TMDB_API_KEY);
        try {
            Response<ReviewResponse> response = call.execute();
            if (response.isSuccessful()) {
                ReviewResponse body = response.body();
                if (body != null) {
                    return body.getReviews();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deliverResult(@Nullable List<Review> data) {
        this.reviews = data;
        super.deliverResult(data);
    }
}
