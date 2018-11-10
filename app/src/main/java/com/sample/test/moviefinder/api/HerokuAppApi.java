package com.sample.test.moviefinder.api;

import com.sample.test.moviefinder.R;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac_tony on 11/10/18.
 */

public class HerokuAppApi {
    private final static String base_url_api = "https://movies-sample.herokuapp.com/api/movies";

    public static Retrofit retrofit;

    public HerokuAppApi() {
    }

    public static Retrofit getRetrofit(){
        if(retrofit==null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url_api)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
