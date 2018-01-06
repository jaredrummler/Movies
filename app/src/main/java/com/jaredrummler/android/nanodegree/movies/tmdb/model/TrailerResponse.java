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

package com.jaredrummler.android.nanodegree.movies.tmdb.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse implements Parcelable {

    @SerializedName("id")
    private String id;

    @SerializedName("results")
    private List<Trailer> trailers;

    public TrailerResponse() {
    }

    protected TrailerResponse(Parcel in) {
        this.id = in.readString();
        this.trailers = in.createTypedArrayList(Trailer.CREATOR);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeTypedList(this.trailers);
    }


    public static final Parcelable.Creator<TrailerResponse> CREATOR = new Parcelable.Creator<TrailerResponse>() {
        @Override
        public TrailerResponse createFromParcel(Parcel source) {
            return new TrailerResponse(source);
        }

        @Override
        public TrailerResponse[] newArray(int size) {
            return new TrailerResponse[size];
        }
    };
}
