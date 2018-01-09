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

package com.jaredrummler.android.nanodegree.movies;

import android.app.Application;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

public class App extends Application {

    private static OkHttpClient okHttpClient;

    public static OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // OkHttp
        okHttpClient = buildOkHttpClient().build();

        // Picasso
        Picasso.setSingletonInstance(new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build());
    }

    protected OkHttpClient.Builder buildOkHttpClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true);
    }

}
