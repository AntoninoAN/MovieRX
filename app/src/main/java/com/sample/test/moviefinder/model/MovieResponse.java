package com.sample.test.moviefinder.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mac_tony on 11/10/18.
 */

public class MovieResponse {

    @SerializedName("data")
    @Expose
    private List<Result> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public MovieResponse() {
    }

    /**
     *
     * @param data
     */
    public MovieResponse(List<Result> data) {
        super();
        this.data = data;
    }

    public List<Result> getData() {
        return data;
    }

    public void setData(List<Result> data) {
        this.data = data;
    }

    public int getTotalResult(){return data != null ? data.size() : 0;}

}