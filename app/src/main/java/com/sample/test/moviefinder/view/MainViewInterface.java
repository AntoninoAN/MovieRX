package com.sample.test.moviefinder.view;


import com.sample.test.moviefinder.model.MovieResult;

/**
 * Created by mac_tony on 11/10/18.
 */

public interface MainViewInterface {
    void displayMovies(MovieResult movieResult);
    void showToast(String message);
}
