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

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.jaredrummler.android.nanodegree.movies.R;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Movie;
import com.jaredrummler.android.nanodegree.movies.utils.Utils;

import static com.jaredrummler.android.nanodegree.movies.tmdb.TmdbApi.TMDB_IMAGE_BASE_URL;

/**
 * Supported poster sizes include:
 * <p>
 * <ul>
 * <li>w92</li>
 * <li>w154</li>
 * <li>w185</li>
 * <li>w342</li>
 * <li>w500</li>
 * <li>w780</li>
 * <li>original</li>
 * </ul>
 * <p>
 * You can retrieve supported image sizes by calling the /configuration API.
 * See: https://developers.themoviedb.org/3/configuration/get-api-configuration
 */
public enum PosterPathSize {
    SMALL("w92"),
    MEDIUM("w154"),
    STANDARD("w185"),
    LARGE("w342"),
    XLARGE("w500"),
    XXLARGE("w780"),
    ORIGINAL("original");

    /**
     * Get the recommended poster size based on the screen resolution
     *
     * @param context The application context
     * @return The {@link PosterPathSize} best suited for displaying in a RecyclerView.
     */
    public static PosterPathSize getIdealSize(@NonNull Context context) {
        int columns = context.getResources().getInteger(R.integer.movies_column_count);
        int width = Utils.getScreenWidth(context) / columns;
        if (width > 500) {
            return PosterPathSize.XXLARGE;
        } else if (width > 342 && width <= 500) {
            return PosterPathSize.XLARGE;
        } else if (width > 185 && width <= 342) {
            return PosterPathSize.LARGE;
        } else if (width > 154 && width <= 185) {
            return PosterPathSize.STANDARD;
        } else if (width > 92 && width <= 154) {
            return PosterPathSize.MEDIUM;
        } else if (width > 0 && width <= 92) {
            return PosterPathSize.SMALL;
        }
        return PosterPathSize.STANDARD;
    }

    private final String size;

    PosterPathSize(@NonNull String size) {
        this.size = size;
    }

    /**
     * Get the movie poster image URI.
     *
     * @param movie The movie
     * @return The Uri for the movie poster
     */
    public Uri getUri(@NonNull Movie movie) {
        return Uri.parse(TMDB_IMAGE_BASE_URL + size + "/" + movie.getPosterPath());
    }

}
