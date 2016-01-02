package com.trevorhalvorson.activitytransitionrecyclerviewexample;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Trevor Halvorson on 1/1/2016.
 */
public class Production implements Serializable {

    @Expose
    private Integer unit;
    @SerializedName("show_id")
    @Expose
    private Integer showId;
    @SerializedName("show_title")
    @Expose
    private String showTitle;
    @SerializedName("release_year")
    @Expose
    private String releaseYear;
    @Expose
    private String rating;
    @Expose
    private String category;
    @SerializedName("show_cast")
    @Expose
    private String showCast;
    @Expose
    private String director;
    @Expose
    private String summary;
    @Expose
    private String poster;
    @Expose
    private Integer mediatype;
    @Expose
    private String runtime;

    public Integer getUnit() {
        return unit;
    }

    public Integer getShowId() {
        return showId;
    }

    public String getShowTitle() {
        return showTitle;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public String getRating() {
        return rating;
    }

    public String getCategory() {
        return category;
    }

    public String getShowCast() {
        return showCast;
    }

    public String getDirector() {
        return director;
    }

    public String getSummary() {
        return summary;
    }

    public String getPoster() {
        return poster;
    }

    public Integer getMediatype() {
        return mediatype;
    }

    public String getRuntime() {
        return runtime;
    }
}
