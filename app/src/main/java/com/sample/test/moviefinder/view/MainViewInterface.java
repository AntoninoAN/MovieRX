package com.sample.test.moviefinder.view;



import com.sample.test.moviefinder.model.MovieResponse;
import com.sample.test.moviefinder.model.Result;

import java.util.List;

/**
 * Created by mac_tony on 11/10/18.
 */

public interface MainViewInterface {
    void displayMoviesCache(List<Result> listado);
    MainActivity getActivity();
}
