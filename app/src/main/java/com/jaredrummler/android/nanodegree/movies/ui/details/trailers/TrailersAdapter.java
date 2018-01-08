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

package com.jaredrummler.android.nanodegree.movies.ui.details.trailers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jaredrummler.android.nanodegree.movies.R;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Trailer;
import com.jaredrummler.android.nanodegree.movies.ui.details.DetailsView;
import com.jaredrummler.android.nanodegree.movies.utils.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    /*package*/ final DetailsView detailsView;
    /*package*/ final List<Trailer> trailers;

    private final BaseViewHolder.OnItemClickListener listener = new BaseViewHolder.OnItemClickListener() {
        @Override
        public void onItemClicked(int position) {
            detailsView.openTrailer(trailers.get(position));
        }
    };

    public TrailersAdapter(
            @NonNull List<Trailer> trailers,
            @NonNull DetailsView detailsView) {
        this.trailers = trailers;
        this.detailsView = detailsView;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.list_item_trailer, parent, false);
        return new TrailerViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        ImageView ivTrailer = holder.find(R.id.iv_trailer_image);
        Trailer trailer = trailers.get(position);
        Context context = holder.itemView.getContext();
        Picasso.with(context)
                .load(trailer.getThumbnailUrl())
                .error(R.drawable.ic_image_broken_24dp)
                .resizeDimen(R.dimen.trailer_thumbnail_width, R.dimen.trailer_thumbnail_width)
                .centerInside()
                .into(ivTrailer);
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

}
