package com.example.finalproject.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalproject.R;
import com.example.finalproject.adapter.MoviesAdapter;
import com.example.finalproject.loader.MoviesLoader;
import com.example.finalproject.model.Cast;
import com.example.finalproject.model.CastResponse;
import com.example.finalproject.model.MovieDetailsResponse;
import com.example.finalproject.model.Result;
import com.example.finalproject.model.VideoResult;

import java.util.ArrayList;
import java.util.List;

public class FragmentNowPlaying extends Fragment implements MoviesLoader.MovieLoadCallback {


    private MoviesLoader mLoader;
    private RecyclerView mMoviesView;
    private MoviesAdapter mMoviesAdapter;

    public static final String PREF_LANGUAGE = "language";
    private static final String TAG = "FragmentNowPlaying";

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.now_playing_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMoviesView = getView().findViewById(R.id.movies_list_now_playing);
        mLoader = new MoviesLoader(this);
        mMoviesView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mMoviesAdapter = new MoviesAdapter(this.getContext());
        mMoviesView.setAdapter(mMoviesAdapter);

        getView().findViewById(R.id.progress_bar_now_playing).setVisibility(View.VISIBLE);

        String language = this.getActivity()
                .getSharedPreferences(getString(R.string.app_name),
                        getContext().MODE_PRIVATE).getString("language", "en-EN");

        if (getActivity().getIntent().hasExtra("genre_id")) {
            mLoader.loadNowPlayingMovies(Integer.parseInt(getActivity().getIntent()
                    .getStringExtra("genre_id")), language);
        } else {
            mLoader.loadNowPlayingMovies(0, language);
        }
    }


    @Override
    public void onMoviesLoaded(List<Result> movies, int genre_id) {
        if (genre_id == 0) {
            mMoviesAdapter.setMovies(movies);
        } else {
            List<Result> mSortedMovies = new ArrayList<>();
            for (int i = 0; i < movies.size(); i++) {
                if (movies.get(i).getGenreIds().contains(genre_id)) {
                    mSortedMovies.add(movies.get(i));
                }
            }
            Log.d(TAG, "onMoviesLoaded movies: " + movies);
            Log.d(TAG, "onMoviesLoaded mSortedMovies: " + mSortedMovies);

            mMoviesAdapter.setMovies(mSortedMovies);
        }
    }

    @Override
    public void onMovieDetailsLoaded(MovieDetailsResponse movieDetails) {

    }

    @Override
    public void onMovieVideosLoaded(List<VideoResult> videoResult) {

    }

    @Override
    public void onCastDetailsLoaded(List<Cast> casts) {

    }
}
