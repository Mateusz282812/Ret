package com.example.comarch_app;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {
    @GET("articles")
    Call<List<Articles>> getArticles();
}
