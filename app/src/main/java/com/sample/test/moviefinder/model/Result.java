package com.sample.test.moviefinder.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by mac_tony on 11/10/18.
 */
@Entity(tableName = "movie")
public class Result {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer id;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title;
    @ColumnInfo(name = "year")
    @SerializedName("year")
    @Expose
    private String year;
    @ColumnInfo(name = "genre")
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("poster")
    @Expose
    private String poster;

    private Date lastRefresh;

    /**
     * No args constructor for use in serialization
     *
     */
    public Result() {
    }

    /**
     *
     * @param genre
     * @param id
     * @param title
     * @param poster
     * @param year
     */
    public Result(Integer id, String title, String year, String genre, String poster, Date refresh) {
        super();
        this.id = id;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.poster = poster;
        this.lastRefresh = refresh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public Date getLastRefresh() { return lastRefresh; }

    public void setLastRefresh(Date lastRefresh) { this.lastRefresh = lastRefresh; }

}