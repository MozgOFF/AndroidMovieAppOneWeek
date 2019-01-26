package com.example.finalproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.api.MovieService;
import com.example.finalproject.loader.MoviesLoader;
import com.example.finalproject.model.Cast;
import com.example.finalproject.model.MovieDetailsResponse;
import com.example.finalproject.model.Result;
import com.example.finalproject.model.VideoResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfilePageActivity extends AppCompatActivity
        implements MoviesLoader.MovieLoadCallback, View.OnClickListener {

    private android.support.v7.widget.Toolbar mToolbar;
    private TextView mTextView, mItemTitle, mItemOverview, mProfileToUseFeatures;
    private MoviesLoader mLoader;
    private ImageView mImageView;
    private Button mButton, mButton2;
    private FirebaseAuth mAuth;
    private LinearLayout mLinearLayout;

    public static final String PREF_LANGUAGE = "language";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        mToolbar = findViewById(R.id.toolbar_profile_page);
        setSupportActionBar(mToolbar);
        
        initUI();
    }

    private void initUI() {
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back_button);
        actionbar.setTitle("Profile page");

        mTextView = findViewById(R.id.profile_sign_in);

        mLinearLayout = findViewById(R.id.change_language_linear);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        mButton = findViewById(R.id.language_en);
        mButton2 = findViewById(R.id.language_ru);
        mProfileToUseFeatures = findViewById(R.id.profile_to_use_features);

        if (currentUser != null) {
            actionbar.setSubtitle(currentUser.getEmail());
            mLinearLayout.setVisibility(View.VISIBLE);
            mProfileToUseFeatures.setVisibility(View.GONE);
            mTextView.setVisibility(View.GONE);
        } else {
            mProfileToUseFeatures.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.VISIBLE);
            mLinearLayout.setVisibility(View.GONE);
        }

        mTextView.setOnClickListener(this);

        mLoader = new MoviesLoader(this);
        mLoader.loadLatestMovie();

        mItemTitle = findViewById(R.id.profile_latest_title);
        mItemOverview = findViewById(R.id.profile_latest_overview);
        mImageView = findViewById(R.id.backdrop_item_latest);


        mButton.setOnClickListener(this);
        mButton2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.profile_sign_in):
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case (R.id.language_en):
                SharedPreferences sharedPreferencess = getSharedPreferences(getString(R.string.app_name),
                        Context.MODE_PRIVATE);
                sharedPreferencess.edit().putString(PREF_LANGUAGE, "en-EN").apply();
                Toast.makeText(this, "Language changed to: en-EN", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.language_ru):
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name),
                        Context.MODE_PRIVATE);
                sharedPreferences.edit().putString(PREF_LANGUAGE, "ru-RU").apply();
                Toast.makeText(this, "Language changed to: ru-RU", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (android.R.id.home):
                Intent intent2 = new Intent(this, MainActivity.class);
                startActivity(intent2);
                finish();
                break;
            case (R.id.profile_logout):
                Intent intent3 = new Intent(this, MainActivity.class);
                startActivity(intent3);
                mAuth.signOut();
                Toast.makeText(this, "You have been signed out",
                        Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMoviesLoaded(List<Result> movies, int genre_id) {

    }

    @Override
    public void onMovieDetailsLoaded(MovieDetailsResponse movieDetails) {
        if (movieDetails.getBackdropPath() == null) {
            Toast.makeText(this, "Sorry, no backdrop for latest movie!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Picasso.get()
                    .load(MovieService.IMAGE_ENDPOINT + movieDetails.getBackdropPath())
                    .into(mImageView);
        }
        mItemOverview.setText(movieDetails.getOverview());
        mItemTitle.setText(movieDetails.getTitle());

    }

    @Override
    public void onMovieVideosLoaded(List<VideoResult> videoResult) {

    }

    @Override
    public void onCastDetailsLoaded(List<Cast> casts) {

    }
}
