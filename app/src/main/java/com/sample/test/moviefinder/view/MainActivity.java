package com.sample.test.moviefinder.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.test.moviefinder.R;
import com.sample.test.moviefinder.adapter.MovieListAdapter;
import com.sample.test.moviefinder.model.MovieResponse;
import com.sample.test.moviefinder.presenter.MoviePresenter;

import java.nio.file.FileAlreadyExistsException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainViewInterface{

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.rv_main_movies)
    RecyclerView rv_main_movies;
    @BindView(R.id.tv_main_search)
    TextView tv_main_search;
    @BindView(R.id.pb_fetch_cache)
    ProgressBar progressBar;

    RecyclerView.Adapter adapter;
    MoviePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        linkPresenter();

        rv_main_movies.setLayoutManager(new GridLayoutManager(this, 3, 1, false));

        fetchMovies();
    }

    @Override
    public void displayMovies(MovieResponse movieResponse) {
        if(movieResponse!= null){
            adapter = new MovieListAdapter(movieResponse.getData(), this);
            rv_main_movies.setAdapter(adapter);
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    private void linkPresenter(){
        presenter = new MoviePresenter(this);
    }

    private void fetchMovies(){
        //TODO get clock timer to fetch from cache??
        presenter.getMovies();
        //presenter.getCache();;
    }
}
