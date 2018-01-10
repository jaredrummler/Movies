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

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.jaredrummler.android.nanodegree.movies.tmdb.model.Review;

/**
 * A dialog to show a movie review with the author and content.
 */
public class ReviewDialog extends DialogFragment {

    private static final String TAG = "ReviewDialog";
    private static final String ARG_REVIEW = "review";

    public static void show(@NonNull Activity activity, @NonNull Review review) {
        ReviewDialog dialog = new ReviewDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_REVIEW, review);
        dialog.setArguments(args);
        dialog.show(activity.getFragmentManager(), TAG);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Review review = getArguments().getParcelable(ARG_REVIEW);
        return new AlertDialog.Builder(getActivity())
                .setTitle(review.getAuthor())
                .setMessage(review.getContent())
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }

}
