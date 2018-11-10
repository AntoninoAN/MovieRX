package com.sample.test.moviefinder.presenter;

import android.util.Log;

import com.sample.test.moviefinder.api.HerokuAppApi;
import com.sample.test.moviefinder.api.HerokuAppInterface;
import com.sample.test.moviefinder.model.MovieResponse;
import com.sample.test.moviefinder.model.MovieResult;
import com.sample.test.moviefinder.view.MainViewInterface;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mac_tony on 11/10/18.
 */

public class MoviePresenter implements MoviePresenterInterface{
    private final String TAG = MoviePresenter.class.getSimpleName();
    MainViewInterface viewInterface;

    public MoviePresenter(MainViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void getMovies() {

    }

    @Override
    public void getCache() {

    }

    private Observable<MovieResult> getObservable(){
        return HerokuAppApi.getRetrofit().create(HerokuAppInterface.class)
                .getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<MovieResult> getObserver(){
        return new DisposableObserver<MovieResult>() {
            @Override
            public void onNext(MovieResult movieResult) {
                Log.d(TAG, "onNext: "+ movieResult.getTotalResult());
                viewInterface.displayMovies(movieResult);
            }

            @Override
            public void onError(Throwable e) {
                viewInterface.showToast(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };
    }
}
