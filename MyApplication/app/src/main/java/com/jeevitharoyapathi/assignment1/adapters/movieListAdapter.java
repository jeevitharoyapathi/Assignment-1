package com.jeevitharoyapathi.assignment1.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeevitharoyapathi.assignment1.R;
import com.jeevitharoyapathi.assignment1.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by jeevitha.royapathi on 7/23/16.
 */
public class movieListAdapter extends RecyclerView.Adapter<movieListAdapter.ViewHolder> {

    public final static int POPULAR_MOVIE = 1;
    public final static int REGULAR_MOVIE = 0;
    private List<Movie> mDataset;
    private Context mContext;
    private OnItemClickListener mclicklistener;

    public movieListAdapter(Context myContext, List<Movie> myDataset) {
        mContext = myContext;
        mDataset = myDataset;

    }

    public movieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == REGULAR_MOVIE) {
            v = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.movie_item, parent, false);
            return new regularViewHolder(v);
        } else {
            v = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.popular_movie_item, parent, false);
            return new popularMovieViewHolder(v);
        }

    }

    @Override
    public int getItemViewType(final int position) {
        final Movie movie = mDataset.get(position);
        if (isPopularMovie(movie)) {
            return POPULAR_MOVIE;
        } else {
            return REGULAR_MOVIE;
        }
    }

    private boolean isPopularMovie(Movie movie) {
        if (movie.getRatings() >= 5)
            return true;
        return false;
    }

    public void setOnClickListener(OnItemClickListener onItemClickListener) {
        mclicklistener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mDataset.get(position);
        holder.bindMovieData(movie);

    }

    public int getItemCount() {
        return mDataset.size();
    }

    public Object getItem(int position) {
        return mDataset.get(position);
    }

    //Function to check NextWorkAvailablity
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    // Viewholder of the recycler view
    public abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View v) {
            super(v);
        }

        public abstract void bindMovieData(Movie movie);
    }

    // Viewholder of the recycler view
    public class regularViewHolder extends ViewHolder {
        @BindView(R.id.movie_title)
        TextView movieTitle;
        @BindView(R.id.movie_overview)
        TextView movieOverview;
        @BindView(R.id.movie_poster)
        ImageView vIcon;
        int orientation;

        public regularViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            orientation = v.getResources().getConfiguration().orientation;
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mclicklistener != null) {
                        mclicklistener.onItemClick(mDataset.get(getPosition()), REGULAR_MOVIE);
                    }
                }

            });
        }

        public void bindMovieData(Movie movie) {
            movieTitle.setText(movie.getOriginalTitle());
            movieOverview.setText(movie.getOverview());
            String imageUrl;
            orientation = itemView.getContext().getResources().getConfiguration().orientation;
            if (Configuration.ORIENTATION_LANDSCAPE == orientation) {
                imageUrl = movie.getBackdropPath();
            } else {
                imageUrl = movie.getPosterPath();
            }
            Picasso.with(vIcon.getContext())
                    .load(imageUrl)
                    .fit()
                    .centerInside()
                    .transform(new RoundedCornersTransformation(10, 1))
                    .placeholder(R.drawable.placeholder)
                    .into(vIcon);
        }
    }

    public class popularMovieViewHolder extends ViewHolder {
        @BindView(R.id.movie_poster)
        ImageView vIcon;
        @BindView(R.id.movie_play)
        ImageView vPlay;
        @BindView(R.id.movie_title)
        TextView txtTitle;

        public popularMovieViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mclicklistener != null) {
                        mclicklistener.onItemClick(mDataset.get(getPosition()), REGULAR_MOVIE);
                    }
                }

            });
            vPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mclicklistener != null) {
                        mclicklistener.onItemClick(mDataset.get(getPosition()), POPULAR_MOVIE);
                    }
                }
            });
        }

        public void bindMovieData(Movie movie) {
            txtTitle.setText(movie.getOriginalTitle());
            Picasso.with(vIcon.getContext())
                    .load(movie.getBackdropPath())
                    .fit()
                    .centerInside()
                    .transform(new RoundedCornersTransformation(10, 1))
                    .placeholder(R.drawable.placeholder)
                    .into(vIcon);
        }
    }

    //Interface to handle recylerview onClick Events
    public interface OnItemClickListener {
        public void onItemClick(Movie contact, int type);
    }
}
