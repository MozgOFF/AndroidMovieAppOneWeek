package com.example.finalproject.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.adapter.CreditsAdapter;
import com.example.finalproject.adapter.LogosAdapter;
import com.example.finalproject.adapter.RecommendationsAdapter;
import com.example.finalproject.adapter.SimilarAdapter;
import com.example.finalproject.adapter.VideosAdapter;
import com.example.finalproject.api.MovieService;
import com.example.finalproject.loader.MoviesLoader;
import com.example.finalproject.loader.RecommendationsLoader;
import com.example.finalproject.model.Cast;
import com.example.finalproject.model.CastResponse;
import com.example.finalproject.model.MovieDetailsResponse;
import com.example.finalproject.model.Result;
import com.example.finalproject.model.VideoResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieItemActivity extends AppCompatActivity implements MoviesLoader.MovieLoadCallback,
        RecommendationsLoader.RecommendationsLoadCallback {

    private static final String TAG = "MovieItemActivity";

    private android.support.v7.widget.Toolbar mToolbar;
    private RecyclerView mRecyclerView, mRecyclerViewSimilar, mLogosRecyclerView,
            mVideosRecylerView, mRecyclerViewCredits;
    private ImageView mImageView, mPosterItem;
    private TextView mItemSlogan, mItemOverview, mItemTitle, mItemReleaseDate, mItemGenre,
            mOriginalTitle, mBudget, mRevenue, mRuntime, mAvgVote;
    private MoviesLoader mLoader;
    private RecommendationsLoader mRecommendationsLoader;
    private ProgressBar mProgressBar;
    private RecommendationsAdapter mRecommendationsAdapter;
    private SimilarAdapter mSimilarAdapter;
    private FirebaseAuth mAuth;
    private LogosAdapter mLogosAdapter;
    private VideosAdapter mVideosAdapter;
    private CreditsAdapter mCreditsAdapter;
    private DatabaseReference myRef;
    private FirebaseDatabase mDatabase;
    private Button mButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_item);
        Log.d(TAG, "OnCreate: started");

        mToolbar = findViewById(R.id.toolbar_item);
        setSupportActionBar(mToolbar);

        initUI();
    }


    private void initUI() {

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back_button);
        actionbar.setTitle("Movie details");

        mDatabase = FirebaseDatabase.getInstance();
        myRef = mDatabase.getReference("Favorite movies");
        mAuth = FirebaseAuth.getInstance();

        final FirebaseUser currentUser = mAuth.getCurrentUser();

        mImageView = findViewById(R.id.backdrop_item);
        mToolbar = findViewById(R.id.toolbar_item);
        mItemSlogan = findViewById(R.id.slogan_item);
        mItemOverview = findViewById(R.id.item_overview);
        mItemTitle = findViewById(R.id.title_item);
        mItemGenre = findViewById(R.id.genre);
        mPosterItem = findViewById(R.id.poster_item);
        mItemReleaseDate = findViewById(R.id.release_date_item);
        mProgressBar = findViewById(R.id.progress_bar_item);
        mOriginalTitle = findViewById(R.id.item_original_title);
        mAvgVote = findViewById(R.id.item_vote_average);
        mRevenue = findViewById(R.id.item_revenue);
        mBudget = findViewById(R.id.item_budget);
        mButton = findViewById(R.id.item_add_to_favorites);
        mRuntime = findViewById(R.id.item_runtime);
        mRecyclerView = findViewById(R.id.movies_list);
        mRecyclerViewSimilar = findViewById(R.id.movies_list_similar);
        mLogosRecyclerView = findViewById(R.id.movie_item_logos_recycler);
        mVideosRecylerView = findViewById(R.id.video_recycler);
        mRecyclerViewCredits = findViewById(R.id.credits_recycler);

        mRecommendationsAdapter = new RecommendationsAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mRecommendationsAdapter);

        mVideosAdapter = new VideosAdapter(this);
        mVideosRecylerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        mVideosRecylerView.setAdapter(mVideosAdapter);

        mSimilarAdapter = new SimilarAdapter(this);
        mRecyclerViewSimilar.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewSimilar.setAdapter(mSimilarAdapter);

        mCreditsAdapter = new CreditsAdapter();
        mRecyclerViewCredits.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        mRecyclerViewCredits.setAdapter(mCreditsAdapter);

        mLogosAdapter = new LogosAdapter();
        mLogosRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        mLogosRecyclerView.setAdapter(mLogosAdapter);

        final String movie_id = getIntent().getStringExtra("movie_id");

        String language = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE).getString("language", "en-EN");

        mLoader = new MoviesLoader(this);
        mLoader.loadMovieDetails(movie_id, language);
        mLoader.loadMovieVideos(movie_id);
        mLoader.loadCastDetails(movie_id);

        mRecommendationsLoader = new RecommendationsLoader(this);
        mRecommendationsLoader.loadRecommendedMovies(movie_id);
        mRecommendationsLoader = new RecommendationsLoader(this);
        mRecommendationsLoader.loadSimilarMovies(movie_id);

        Log.d(TAG, "initUI: " + movie_id);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentUser != null) {
                    databaseConnector(movie_id, currentUser.getUid());
                } else {
                    Toast.makeText(MovieItemActivity.this,
                            "Log in to unlock this feature", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void databaseConnector(String movie_id, String uID) {
        if (!myRef.child(uID).child("Favorites").getKey().equals(movie_id)) {
            myRef.child(uID).child("Favorites").setValue(movie_id);
            Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
        } else {
            myRef.child(uID).child("Favorites").setValue("");
            Toast.makeText(this, "The movie was in your favorites, it was removed",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMoviesLoaded(List<Result> movies, int genre_id) {

    }

    @Override
    public void onMovieDetailsLoaded(MovieDetailsResponse movieDetails) {
        Log.d(TAG, "onMovieDetailsLoaded: getTagline: " +
                String.valueOf(movieDetails.getTagline()));

        mLogosAdapter.setLogos(movieDetails.getProductionCompanies());

        mProgressBar.setVisibility(View.GONE);
        mItemSlogan.setText(movieDetails.getTagline());
        mItemOverview.setText(movieDetails.getOverview());
        mItemGenre.setText(movieDetails.getOriginalLanguage());
        mItemReleaseDate.setText(movieDetails.getReleaseDate());
        mItemTitle.setText(movieDetails.getTitle());
        mOriginalTitle.setText("Original title: " + movieDetails.getOriginalTitle());
        mRuntime.setText("Runtime: " + movieDetails.getRuntime() + "min");
        mRevenue.setText("Revenue: " + movieDetails.getRevenue() + "$");
        mBudget.setText("Budget: " + movieDetails.getBudget() + "$");
        mAvgVote.setText("Average vote: " + movieDetails.getVoteAverage());


        Picasso.with(mImageView.getContext())
                .load(MovieService.IMAGE_ENDPOINT + movieDetails.getPosterPath())
                .into(mImageView);

        Picasso.with(mPosterItem.getContext())
                .load(MovieService.IMAGE_ENDPOINT + movieDetails.getBackdropPath())
                .into(mPosterItem);


    }

    @Override
    public void onMovieVideosLoaded(List<VideoResult> videoResult) {
        Toast.makeText(this, "Videos are loaded! Size: " + videoResult.size(), Toast.LENGTH_SHORT).show();
        mVideosAdapter.setmVideoResults(videoResult);
    }

    @Override
    public void onCastDetailsLoaded(List<Cast> casts) {
        mCreditsAdapter.setmCasts(casts);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected: back button is pressed");
                Toast.makeText(this, "Back button is pressed",
                        Toast.LENGTH_SHORT).show();
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecommendationsLoaded(List<Result> movies) {
        mRecommendationsAdapter.setMovies(movies);
    }

    @Override
    public void onSimilarLoaded(List<Result> movies) {
        mSimilarAdapter.setMovies(movies);
    }
}
