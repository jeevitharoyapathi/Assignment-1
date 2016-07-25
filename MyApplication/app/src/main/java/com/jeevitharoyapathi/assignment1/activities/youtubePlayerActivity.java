package com.jeevitharoyapathi.assignment1.activities;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.jeevitharoyapathi.assignment1.R;
import com.jeevitharoyapathi.assignment1.model.Video;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

/**
 * Created by jeevitha.royapathi on 7/24/16.
 */
public class youtubePlayerActivity extends YouTubeBaseActivity {
    public static final String MOVIE_ID = "MOVIE_ID";
    private ArrayList<Video> mVideos;
    @BindView(R.id.player)
    protected YouTubePlayerView mYouTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_player);
        ButterKnife.bind(this);
        getVideoData(getIntent().getStringExtra(MOVIE_ID));
    }

    public void getVideoData(String movieUrl) {
        String url = movieUrl;
        mVideos= new ArrayList<>();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray videoJsonResults = null;
                try {
                    videoJsonResults = response.getJSONArray("results");
                    mVideos.clear();
                    mVideos.addAll(Video.fromJSONArray(videoJsonResults));
                    if (mVideos.size() > 0) {
                        playVideo(mVideos.get(0).getKey());
                    } else
                        playVideo("5xVh-7ywKpE");
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

    private void playVideo(final String key) {

        mYouTubePlayerView.initialize("AIzaSyCOfAdykA9O7dq25zYz88HuzI9DAAxVt8M",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        // do any work here to cue video, play video, etc.
                        youTubePlayer.cueVideo(key);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }

}