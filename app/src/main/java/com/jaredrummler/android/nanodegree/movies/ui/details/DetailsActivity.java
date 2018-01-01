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
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
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
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private static final String TAG = "DetailsActivity";

    public static final String EXTRA_MOVIE = "nanodegree.movies.extras.MOVIE";

    private Movie mMovie;

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
        mMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        // Load the details
        loadBackdrop();
        loadPoster();
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

    private void loadBackdrop() {
        ImageView ivMovieBackdrop = (ImageView) findViewById(R.id.iv_movie_backdrop);
        Uri backdropUri = BackropPathSize.MEDIUM.getUri(mMovie);
        Picasso.with(this).load(backdropUri).into(ivMovieBackdrop, new Callback() {
            @Override
            public void onSuccess() {
                // Set the movie details after the image is loaded
                setMovieDetails();
            }

            @Override
            public void onError() {
                Log.d(TAG, "Error loading backdrop image");
                setMovieDetails();
            }
        });
    }

    private void loadPoster() {
        ImageView ivMoviePoster = (ImageView) findViewById(R.id.iv_movie_poster);
        PosterPathSize posterSize = PosterPathSize.getIdealSize(this);
        Uri posterUri = posterSize.getUri(mMovie);
        //noinspection SuspiciousNameCombination
        Picasso.with(this)
                .load(posterUri)
                .resizeDimen(R.dimen.details_movie_poster_width, R.dimen.details_movie_poster_width)
                .centerInside()
                .into(ivMoviePoster);
    }

    /*package*/ void setMovieDetails() {
        // Hide the spinner
        findViewById(R.id.pb_movie_backrop).setVisibility(View.GONE);
        findViewById(R.id.movie_details_layout).setVisibility(View.VISIBLE);
        // Set the movie title
        TextView tvMovieTitle = (TextView) findViewById(R.id.tv_movie_title);
        tvMovieTitle.setText(mMovie.getTitle());
        // Set the release date
        TextView tvReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        tvReleaseDate.setText(mMovie.getReleaseDate());
        // Set the rating
        RatingBar ratingBar = (RatingBar) findViewById(R.id.movie_rating);
        ratingBar.setRating((mMovie.getVoteAverage().floatValue() / 10) * 5);
        // Set the movie overview text
        TextView tvOverview = (TextView) findViewById(R.id.tv_overview);
        tvOverview.setText(mMovie.getOverview());
    }

    public void onOpenTmdb(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.themoviedb.org/"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
