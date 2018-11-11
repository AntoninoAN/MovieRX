package com.sample.test.moviefinder.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.sample.test.moviefinder.R;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac_tony on 11/10/18.
 */

public class HerokuAppApi {
    private final static String base_url_api = "https://movies-sample.herokuapp.com/";

    public static Retrofit retrofit;

    private static long cacheSize = (5 * 1024 * 1024);


    public HerokuAppApi() {
    }

    public static Retrofit getRetrofit(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url_api)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getClient(context))
                    .build();
        }
        return retrofit;
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

    private static OkHttpClient getClient(final Context context) {
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (hasNetwork(context)) {
                            request.newBuilder().header("Cache-Control", "public max-age=" + 60 * 10).build();
                            return chain.proceed(request);

                        } else {
                            request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale" + 60 * 10).build();
                            return chain.proceed(request);
                        }
                    }
                });
        OkHttpClient okHttpClient = builder.build();
        return okHttpClient;
    }
}
