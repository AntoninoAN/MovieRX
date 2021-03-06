package com.sample.test.moviefinder.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by mac_tony on 11/16/18.
 */

public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
