package com.sample.test.moviefinder.view;

import android.app.SearchManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.sample.test.moviefinder.R;
import com.sample.test.moviefinder.adapter.MovieListAdapter;
import com.sample.test.moviefinder.model.MovieResponse;
import com.sample.test.moviefinder.presenter.MoviePresenter;

import java.nio.file.FileAlreadyExistsException;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements MainViewInterface{

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.rv_main_movies)
    RecyclerView rv_main_movies;
    @BindView(R.id.tv_main_search)
    EditText tv_main_search;
    @BindView(R.id.pb_fetch_cache)
    ProgressBar progressBar;

    RecyclerView.Adapter adapter;
    MoviePresenter presenter;
    SearchView searchView;

    CompositeDisposable disposable =  new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        linkPresenter();

        rv_main_movies.setLayoutManager(new GridLayoutManager(this, 3, 1, false));

        fetchMovies();

        SearchManager searchManager = (SearchManager)getSystemService(this.SEARCH_SERVICE);
        searchView = (SearchView) tv_main_search.getParent();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Search");
        presenter.getResultsBasedOnQuery(searchView, this);
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

    @Override
    public void displayMoviesSearch(MovieResponse searchResponse) {
        if(searchResponse!= null){
            adapter = new MovieListAdapter(searchResponse.getData(), this);
            rv_main_movies.setAdapter(adapter);
        }
    }

    private void linkPresenter(){
        presenter = new MoviePresenter(this);
    }

    private void fetchMovies(){
        //TODO get clock timer to fetch from cache??
        presenter.getMovies(this);
        //presenter.getCache();;
    }
}
