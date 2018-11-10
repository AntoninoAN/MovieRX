package com.sample.test.moviefinder.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac_tony on 11/10/18.
 */

public class MovieResult {

    @SerializedName("data")
    @Expose
    private List<MovieResponse> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public MovieResult() {
    }

    /**
     *
     * @param data
     */
    public MovieResult(List<MovieResponse> data) {
        super();
        this.data = data;
    }

    public List<MovieResponse> getData() {
        return data;
    }

    public void setData(List<MovieResponse> data) {
        this.data = data;
    }

    public int getTotalResult(){return data.size();}

}