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

package com.jaredrummler.android.nanodegree.movies.ui.details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jaredrummler.android.nanodegree.movies.R;
import com.jaredrummler.android.nanodegree.movies.tmdb.config.BackropPathSize;
import com.jaredrummler.android.nanodegree.movies.tmdb.config.PosterPathSize;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Movie;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Review;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Trailer;
import com.jaredrummler.android.nanodegree.movies.ui.details.reviews.ReviewDialog;
import com.jaredrummler.android.nanodegree.movies.ui.details.reviews.ReviewsAdapter;
import com.jaredrummler.android.nanodegree.movies.ui.details.reviews.ReviewsLoader;
import com.jaredrummler.android.nanodegree.movies.ui.details.trailers.TrailerLoader;
import com.jaredrummler.android.nanodegree.movies.ui.details.trailers.TrailersAdapter;
import com.jaredrummler.android.nanodegree.movies.utils.MovieFavorites;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailsActivity extends AppCompatActivity implements DetailsView {

    private static final String TAG = "DetailsActivity";

    public static final String EXTRA_MOVIE = "nanodegree.movies.extras.MOVIE";

    /*package*/ MovieFavorites favorites;
    /*package*/ Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ensure we have a movie
        if (!getIntent().hasExtra(EXTRA_MOVIE)) {
            throw new RuntimeException("Please pass a movie to the DetailsActivity");
        }

        setContentView(R.layout.activity_details);

        // Setup the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        // Get the Movie
        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        favorites = new MovieFavorites(this);

        // Load the details
        showBackdrop(movie);
        showPoster(movie);

        // Load the trailers
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(TrailerLoader.TRAILER_LOADER_ID, null, trailerCallbacks);
        loaderManager.initLoader(ReviewsLoader.REVIEWS_LOADER_ID, null, reviewsCallbacks);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMovieDetails(final Movie movie) {
        // Hide the spinner
        findViewById(R.id.pb_movie_backrop).setVisibility(View.GONE);
        findViewById(R.id.movie_details_layout).setVisibility(View.VISIBLE);
        // Set the movie title
        TextView tvMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        tvMovieTitle.setText(movie.getTitle());
        // Set the release date
        TextView tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        tvReleaseDate.setText(movie.getReleaseDate());
        // Set the rating
        RatingBar ratingBar = (RatingBar) findViewById(R.id.movie_rating);
        ratingBar.setRating((movie.getVoteAverage().floatValue() / 10) * 5);
        // Set the movie overview text
        TextView tvOverview = (TextView) findViewById(R.id.tv_overview);
        tvOverview.setText(movie.getOverview());
        // Show the FAB
        updateFavoritesView();
    }

    @Override
    public void showBackdrop(@NonNull final Movie movie) {
        ImageView ivMovieBackdrop = (ImageView) findViewById(R.id.iv_movie_backdrop);
        Uri backdropUri = BackropPathSize.MEDIUM.getUri(movie);
        Picasso.with(this).load(backdropUri).into(ivMovieBackdrop, new Callback() {
            @Override
            public void onSuccess() {
                // Set the movie details after the image is loaded
                showMovieDetails(movie);
            }

            @Override
            public void onError() {
                Log.d(TAG, "Error loading backdrop image");
                showMovieDetails(movie);
            }
        });
    }

    @Override
    public void showPoster(@NonNull Movie movie) {
        ImageView ivMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        PosterPathSize posterSize = PosterPathSize.getIdealSize(this);
        Uri posterUri = posterSize.getUri(movie);
        //noinspection SuspiciousNameCombination
        Picasso.with(this)
                .load(posterUri)
                .resizeDimen(R.dimen.details_movie_poster_width, R.dimen.details_movie_poster_width)
                .centerInside()
                .into(ivMoviePoster);
    }

    @Override
    public void showTrailers(@Nullable List<Trailer> trailers) {
        if (trailers == null || trailers.isEmpty()) {
            findViewById(R.id.cv_trailers).setVisibility(View.GONE);
            return;
        }

        RecyclerView rvTrailers = findViewById(R.id.rv_trailers);
        rvTrailers.setVisibility(View.VISIBLE);
        rvTrailers.setHasFixedSize(true);
        rvTrailers.setNestedScrollingEnabled(false);
        findViewById(R.id.pb_trailers).setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvTrailers.setLayoutManager(layoutManager);
        rvTrailers.setAdapter(new TrailersAdapter(trailers, this));
    }

    @Override
    public void openTrailer(@NonNull Trailer trailer) {
        Intent watchIntent = trailer.getWatchIntent(this);
        if (watchIntent != null) {
            startActivity(watchIntent);
        }
    }

    @Override
    public void showReviews(@Nullable List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            findViewById(R.id.cv_reviews).setVisibility(View.GONE);
            return;
        }

        RecyclerView rvReviews = findViewById(R.id.rv_reviews);
        rvReviews.setVisibility(View.VISIBLE);
        rvReviews.setHasFixedSize(true);
        rvReviews.setNestedScrollingEnabled(false);
        findViewById(R.id.pb_reviews).setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvReviews.setLayoutManager(layoutManager);
        rvReviews.setAdapter(new ReviewsAdapter(reviews, this));
    }

    @Override
    public void openReview(@NonNull Review review) {
        ReviewDialog.show(this, review);
    }

    @Override
    public void updateFavoritesView() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_favorite);
        if (fab.getVisibility() == View.GONE) {
            // Setup and show the FAB for the first time.
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favorites.isFavorite(movie)) {
                        favorites.remove(movie);
                    } else {
                        favorites.save(movie);
                    }
                    updateFavoritesView();
                }
            });
        }
        // Set the correct image resource.
        @DrawableRes int icon;
        if (favorites.isFavorite(movie)) {
            icon = R.drawable.ic_favorite_white_24dp;
        } else {
            icon = R.drawable.ic_favorite_border_white_24dp;
        }
        fab.setImageResource(icon);
    }

    public void onOpenTmdb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.themoviedb.org/"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private final LoaderManager.LoaderCallbacks<List<Trailer>> trailerCallbacks = new LoaderManager.LoaderCallbacks<List<Trailer>>() {
        @Override
        public Loader<List<Trailer>> onCreateLoader(int id, Bundle args) {
            return new TrailerLoader(DetailsActivity.this, movie);
        }

        @Override
        public void onLoadFinished(Loader<List<Trailer>> loader, List<Trailer> data) {
            showTrailers(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Trailer>> loader) {

        }
    };

    private final LoaderManager.LoaderCallbacks<List<Review>> reviewsCallbacks = new LoaderManager.LoaderCallbacks<List<Review>>() {
        @Override
        public Loader<List<Review>> onCreateLoader(int id, Bundle args) {
            return new ReviewsLoader(DetailsActivity.this, movie);
        }

        @Override
        public void onLoadFinished(Loader<List<Review>> loader, List<Review> data) {
            showReviews(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Review>> loader) {

        }
    };

}
