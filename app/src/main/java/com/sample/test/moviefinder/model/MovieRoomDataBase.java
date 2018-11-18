package com.sample.test.moviefinder.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.sample.test.moviefinder.converter.DateConverter;

/**
 * Created by mac_tony on 11/15/18.
 */

@Database(entities = {Result.class}, version = 1)
@TypeConverters(DateConverter.class)
public abstract class MovieRoomDataBase extends RoomDatabase {
    private static volatile MovieRoomDataBase INSTANCE;

    public static MovieRoomDataBase getInstance(Context context) {
        if(INSTANCE == null){
            synchronized (MovieRoomDataBase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDataBase.class, "movie_db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract MovieDao movieDao();

}
