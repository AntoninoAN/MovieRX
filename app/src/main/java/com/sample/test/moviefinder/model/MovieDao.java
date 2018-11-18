package com.sample.test.moviefinder.model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;


/**
 * Created by mac_tony on 11/14/18.
 */
@Dao
public interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCache(Result result);
    @Query("SELECT * FROM movie ORDER BY title ASC")
    LiveData<List<Result>> getCache();
    @Query("SELECT * FROM movie WHERE lastRefresh > :lastrefreshed LIMIT 1")
    Result lastRefresh(Date lastrefreshed);
}
