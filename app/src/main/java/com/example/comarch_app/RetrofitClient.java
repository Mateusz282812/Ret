package com.example.comarch_app;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public Api getApi(){
        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl("https://api.spaceflightnewsapi.net/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofitBuilder.create(Api.class);
        return api;
    }
}
