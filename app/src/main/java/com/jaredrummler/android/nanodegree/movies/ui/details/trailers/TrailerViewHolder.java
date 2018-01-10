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

import android.support.annotation.NonNull;
import android.view.View;

import com.jaredrummler.android.nanodegree.movies.R;
import com.jaredrummler.android.nanodegree.movies.utils.BaseViewHolder;

final class TrailerViewHolder extends BaseViewHolder {

    TrailerViewHolder(View itemView, @NonNull final OnItemClickListener listener) {
        super(itemView);
        itemView.findViewById(R.id.iv_trailer_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClicked(getAdapterPosition());
            }
        });
    }

}
