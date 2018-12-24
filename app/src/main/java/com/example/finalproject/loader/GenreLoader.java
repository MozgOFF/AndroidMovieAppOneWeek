package com.example.finalproject.loader;

import android.util.Log;

import com.example.finalproject.api.MovieService;
import com.example.finalproject.model.Genre;
import com.example.finalproject.model.GenreListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreLoader {

    private GenreLoadCallback mCallback;
    private static final String TAG = "GenreLoader";

    public GenreLoader(GenreLoadCallback callback) {
        mCallback = callback;
    }

    public void loadGenres() {
        MovieService.getApi()
                .getGenreList()
                .enqueue(new Callback<GenreListResponse>() {
                    @Override
                    public void onResponse(Call<GenreListResponse> call, Response<GenreListResponse> response) {
                        mCallback.onGenresLoaded(response.body().getGenres());
                        Log.d(TAG, "onResponse: called");
                        Log.d(TAG, "onResponse: genres: " + response.body().getGenres());
                    }

                    @Override
                    public void onFailure(Call<GenreListResponse> call, Throwable t) {

                    }
                });
    }

    public interface GenreLoadCallback {
        void onGenresLoaded(List<Genre> genres);
    }
}