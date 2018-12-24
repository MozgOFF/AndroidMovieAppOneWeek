package com.example.finalproject.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalproject.R;
import com.example.finalproject.adapter.GenreAdapter;
import com.example.finalproject.loader.GenreLoader;
import com.example.finalproject.model.Genre;

import java.util.List;

public class FilterActivity extends AppCompatActivity implements GenreLoader.GenreLoadCallback {

    private static final String TAG = "FilterActivity";
    
    private android.support.v7.widget.Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private GenreLoader mLoader;
    private RecyclerView mGenresView;
    private GenreAdapter mGenreAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        mToolbar = findViewById(R.id.toolbar_filter);
        setSupportActionBar(mToolbar);

        initUI();
    }

    private void initUI() {
        Log.d(TAG, "initUI: is called");

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_back_button);
        actionbar.setTitle("Select filter ");

        mGenresView = findViewById(R.id.recycler_filter);
        mProgressBar = findViewById(R.id.progress_bar_filter);
        mLoader = new GenreLoader(this);
        mGenresView.setLayoutManager(new LinearLayoutManager(this));
        mGenreAdapter = new GenreAdapter(this);
        mGenresView.setAdapter(mGenreAdapter);

        mLoader.loadGenres();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(TAG, "onOptionsItemSelected: back button is pressed");
                Toast.makeText(this, "Back button is pressed",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGenresLoaded(List<Genre> genres) {
        Log.d(TAG, "onGenresLoaded: called");
        mGenreAdapter.setGenreList(genres);
        mProgressBar.setVisibility(View.GONE);

    }
}
