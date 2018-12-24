package com.example.finalproject.api;

import com.example.finalproject.model.Cast;
import com.example.finalproject.model.CastResponse;
import com.example.finalproject.model.GenreListResponse;
import com.example.finalproject.model.MovieDetailsResponse;
import com.example.finalproject.model.MoviesResponse;
import com.example.finalproject.model.RecommendationsResponse;
import com.example.finalproject.model.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("movie/popular?api_key=23d691ff8bed37cf9cf63c5ceb5445eb")
    Call<MoviesResponse> getPopularMovies(@Query("language") String language);

    @GET("movie/top_rated?api_key=23d691ff8bed37cf9cf63c5ceb5445eb")
    Call<MoviesResponse> getTopRatedMovies(@Query("language") String language);

    @GET("movie/upcoming?api_key=23d691ff8bed37cf9cf63c5ceb5445eb")
    Call<MoviesResponse> getUpcomingMovies(@Query("language") String language);

    @GET("movie/latest?api_key=23d691ff8bed37cf9cf63c5ceb5445eb")
    Call<MovieDetailsResponse> getLatestMovie();

    @GET("movie/now_playing?api_key=23d691ff8bed37cf9cf63c5ceb5445eb")
    Call<MoviesResponse> getNowPlayingMovies(@Query("language") String language);

    @GET("movie/{movie_id}?api_key=23d691ff8bed37cf9cf63c5ceb5445eb")
    Call<MovieDetailsResponse> getMovieDetails(@Path("movie_id") String id, @Query("language") String language);

    @GET("movie/{movie_id}/credits?api_key=23d691ff8bed37cf9cf63c5ceb5445eb")
    Call<CastResponse> getCastDetails(@Path("movie_id") String id);

    @GET("movie/{movie_id}/recommendations?api_key=23d691ff8bed37cf9cf63c5ceb5445eb")
    Call<RecommendationsResponse> getMovieRecommendations(@Path("movie_id") String id);

    @GET("genre/movie/list?api_key=23d691ff8bed37cf9cf63c5ceb5445eb")
    Call<GenreListResponse> getGenreList();

    @GET("movie/{movie_id}/similar?api_key=23d691ff8bed37cf9cf63c5ceb5445eb")
    Call<RecommendationsResponse> getSimilarMovies(@Path("movie_id") String id);

    @GET("movie/{movie_id}/videos?api_key=23d691ff8bed37cf9cf63c5ceb5445eb")
    Call<VideoResponse> getMovieVideos(@Path("movie_id") String id);

}
