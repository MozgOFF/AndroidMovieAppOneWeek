package com.example.finalproject.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieService {

    private static final String ENDPOINT = "https://api.themoviedb.org/3/";
    public static final String IMAGE_ENDPOINT = "http://image.tmdb.org/t/p/w780/";

    public static MovieApi getApi() {
        return getRetrofit().create(MovieApi.class);
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
