package com.example.finalproject;

import android.view.View;

public interface OnItemClickedListener {
    void onMovieClicked(View v, int position, boolean isLongClick);
    void onGenreClicked(View v, int position, boolean isLongClick);
}
