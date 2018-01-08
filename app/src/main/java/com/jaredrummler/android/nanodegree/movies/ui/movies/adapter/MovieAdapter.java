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

package com.jaredrummler.android.nanodegree.movies.ui.movies.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jaredrummler.android.nanodegree.movies.R;
import com.jaredrummler.android.nanodegree.movies.tmdb.config.PosterPathSize;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Movie;
import com.jaredrummler.android.nanodegree.movies.ui.movies.MoviesView;
import com.jaredrummler.android.nanodegree.movies.utils.BaseViewHolder.OnItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * The {@link RecyclerView.Adapter} used for showing a list of movies
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    /*package*/ final List<Movie> movies;
    /*package*/ final MoviesView moviesView;

    private final OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClicked(int position) {
            moviesView.onMovieClicked(movies.get(position));
        }
    };

    public MovieAdapter(@NonNull List<Movie> movies,
                        @NonNull MoviesView moviesView) {
        this.movies = movies;
        this.moviesView = moviesView;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.list_item_movie, parent, false);
        return new MovieViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Movie movie = movies.get(position);
        ImageView thumbnail = holder.find(R.id.movie_thumbnail);
        PosterPathSize poster = PosterPathSize.getIdealSize(context);
        Uri posterUri = poster.getUri(movie);
        Picasso.with(context)
                .load(posterUri)
                .config(Bitmap.Config.RGB_565)
                .into(thumbnail);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    @NonNull
    public List<Movie> getMovies() {
        return movies;
    }

}