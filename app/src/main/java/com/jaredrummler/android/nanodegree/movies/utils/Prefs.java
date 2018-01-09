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
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jaredrummler.android.nanodegree.movies.ui.movies.MovieOrder;

/**
 * Shared preference helper for the app
 */
public class Prefs {

    public static final String PREF_MOVIE_SORT_ORDER = "movie_order";
    public static final MovieOrder DEFAULT_SORT_ORDER = MovieOrder.POPULAR;

    private static Prefs singleton;

    public static Prefs with(Context context) {
        if (singleton == null) {
            synchronized (Prefs.class) {
                if (singleton == null) {
                    singleton = new Prefs(context);
                }
            }
        }
        return singleton;
    }

    private final SharedPreferences preferences;

    private Prefs(Context context) {
        this.preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public MovieOrder getMovieSortOrder() {
        String value = preferences.getString(PREF_MOVIE_SORT_ORDER, DEFAULT_SORT_ORDER.path);
        for (MovieOrder sortOrder : MovieOrder.values()) {
            if (sortOrder.path.equals(value)) {
                return sortOrder;
            }
        }
        return DEFAULT_SORT_ORDER;
    }

    public void saveMovieSortOrder(MovieOrder sortOrder) {
        preferences.edit().putString(PREF_MOVIE_SORT_ORDER, sortOrder.path).apply();
    }

}
