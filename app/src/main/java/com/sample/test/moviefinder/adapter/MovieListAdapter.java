package com.sample.test.moviefinder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sample.test.moviefinder.R;
import com.sample.test.moviefinder.model.MovieResponse;
import com.sample.test.moviefinder.model.MovieResult;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mac_tony on 11/10/18.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MoviesHolder> {

    List<MovieResponse> movieResponses;
    Context mContext;

    public MovieListAdapter(List<MovieResponse> movieResults, Context mContext) {
        this.movieResponses = movieResults;
        this.mContext = mContext;
    }

    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.item_movie,
                parent,
                false);
        MoviesHolder moviesHolder = new MoviesHolder(view);
        return moviesHolder;
    }

    @Override
    public void onBindViewHolder(MoviesHolder holder, int position) {
        Glide.with(mContext).load(movieResponses.get(position).getPoster()).into(holder.iv_movie_item);
        holder.tv_title_movie_item.setText(movieResponses.get(position).getTitle());
        holder.tv_year_movie_item.setText(movieResponses.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return movieResponses.size();
    }

    public class MoviesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_title_movie_item)
        TextView tv_title_movie_item;
        @BindView(R.id.tv_year_movie_item)
        TextView tv_year_movie_item;
        @BindView(R.id.iv_movie_item)
        ImageView iv_movie_item;

        public MoviesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
