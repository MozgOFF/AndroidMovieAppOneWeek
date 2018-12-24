package com.example.finalproject.loader;


import com.example.finalproject.api.MovieService;
import com.example.finalproject.model.Cast;
import com.example.finalproject.model.CastResponse;
import com.example.finalproject.model.MovieDetailsResponse;
import com.example.finalproject.model.MoviesResponse;
import com.example.finalproject.model.Result;
import com.example.finalproject.model.VideoResponse;
import com.example.finalproject.model.VideoResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesLoader {
    private MovieLoadCallback mCallback;

    public MoviesLoader(MovieLoadCallback callback) {
        mCallback = callback;
    }

    public void loadMovieDetails(String id, String language) {
        MovieService.getApi()
                .getMovieDetails(id, language)
                .enqueue(new Callback<MovieDetailsResponse>() {
                    @Override
                    public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                        mCallback.onMovieDetailsLoaded(response.body());
                    }

                    @Override
                    public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {

                    }
                });
    }


    public void loadPopularMovies(final int genre_id, String language) {
        MovieService.getApi()
                .getPopularMovies(language)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                            mCallback.onMoviesLoaded(response.body().getResults(), genre_id);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });
    }

    public void loadLatestMovie() {
        MovieService.getApi()
                .getLatestMovie()
                .enqueue(new Callback<MovieDetailsResponse>() {
                    @Override
                    public void onResponse(Call<MovieDetailsResponse> call, Response<MovieDetailsResponse> response) {
                        mCallback.onMovieDetailsLoaded(response.body());
                    }

                    @Override
                    public void onFailure(Call<MovieDetailsResponse> call, Throwable t) {

                    }
                });
    }

    public void loadNowPlayingMovies(final int genre_id, String language) {
        MovieService.getApi()
                .getNowPlayingMovies(language)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        mCallback.onMoviesLoaded(response.body().getResults(), genre_id);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });
    }

    public void loadTopRatedMovies(final int genre_id, String language) {
        MovieService.getApi()
                .getTopRatedMovies(language)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        mCallback.onMoviesLoaded(response.body().getResults(), genre_id);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });
    }


    public void loadUpcomingMovies(final int genre_id, String language) {
        MovieService.getApi()
                .getUpcomingMovies(language)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        mCallback.onMoviesLoaded(response.body().getResults(), genre_id);
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {

                    }
                });
    }

    public void loadMovieVideos(String id) {
        MovieService.getApi()
                .getMovieVideos(id)
                .enqueue(new Callback<VideoResponse>() {
                    @Override
                    public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                        mCallback.onMovieVideosLoaded(response.body().getResults());
                    }

                    @Override
                    public void onFailure(Call<VideoResponse> call, Throwable t) {

                    }
                });
    }

    public void loadCastDetails(String id) {
        MovieService.getApi()
                .getCastDetails(id)
                .enqueue(new Callback<CastResponse>() {
                    @Override
                    public void onResponse(Call<CastResponse> call, Response<CastResponse> response) {
                        mCallback.onCastDetailsLoaded(response.body().getCast());
                    }

                    @Override
                    public void onFailure(Call<CastResponse> call, Throwable t) {

                    }
                });
    }

    public interface MovieLoadCallback {
        void onMoviesLoaded(List<Result> movies, int genre_id);

        void onMovieDetailsLoaded(MovieDetailsResponse movieDetails);

        void onMovieVideosLoaded(List<VideoResult> videoResult);

        void onCastDetailsLoaded(List<Cast> casts);
    }
}
