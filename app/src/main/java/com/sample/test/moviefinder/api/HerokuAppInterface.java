package com.sample.test.moviefinder.api;

import com.sample.test.moviefinder.model.MovieResult;


import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by mac_tony on 11/10/18.
 */

public interface HerokuAppInterface {
    @GET()
    Observable<MovieResult>getMovies();
}
