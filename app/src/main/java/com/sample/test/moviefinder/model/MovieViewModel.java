package com.sample.test.moviefinder.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by mac_tony on 11/15/18.
 */

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository movieRepository;
    private LiveData<List<Result>> result;

    public MovieViewModel(Application application) {
        super(application);
        this.movieRepository = new MovieRepository(application);
        init(application);
    }

    private void init(Application application){
        result = movieRepository.getCache(application);
    }

    public LiveData<List<Result>> getCache(){return result;}

}
