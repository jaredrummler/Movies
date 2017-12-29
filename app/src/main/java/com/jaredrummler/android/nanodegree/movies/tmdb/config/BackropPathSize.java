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

package com.jaredrummler.android.nanodegree.movies.tmdb.config;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.jaredrummler.android.nanodegree.movies.tmdb.model.Movie;

import static com.jaredrummler.android.nanodegree.movies.tmdb.TmdbApi.TMDB_IMAGE_BASE_URL;

/**
 * Supported poster sizes include:
 * <p>
 * <ul>
 * <li>w300</li>
 * <li>w780</li>
 * <li>w1280</li>
 * <li>original</li>
 * </ul>
 *
 * You can retrieve supported image sizes by calling the /configuration API.
 * See: https://developers.themoviedb.org/3/configuration/get-api-configuration
 */
public enum BackropPathSize {
    SMALL("w300"),
    MEDIUM("w780"),
    LARGE("w1280"),
    ORIGINAL("original");

    private final String size;

    BackropPathSize(String size) {
        this.size = size;
    }

    /**
     * Get the movie backdrop image URI.
     *
     * @param movie The movie
     * @return The Uri for the movie backdrop
     */
    public Uri getUri(@NonNull Movie movie) {
        return Uri.parse(TMDB_IMAGE_BASE_URL + size + "/" + movie.getBackdropPath());
    }

}
