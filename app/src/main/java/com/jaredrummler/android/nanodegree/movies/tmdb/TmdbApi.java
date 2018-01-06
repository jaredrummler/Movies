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

package com.jaredrummler.android.nanodegree.movies.tmdb;

import com.jaredrummler.android.nanodegree.movies.tmdb.model.MoviesResponse;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.ReviewResponse;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The Movie DB API
 * <p>
 * See: https://developers.themoviedb.org
 */
public interface TmdbApi {

    String TMDB_BASE_URL = "https://api.themoviedb.org/";

    String TMDB_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";

    @GET("3/movie/{sort_by}")
    Call<MoviesResponse> fetchMovies(
            @Path("sort_by") String sortBy,
            @Query("api_key") String apiKey
    );

    @GET("3/movie/{sort_by}")
    Call<MoviesResponse> fetchMovies(
            @Path("sort_by") String sortBy,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("3/movie/{movie_id}/videos")
    Call<TrailerResponse> fetchTrailers(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    @GET("3/movie/{movie_id}/reviews")
    Call<ReviewResponse> fetchReviews(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    @GET("3/movie/{movie_id}/reviews")
    Call<ReviewResponse> fetchReviews(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

}
