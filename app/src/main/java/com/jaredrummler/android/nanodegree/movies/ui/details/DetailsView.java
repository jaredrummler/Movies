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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jaredrummler.android.nanodegree.movies.tmdb.model.Movie;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Review;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Trailer;

import java.util.List;

public interface DetailsView {

    void showMovieDetails(@NonNull Movie movie);

    void showBackdrop(@NonNull Movie movie);

    void showPoster(@NonNull Movie movie);

    void showTrailers(@Nullable List<Trailer> trailers);

    void openTrailer(@NonNull Trailer trailer);

    void showReviews(@Nullable List<Review> reviews);

    void openReview(@NonNull Review review);

}
