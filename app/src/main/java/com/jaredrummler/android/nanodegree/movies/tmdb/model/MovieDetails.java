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

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetails extends Movie {

    @SerializedName("genres")
    private List<Genre> genres;

    @SerializedName("budget")
    private Integer budget;

    @SerializedName("revenue")
    private Integer revenue;

    @SerializedName("runtime")
    private Integer runtime;

    @SerializedName("homepage")
    private String homepage;

    @SerializedName("videos")
    private Videos videos;

    @SerializedName("reviews")
    private Reviews reviews;

    @SerializedName("status")
    private String status;

    @SerializedName("tagline")
    private String tagline;

    public MovieDetails() {
    }

    protected MovieDetails(Parcel in) {
        super(in);
        this.genres = in.createTypedArrayList(Genre.CREATOR);
        this.budget = (Integer) in.readValue(Integer.class.getClassLoader());
        this.revenue = (Integer) in.readValue(Integer.class.getClassLoader());
        this.runtime = (Integer) in.readValue(Integer.class.getClassLoader());
        this.homepage = in.readString();
        this.videos = in.readParcelable(Videos.class.getClassLoader());
        this.reviews = in.readParcelable(Reviews.class.getClassLoader());
        this.status = in.readString();
        this.tagline = in.readString();
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Videos getVideos() {
        return videos;
    }

    public void setVideos(Videos videos) {
        this.videos = videos;
    }

    public Reviews getReviews() {
        return reviews;
    }

    public void setReviews(Reviews reviews) {
        this.reviews = reviews;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.genres);
        dest.writeValue(this.budget);
        dest.writeValue(this.revenue);
        dest.writeValue(this.runtime);
        dest.writeString(this.homepage);
        dest.writeParcelable(this.videos, flags);
        dest.writeParcelable(this.reviews, flags);
        dest.writeString(this.status);
        dest.writeString(this.tagline);
    }

    public static final Creator<MovieDetails> CREATOR = new Creator<MovieDetails>() {
        @Override
        public MovieDetails createFromParcel(Parcel source) {
            return new MovieDetails(source);
        }

        @Override
        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };

}
