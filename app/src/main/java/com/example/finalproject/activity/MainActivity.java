package com.example.finalproject.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.finalproject.fragment.FragmentNowPlaying;
import com.example.finalproject.fragment.FragmentPopular;
import com.example.finalproject.fragment.FragmentTopRated;
import com.example.finalproject.fragment.FragmentUpcoming;
import com.example.finalproject.R;
import com.example.finalproject.adapter.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private android.support.v7.widget.Toolbar mToolbar;


    private static final String TAG = "MainActivity";
    public static final String PREF_LANGUAGE = "language";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        initUI();
    }


    private void initUI() {
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_video);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentPopular(), "Popular");
        adapter.AddFragment(new FragmentTopRated(), "Top rated");
        adapter.AddFragment(new FragmentUpcoming(), "Upcoming");
        adapter.AddFragment(new FragmentNowPlaying(), "Now playing");

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        String language = getSharedPreferences(getString(R.string.app_name),
                Context.MODE_PRIVATE).getString(PREF_LANGUAGE, "en-EN");


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d("Home", "onOptionsItemSelected: home page is working");
                Toast.makeText(this, "Home button is pressed",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.account_page:
                Log.d("Account page", "onOptionsItemSelected: account page is working");
                Intent intent2 = new Intent(this, ProfilePageActivity.class);
                startActivity(intent2);
                return true;
            case R.id.main_search:
                Log.d("Search", "onOptionsItemSelected: search is working");
                Toast.makeText(this, "Search is not working yet",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.filter_page:
                Log.d("Filter", "onOptionsItemSelected: filter is pressed");
                Intent intent = new Intent(this, FilterActivity.class);
                startActivity(intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}
