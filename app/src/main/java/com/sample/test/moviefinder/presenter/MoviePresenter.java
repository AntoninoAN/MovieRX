package com.sample.test.moviefinder.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.SearchView;
import android.widget.TextView;

import com.sample.test.moviefinder.api.HerokuAppApi;
import com.sample.test.moviefinder.api.HerokuAppInterface;
import com.sample.test.moviefinder.model.MovieResponse;
import com.sample.test.moviefinder.view.MainActivity;
import com.sample.test.moviefinder.view.MainViewInterface;

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

    public MoviePresenter(MainViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void getMovies(Context context) {
        getObservable(context).subscribeWith(getObserver());
    }

    @Override
    public void getResultsBasedOnQuery(SearchView searchView, final Context context) {
        getObservableQuery(searchView)
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        if(s.equals("")){
                            return false;
                        }else{
                            return true;
                        }
                    }
                })
                .debounce(3, TimeUnit.SECONDS)
                .distinctUntilChanged()
                .switchMap(new Function<String, ObservableSource<MovieResponse>>() {
                    @Override
                    public ObservableSource<MovieResponse> apply(String s) throws Exception {
                        return HerokuAppApi.getRetrofit(context).create(HerokuAppInterface.class)
                            .getMoviesBasedOnQuery(s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());
    }

    private Observable<MovieResponse> getObservable(Context context){
        return HerokuAppApi.getRetrofit(context).create(HerokuAppInterface.class)
                .getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private DisposableObserver<MovieResponse> getObserver(){
        return new DisposableObserver<MovieResponse>() {
            @Override
            public void onNext(MovieResponse movieResult) {
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


    private Observable<String> getObservableQuery(SearchView searchView){

        final PublishSubject<String> publishSubject = PublishSubject.create();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                publishSubject.onNext(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                publishSubject.onNext(newText);
                return true;
            }
        });

        return publishSubject;
    }
}
