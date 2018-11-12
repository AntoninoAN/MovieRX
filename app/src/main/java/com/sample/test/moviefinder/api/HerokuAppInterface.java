package com.sample.test.moviefinder.api;

import com.sample.test.moviefinder.model.MovieResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mac_tony on 11/10/18.
 */

public interface HerokuAppInterface {
    @GET("api/movies/")
    Observable<MovieResponse>getMovies();
    @GET("api/movies/search")
    Observable<MovieResponse>getMoviesBasedOnQuery(@Query("query") String s);
}
