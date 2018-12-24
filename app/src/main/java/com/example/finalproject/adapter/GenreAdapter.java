package com.example.finalproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalproject.activity.MainActivity;
import com.example.finalproject.OnItemClickedListener;
import com.example.finalproject.R;
import com.example.finalproject.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {
    private static final String TAG = "GenreAdapter";

    public static final int REQUEST_CODE_FILTER = 1;

    private TextView mGenreView;
    private List<Genre> GenreList;
    private Context mContext;

    public GenreAdapter(Context mContext) {
        GenreList = new ArrayList<>();
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public GenreAdapter.GenreViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_genre, viewGroup, false);

        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GenreAdapter.GenreViewHolder genreViewHolder, int i) {
        genreViewHolder.bindGenre(GenreList.get(i));

        genreViewHolder.setOnItemClickedListener(new OnItemClickedListener() {
            @Override
            public void onMovieClicked(View v, int position, boolean isLongClick) {

            }

            @Override
            public void onGenreClicked(View v, int position, boolean isLongClick) {
                Log.d(TAG, "onGenreClicked: called");
                if (isLongClick) {
                    Log.d(TAG, "onGenreClicked: called");
                } else {
                    Log.d(TAG, "onGenreClicked: called");

                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra("genre_id", GenreList.get(position).getId().toString());
                    mContext.startActivity(intent);
                    Log.d(TAG, "onGenreClicked: " + GenreList.get(position).getId());
//                    mContext.startActivityForResult(intent, REQUEST_CODE_FILTER);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return GenreList.size();
    }

    public void setGenreList(List<Genre> genres) {
        GenreList.clear();
        GenreList.addAll(genres);

        notifyDataSetChanged();
    }

    public class GenreViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private OnItemClickedListener mOnItemClickedListener;

        void setOnItemClickedListener(OnItemClickedListener mOnItemClickedListener) {
            this.mOnItemClickedListener = mOnItemClickedListener;
        }

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);

            mGenreView = itemView.findViewById(R.id.genre_list_item);

            itemView.setOnClickListener(this);

        }

        public void bindGenre(Genre genre) {
            Log.d(TAG, "bindGenre: getName: " + genre.getName());
            mGenreView.setText(genre.getName());
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: is called ");
            mOnItemClickedListener.onGenreClicked(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            mOnItemClickedListener.onGenreClicked(v, getAdapterPosition(), true);
            return false;
        }
    }
}
