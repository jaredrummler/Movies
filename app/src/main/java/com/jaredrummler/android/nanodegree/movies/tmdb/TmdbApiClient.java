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

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * API Client for The Movie DB
 */
public final class TmdbApiClient {

    public static final TmdbApi INSTANCE;

    static {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TmdbApi.TMDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        INSTANCE = retrofit.create(TmdbApi.class);
    }

}
