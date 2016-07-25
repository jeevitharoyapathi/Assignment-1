package com.jeevitharoyapathi.assignment1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeevitharoyapathi.assignment1.R;
import com.jeevitharoyapathi.assignment1.adapters.movieListAdapter;
import com.jeevitharoyapathi.assignment1.model.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity implements movieListAdapter.OnItemClickListener {

    ArrayList<Movie> movies;
    @BindView(R.id.movie_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    private movieListAdapter mMovieListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        movies = new ArrayList<>();
        getMovieData();
        mMovieListAdapter = new movieListAdapter(this, movies);
        mMovieListAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mMovieListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMovieData();
            }
        });

        mRecyclerView.setAdapter(mMovieListAdapter);
    }

    public void getMovieData() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;
                try {
                    movieJsonResults = response.getJSONArray("results");
                    movies.clear();
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    mMovieListAdapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    public void onItemClick(Movie movie, int type) {
        Intent intent;
        if (movieListAdapter.REGULAR_MOVIE == type) {
            ObjectMapper mapper = new ObjectMapper();
            String sMovie = null;
            try {
                sMovie = mapper.writeValueAsString(movie);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            intent = new Intent(this, MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.MOVIE_DETAILS, sMovie);
        } else {
            intent = new Intent(this, youtubePlayerActivity.class);
            intent.putExtra(youtubePlayerActivity.MOVIE_ID, movie.getMovieUrl());
        }
        startActivity(intent);
    }
}
