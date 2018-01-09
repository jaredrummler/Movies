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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaredrummler.android.nanodegree.movies.R;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Movie;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.MovieList;
import com.jaredrummler.android.nanodegree.movies.ui.details.DetailsActivity;
import com.jaredrummler.android.nanodegree.movies.ui.movies.adapter.MovieAdapter;
import com.jaredrummler.android.nanodegree.movies.utils.Prefs;

import java.util.List;

public class MoviesActivity extends AppCompatActivity implements MoviesView, LoaderManager.LoaderCallbacks<MovieList> {

    private static final String TAG = "MainActivity";

    private static final int LOADER_MOVIES = 215;

    private Prefs prefs;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ViewGroup errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Find views
        recyclerView = (RecyclerView) findViewById(R.id.rv_movies);
        progressBar = (ProgressBar) findViewById(R.id.pb_movies_progress);
        errorLayout = (ConstraintLayout) findViewById(R.id.error_layout);
        // Get preferences for sort order
        prefs = Prefs.with(this);
        // Load the movies
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(LOADER_MOVIES, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        // Check the sort order preference
        int sortOrderMenuId = prefs.getMovieSortOrder().menuId;
        menu.findItem(sortOrderMenuId).setChecked(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_sort_now_playing:
            case R.id.action_sort_popular:
            case R.id.action_sort_top_rated:
            case R.id.action_sort_upcoming:
            case R.id.action_sort_favorites:
                MovieOrder movieOrder = MovieOrder.idOf(id);
                prefs.saveMovieSortOrder(movieOrder);
                item.setChecked(true);
                LoaderManager loaderManager = getSupportLoaderManager();
                loaderManager.restartLoader(LOADER_MOVIES, null, this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMovies(@NonNull List<Movie> movies) {
        errorLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        int spanCount = getResources().getInteger(R.integer.movies_column_count);
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MovieAdapter(movies, this));
    }

    @Override
    public void openMovieDetails(@NonNull Movie movie) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    @Override
    public void showErrorView(@Nullable String reason) {
        errorLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        TextView tvErrorMessage = (TextView) findViewById(R.id.tv_error_message);
        if (reason != null) {
            tvErrorMessage.setText(reason);
        } else {
            tvErrorMessage.setText(R.string.no_connection_message);
        }
    }

    @Override
    public Loader<MovieList> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(MoviesActivity.this, prefs.getMovieSortOrder());
    }

    @Override
    public void onLoadFinished(Loader<MovieList> loader, MovieList data) {
        if (data == null) {
            showErrorView(null);
        } else {
            showMovies(data.getMovies());
        }
    }

    @Override
    public void onLoaderReset(Loader<MovieList> loader) {

    }

    public void onRefresh(View view) {
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(LOADER_MOVIES, null, this);
    }

}
