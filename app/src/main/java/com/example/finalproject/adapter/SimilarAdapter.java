package com.example.finalproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.activity.MovieItemActivity;
import com.example.finalproject.OnItemClickedListener;
import com.example.finalproject.R;
import com.example.finalproject.api.MovieService;
import com.example.finalproject.model.Result;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SimilarAdapter extends RecyclerView.Adapter<SimilarAdapter.SimilarViewHolder> {

    private static final String TAG = "SimilarAdapter";

    private Context mContext;
    private List<Result> mMovies;

    public SimilarAdapter(Context mContext) {
        this.mContext = mContext;
        mMovies = new ArrayList<>();
    }

    @NonNull
    @Override
    public SimilarAdapter.SimilarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_movie, viewGroup, false);

        return new SimilarViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SimilarViewHolder similarViewHolder, int i) {
        similarViewHolder.bindMovie(mMovies.get(i));

        similarViewHolder.setOnItemClickedListenerListener(new OnItemClickedListener() {
            @Override
            public void onMovieClicked(View v, int position, boolean isLongClick) {
                if (isLongClick) {
                    Log.d("LongClick", "onMovieClicked long: " + position);
                    Toast.makeText(mContext, "LongClick: " + position, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("ShortClick", "onMovieClicked short: " + position);
                    Toast.makeText(mContext, "ShortClick: " + position + String.valueOf(mMovies.get(position).getId()), Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(mContext, MovieItemActivity.class);
                    intent.putExtra("movie_id", String.valueOf(mMovies.get(position).getId()));
                    mContext.startActivity(intent);
                }
            }

            @Override
            public void onGenreClicked(View v, int position, boolean isLongClick) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setMovies(List<Result> movies) {
        mMovies.clear();
        mMovies.addAll(movies);

        notifyDataSetChanged();
    }

    public class SimilarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView mTitleView;
        private TextView mRatingView;
        private TextView mReleaseDateView;

        private ImageView mBackdrop;

        private OnItemClickedListener onItemClickedListenerListener;

        void setOnItemClickedListenerListener(OnItemClickedListener onItemClickedListenerListener) {
            this.onItemClickedListenerListener = onItemClickedListenerListener;
        }

        public SimilarViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitleView = itemView.findViewById(R.id.title_view);
            mRatingView = itemView.findViewById(R.id.rating_view);
            mReleaseDateView = itemView.findViewById(R.id.release_date_view);

            mBackdrop = itemView.findViewById(R.id.backdrop);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bindMovie(Result result) {
            Log.d(TAG, "bindMovie: is called");

            mTitleView.setText(result.getTitle());
            mRatingView.setText(String.valueOf(result.getVoteAverage()));
            mReleaseDateView.setText(result.getReleaseDate());

            Picasso.with(itemView.getContext())
                    .load(MovieService.IMAGE_ENDPOINT + result.getPosterPath())
                    .into(mBackdrop);
        }

        @Override
        public void onClick(View v) {
            onItemClickedListenerListener.onMovieClicked(v, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View v) {
            onItemClickedListenerListener.onMovieClicked(v, getAdapterPosition(), true);

            return false;
        }
    }
}
