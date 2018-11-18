package com.sample.test.moviefinder.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.sample.test.moviefinder.api.HerokuAppApi;
import com.sample.test.moviefinder.api.HerokuAppInterface;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mac_tony on 11/15/18.
 */

public class MovieRepository {
    public static final String TAG = MovieRepository.class.getSimpleName();
    private static final int FRESH_CACHE_TIMEOUT = 1;
    private MovieDao movieDao;
    private HerokuAppInterface appInterface;
    public static boolean refresh;


    MovieRepository(Application application) {
        MovieRoomDataBase dataBase = MovieRoomDataBase.getInstance(application.getApplicationContext());
        movieDao = dataBase.movieDao();
        this.appInterface = HerokuAppApi.getRetrofitDB(application.getApplicationContext())
                .create(HerokuAppInterface.class);
    }

    LiveData<List<Result>> getCache(final Application App) {
        //pass current time and compare in dao if something previously exists
        getAsync();
        if (refresh) {
            Log.d(TAG, "run: empty room");
            //nothing exists fetch
            appInterface.getMoviesForCache().enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    Toast.makeText(App.getApplicationContext(), "Data fetch from Api", Toast.LENGTH_LONG).show();
                    insertCache(response.body());
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    Toast.makeText(App.getApplicationContext(), "Network error " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
        //always return from room
        return movieDao.getCache();
    }


    public void insertCache(MovieResponse result) {
        new insertAsync(movieDao).execute(result);
    }

    public void getAsync() {
        new getAsync(movieDao).execute();
    }

    private static class getAsync extends AsyncTask<Void, Void, Void> {

        private MovieDao movieDao;

        getAsync(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(Void... applications) {
            refresh = movieDao.lastRefresh(getMaxRefreshTime(new Date())) == null;
            return null;
        }
    }

    private static class insertAsync extends AsyncTask<MovieResponse, Void, Void> {

        private MovieDao asyncMovieDao;

        insertAsync(MovieDao movieDao) {
            asyncMovieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MovieResponse... results) {
            List<Result> movie = results[0].getData();
            for (Result movieItem :
                    movie) {
                if (movieItem != null) {
                    Result item = new Result();
                    item.setId(movieItem.getId());
                    item.setTitle(movieItem.getTitle());
                    item.setGenre(movieItem.getGenre());
                    item.setPoster(movieItem.getPoster());
                    item.setLastRefresh(new Date());
                    asyncMovieDao.insertCache(item);
                }
            }
            return null;
        }
    }

    private static Date getMaxRefreshTime(Date currentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.MINUTE, -FRESH_CACHE_TIMEOUT);
        return cal.getTime();
    }
}
