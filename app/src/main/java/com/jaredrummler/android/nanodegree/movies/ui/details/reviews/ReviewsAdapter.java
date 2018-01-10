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

package com.jaredrummler.android.nanodegree.movies.ui.details.reviews;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaredrummler.android.nanodegree.movies.R;
import com.jaredrummler.android.nanodegree.movies.tmdb.model.Review;
import com.jaredrummler.android.nanodegree.movies.ui.details.DetailsView;
import com.jaredrummler.android.nanodegree.movies.utils.BaseViewHolder.OnItemClickListener;

import java.util.List;

/**
 * RecyclerView adapter to show a list of movie reviews.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsViewHolder> {

    /*package*/ final DetailsView detailsView;
    /*package*/ final List<Review> reviews;

    private final OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onItemClicked(int position) {
            detailsView.openReview(reviews.get(position));
        }
    };

    public ReviewsAdapter(
            @NonNull List<Review> reviews,
            @NonNull DetailsView detailsView) {
        this.reviews = reviews;
        this.detailsView = detailsView;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.list_item_review, parent, false);
        return new ReviewsViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        TextView tvReview = holder.find(R.id.tv_review_text);
        tvReview.setText(reviews.get(position).getContent());
        // Hide the divider for the last item
        holder.find(R.id.divider).setVisibility(position == getItemCount() - 1 ? View.GONE : View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

}
