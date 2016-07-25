package com.jeevitharoyapathi.assignment1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jeevitha.royapathi on 7/22/16.
 */

public class Movie implements Serializable {

    private static final String MOVIE_VIDEO = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @JsonProperty("id")
    Integer mId;
    @JsonProperty("poster_path")
    String mPosterPath;
    @JsonProperty("backdrop_path")
    String mBackdropPath;
    @JsonProperty("original_title")
    String originalTitle;
    @JsonProperty("overview")
    String overview;
    @JsonProperty("vote_average")
    float mRatings;
    @JsonProperty("video")
    boolean mIsVideo;
    @JsonProperty("backdropPath")
    String mback;
    @JsonProperty("posterPath")
    String mpost;

    public Movie() {}

    @JsonIgnore
    public String getMovieUrl() {
        return String.format(MOVIE_VIDEO, mId);
    }

    @JsonIgnore
    public Movie(JSONObject jsonObject) {
        try {
            mPosterPath = jsonObject.getString("poster_path");
            mBackdropPath = jsonObject.getString("backdrop_path");
            originalTitle = jsonObject.getString("original_title");
            overview = jsonObject.getString("overview");
            mRatings = jsonObject.getLong("vote_average");
            setId(jsonObject.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        mId = id;
    }

    public float getRatings() {
        return mRatings;
    }

    public void setRatings(float ratings) {
        mRatings = ratings;
    }

    public boolean isVideo() {
        return mIsVideo;
    }

    public void setVideo(boolean video) {
        mIsVideo = video;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", mPosterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s", mBackdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    @JsonIgnore
    public static ArrayList<Movie> fromJSONArray(JSONArray array) {
        ObjectMapper mapper = new ObjectMapper();

        ArrayList<Movie> results = new ArrayList<>();
        for (int x = 0; x < array.length(); x++) {
            try {
                results.add(new Movie(array.getJSONObject(x)));
                //results.add(mapper.readValue(array.getJSONObject(x).toString(),Movie.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return results;
    }

}
