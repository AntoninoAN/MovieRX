package com.sample.test.moviefinder.view;


import com.sample.test.moviefinder.model.MovieResponse;

/**
 * Created by mac_tony on 11/10/18.
 */

public interface MainViewInterface {
    void displayMovies(MovieResponse movieResponse);
    void showToast(String message);
}
