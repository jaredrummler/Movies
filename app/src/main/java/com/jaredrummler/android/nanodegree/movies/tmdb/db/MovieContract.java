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

package com.jaredrummler.android.nanodegree.movies.tmdb.db;

import android.net.Uri;
import android.provider.BaseColumns;

import static android.content.ContentResolver.CURSOR_DIR_BASE_TYPE;
import static android.content.ContentResolver.CURSOR_ITEM_BASE_TYPE;
import static android.content.ContentResolver.SCHEME_CONTENT;

public interface MovieContract {

    String AUTHORITY = "com.jaredrummler.android.nanodegree.movies";

    Uri BASE_CONTENT_URI = new Uri.Builder().scheme(SCHEME_CONTENT).authority(AUTHORITY).build();

    String PATH_MOVIES = "movies";

    interface MovieEntry extends BaseColumns {
        Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();

        String TABLE_NAME = "movies";

        String COLUMN_POSTER_PATH = "poster_path";
        String COLUMN_ADULT = "adult";
        String COLUMN_OVERVIEW = "overview";
        String COLUMN_RELEASE_DATE = "release_date";
        String COLUMN_GENRE_IDS = "genre_ids";
        String COLUMN_ID = "id";
        String COLUMN_ORIGINAL_TITLE = "original_title";
        String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        String COLUMN_TITLE = "title";
        String COLUMN_BACKDROP_PATH = "backdrop_path";
        String COLUMN_POPULARITY = "popularity";
        String COLUMN_VOTE_COUNT = "vote_count";
        String COLUMN_VIDEO = "video";
        String COLUMN_VOTE_AVERAGE = "vote_average";

        String CONTENT_TYPE = CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_MOVIES;
        String CONTENT_ITEM_TYPE = CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + PATH_MOVIES;
    }

}
