package com.sample.test.moviefinder.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac_tony on 11/10/18.
 */

public class HerokuAppApi {
    private final static String base_url_api = "https://movies-sample.herokuapp.com/";

    public static Retrofit retrofitdb;



    public HerokuAppApi() {
    }

    public static Retrofit getRetrofitDB(Context context){
        if(retrofitdb == null)
            retrofitdb = new Retrofit.Builder()
                    .baseUrl(base_url_api)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        return retrofitdb;
    }

    private static Boolean hasNetwork(Context context) {
        Boolean isConnected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected())
            isConnected = true;
        return isConnected;
    }

}
