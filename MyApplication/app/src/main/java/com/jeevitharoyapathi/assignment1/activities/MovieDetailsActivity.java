package com.jeevitharoyapathi.assignment1.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeevitharoyapathi.assignment1.R;
import com.jeevitharoyapathi.assignment1.model.Movie;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

public class MovieDetailsActivity extends AppCompatActivity {
    public final static String MOVIE_DETAILS = "movie_details";
    private Movie mMovie;
    @BindView(R.id.imageview_backdrop)
    ImageView mImgBackdrop;
    @BindView(R.id.imageview_poster)
    ImageView mImgPoster;
    @BindView(R.id.movie_releasedate)
    TextView txtReleaseDate;
    @BindView(R.id.movie_overview)
    TextView txtOverview;
    @BindView(R.id.ratingbar_vote)
    RatingBar mRatingBar;
    @BindView(R.id.player_fab)
    FloatingActionButton mActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        ObjectMapper mapper = new ObjectMapper();
        try {
            String movieString = getIntent().getStringExtra(MOVIE_DETAILS);
            mMovie = mapper.readValue(movieString, Movie.class);
            txtOverview.setText(mMovie.getOverview());
            txtReleaseDate.setText(mMovie.getOriginalTitle());
            mRatingBar.setRating(mMovie.getRatings() / 2);
            Picasso.with(this)
                    .load(mMovie.getBackdropPath())
                    .centerInside()
                    .fit()
                    .transform(new RoundedCornersTransformation(10, 1))
                    .into(mImgBackdrop);
            Picasso.with(this)
                    .load(mMovie.getPosterPath())
                    .centerInside()
                    .fit()
                    .transform(new RoundedCornersTransformation(10, 1))
                    .into(mImgPoster);
            mActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MovieDetailsActivity.this, youtubePlayerActivity.class);
                    intent.putExtra(youtubePlayerActivity.MOVIE_ID, mMovie.getMovieUrl());
                    startActivity(intent);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
