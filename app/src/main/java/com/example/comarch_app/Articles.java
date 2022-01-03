package com.example.comarch_app;

import com.google.gson.annotations.SerializedName;

public class Articles {
    @SerializedName("id")
    public final Integer id;
    @SerializedName("featured")
    public final Boolean featured;
    @SerializedName("title")
    public final String title;
    @SerializedName("url")
    public final String url;
    @SerializedName("imageUrl")
    public final String imageURL;
    @SerializedName("newsSite")
    public final String newsSite;
    @SerializedName("summary")
    public final String summary;
    @SerializedName("publishedAt")
    public final String publishedAt;

    public Articles(Integer id, Boolean featured, String title, String url, String imageURL, String newsSite, String summary, String publishedAt) {
        this.id = id;
        this.featured = featured;
        this.title = title;
        this.url = url;
        this.imageURL = imageURL;
        this.newsSite = newsSite;
        this.summary = summary;
        this.publishedAt = publishedAt;
    }
}
