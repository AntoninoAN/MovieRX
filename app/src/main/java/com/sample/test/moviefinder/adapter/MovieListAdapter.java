package com.sample.test.moviefinder.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sample.test.moviefinder.R;
import com.sample.test.moviefinder.model.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mac_tony on 11/10/18.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MoviesHolder>
implements Filterable{
    public final String TAG = MovieListAdapter.class.getSimpleName();
    List<Result> movieResponses;
    List<Result> movieResponsesFiltered;
    Context mContext;

    public MovieListAdapter(List<Result> movieResults, Context mContext) {
        this.movieResponses = movieResults;
        this.movieResponsesFiltered = movieResults;
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
        Log.d(TAG, "onBindViewHolder: "+movieResponsesFiltered.get(position).getPoster());
        Log.d(TAG, "onBindViewHolder: "+movieResponsesFiltered.get(position).getTitle());
        Log.d(TAG, "onBindViewHolder: "+movieResponsesFiltered.get(position).getYear());
        Glide.with(mContext).load(movieResponsesFiltered.get(position).getPoster()).into(holder.iv_movie_item);
        holder.tv_title_movie_item.setText(movieResponsesFiltered.get(position).getTitle());
        holder.tv_year_movie_item.setText(movieResponsesFiltered.get(position).getYear());
    }

    @Override
    public int getItemCount() {
        return movieResponsesFiltered != null? movieResponsesFiltered.size() : 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) movieResponsesFiltered = movieResponses;
                else {
                    List<Result> filteredList = new ArrayList<>();
                    for(Result item : movieResponses){
                        if(item.getGenre().toLowerCase().contains(charString.toLowerCase()) ||
                                item.getTitle().toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(item);
                    }
                    movieResponsesFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = movieResponsesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                movieResponsesFiltered = (ArrayList<Result>) results.values;
                notifyDataSetChanged();
            }
        };
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
