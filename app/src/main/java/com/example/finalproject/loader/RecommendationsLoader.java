package com.example.finalproject.loader;

import com.example.finalproject.api.MovieService;
import com.example.finalproject.model.RecommendationsResponse;
import com.example.finalproject.model.Result;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecommendationsLoader {
    private RecommendationsLoadCallback mCallback;

    public RecommendationsLoader(RecommendationsLoadCallback callback) {
        mCallback = callback;
    }

    public void loadRecommendedMovies(String id) {
        MovieService.getApi()
                .getMovieRecommendations(id)
                .enqueue(new Callback<RecommendationsResponse>() {
                    @Override
                    public void onResponse(Call<RecommendationsResponse> call, Response<RecommendationsResponse> response) {
                        mCallback.onRecommendationsLoaded(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<RecommendationsResponse> call, Throwable t) {

                    }
                });
    }

    public void loadSimilarMovies(String id) {
        MovieService.getApi()
                .getSimilarMovies(id)
                .enqueue(new Callback<RecommendationsResponse>() {
                    @Override
                    public void onResponse(Call<RecommendationsResponse> call, Response<RecommendationsResponse> response) {
                        mCallback.onSimilarLoaded(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<RecommendationsResponse> call, Throwable t) {

                    }
                });
    }

    public interface RecommendationsLoadCallback {

        void onRecommendationsLoaded(List<Result> movies);

        void onSimilarLoaded(List<Result> movies);
    }
}
