package com.sample.test.moviefinder.presenter;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;

import com.sample.test.moviefinder.api.HerokuAppApi;
import com.sample.test.moviefinder.api.HerokuAppInterface;
import com.sample.test.moviefinder.model.MovieResponse;
import com.sample.test.moviefinder.model.MovieViewModel;
import com.sample.test.moviefinder.model.Result;
import com.sample.test.moviefinder.view.MainActivity;
import com.sample.test.moviefinder.view.MainViewInterface;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mac_tony on 11/10/18.
 */

public class MoviePresenter implements MoviePresenterInterface{
    private final String TAG = MoviePresenter.class.getSimpleName();
    MainViewInterface viewInterface;
    public MovieViewModel viewModel;

    public MoviePresenter(MainViewInterface viewInterface) {
        this.viewInterface = viewInterface;
        viewModel = ViewModelProviders.of(viewInterface.getActivity()).get(MovieViewModel.class);
    }

    @Override
    public void getMovies(Context context) {
        viewModel.getCache().observe(viewInterface.getActivity(),
                new Observer<List<Result>>() {
                    @Override
                    public void onChanged(@Nullable List<Result> results) {
                        viewInterface.displayMoviesCache(results);
                    }
                });
    }
}
