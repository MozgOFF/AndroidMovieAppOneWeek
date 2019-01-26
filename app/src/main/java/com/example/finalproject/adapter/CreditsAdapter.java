package com.example.finalproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject.R;
import com.example.finalproject.api.MovieService;
import com.example.finalproject.model.Cast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.CreditsViewHolder> {

    private static final String TAG = "CreditsAdapter";
    private List<Cast> mCasts;

    public CreditsAdapter() {
        mCasts = new ArrayList<>();
    }

    @NonNull
    @Override
    public CreditsAdapter.CreditsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_cast, viewGroup, false);
        return new CreditsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditsAdapter.CreditsViewHolder creditsViewHolder, int i) {
        creditsViewHolder.bindCredit(mCasts.get(i));
    }

    @Override
    public int getItemCount() {
        return mCasts.size();
    }

    public void setmCasts(List<Cast> Casts) {
        mCasts.clear();
        mCasts.addAll(Casts);

        notifyDataSetChanged();
    }

    public class CreditsViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView, mTextView2;
        private ImageView mImageView;


        public CreditsViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.cast_character);
            mImageView = itemView.findViewById(R.id.cast_backdrop);
            mTextView2 = itemView.findViewById(R.id.cast_name);
        }

        public void bindCredit(Cast cast) {

            mTextView.setText(cast.getCharacter());
            Picasso.get()
                    .load(MovieService.IMAGE_ENDPOINT + cast.getProfilePath())
                    .into(mImageView);
            mTextView2.setText(cast.getName());
        }
    }
}
