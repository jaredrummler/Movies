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

package com.jaredrummler.android.nanodegree.movies.ui.movies;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jaredrummler.android.nanodegree.movies.tmdb.model.Movie;

import java.util.List;

public interface MoviesView {

    /**
     * Called when a movie was clicked from the RecyclerView
     *
     * @param movie The movie that was clicked
     */
    void onMovieClicked(@NonNull Movie movie);

    /**
     * Called when movies were loaded from The Movie DB API.
     *
     * @param movies The list of movies
     */
    void onFetchedMovies(@NonNull List<Movie> movies);

    /**
     * Called when an error occurred while fetching the movies.
     *
     * @param reason Optional reason for failure
     */
    void onFailedToFetchMovies(@Nullable String reason);

}
