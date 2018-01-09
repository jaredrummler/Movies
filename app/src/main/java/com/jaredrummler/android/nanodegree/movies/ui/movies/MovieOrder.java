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

import android.support.annotation.IdRes;

import com.jaredrummler.android.nanodegree.movies.R;

/**
 * The type of movie list to retrieve via the TMDB API.
 * See: https://developers.themoviedb.org/3
 */
public enum MovieOrder {
    /**
     * Get a list of the current popular movies on TMDb. This list updates daily.
     * See: https://developers.themoviedb.org/3/movies/get-popular-movies
     */
    POPULAR("popular", R.id.action_sort_popular),
    /**
     * Get the top rated movies on TMDb.
     * See: https://developers.themoviedb.org/3/movies/get-top-rated-movies
     */
    TOP_RATED("top_rated", R.id.action_sort_top_rated),
    /**
     * Get a list of upcoming movies in theatres.
     * See: https://developers.themoviedb.org/3/movies/get-upcoming
     */
    UPCOMING("upcoming", R.id.action_sort_upcoming),
    /**
     * Get a list of movies in theatres.
     * See: https://developers.themoviedb.org/3/movies/get-now-playing
     */
    NOW_PLAYING("now_playing", R.id.action_sort_now_playing),
    /**
     * Favorites saved by the user
     */
    FAVORITES("favorites", R.id.action_sort_favorites);

    /**
     * Get the movie sort order based on the menu id
     *
     * @param id The resource id
     * @return The sort order
     */
    public static MovieOrder idOf(@IdRes int id) {
        for (MovieOrder sortOrder : MovieOrder.values()) {
            if (sortOrder.menuId == id) {
                return sortOrder;
            }
        }
        throw new IllegalArgumentException("Invalid sort order id");
    }

    public final String path;
    @IdRes
    public final int menuId;

    MovieOrder(String path, int menuId) {
        this.path = path;
        this.menuId = menuId;
    }

}
