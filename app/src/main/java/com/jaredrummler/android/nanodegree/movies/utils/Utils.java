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

package com.jaredrummler.android.nanodegree.movies.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.jaredrummler.android.nanodegree.movies.R;
import com.jaredrummler.android.nanodegree.movies.tmdb.config.MovieSortOrder;

/**
 * Helper methods for the app
 */
public final class Utils {

    /**
     * Get the screen width of the device
     *
     * @param context The application context
     * @return The screen width in PX.
     */
    public static int getScreenWidth(@NonNull Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels / 2;
    }

    /**
     * Check if the device is connected to the internet
     * <p>
     * See: https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
     *
     * @param context The application context
     * @return {@code true} if the device is connected
     */
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Get the sort order menu id
     *
     * @param sortOrder The sort order
     * @return The resource id associated with the menu item
     */
    public static int getSortOrderMenuId(@NonNull MovieSortOrder sortOrder) {
        switch (sortOrder) {
            case POPULAR:
                return R.id.action_sort_popular;
            case TOP_RATED:
                return R.id.action_sort_top_rated;
            case UPCOMING:
                return R.id.action_sort_upcoming;
            case NOW_PLAYING:
                return R.id.action_sort_now_playing;
            default:
                throw new IllegalArgumentException("Unknown sort order: " + sortOrder);
        }
    }

    /**
     * Get the movie sort order based on the menu id
     *
     * @param id The resource id
     * @return The sort order
     */
    public static MovieSortOrder getMovieSortOrder(@IdRes int id) {
        switch (id) {
            case R.id.action_sort_now_playing:
                return MovieSortOrder.NOW_PLAYING;
            case R.id.action_sort_popular:
                return MovieSortOrder.POPULAR;
            case R.id.action_sort_top_rated:
                return MovieSortOrder.TOP_RATED;
            case R.id.action_sort_upcoming:
                return MovieSortOrder.UPCOMING;
            default:
                throw new IllegalArgumentException("Resource id not associated with sort order");
        }
    }

    private Utils() {
        throw new AssertionError("no instances");
    }

}
