package com.angelgomezsalazar.upcomingmoviesdemo.retrofit.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by angelgomez on 8/18/16.
 */
public class Movie {

    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("genre_ids")
    private List<Integer> genreIds;
    private String release_date;
    private String overview;

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getRelease_date() {
        return release_date;
    }

    public String getOverview() {
        return overview;
    }
}
