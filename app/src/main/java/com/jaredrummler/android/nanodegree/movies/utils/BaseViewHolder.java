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

package com.jaredrummler.android.nanodegree.movies.utils;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private final SparseArray<View> views = new SparseArray<>();

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T find(@IdRes int id) {
        View view = views.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            views.put(id, view);
        }
        //noinspection unchecked
        return (T) view;
    }

    /**
     * Callback when a view is clicked from the ViewHolder
     */
    public interface OnItemClickListener {

        /**
         * Called when an item is clicked
         *
         * @param position The position of the item.
         */
        void onItemClicked(int position);
    }

}
