package com.example.finalproject.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.OnItemClickedListener;
import com.example.finalproject.R;
import com.example.finalproject.model.VideoResult;

import java.util.ArrayList;
import java.util.List;

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosViewHolder> {

    private List<VideoResult> mVideoResults;
    private static final String TAG = "VideosAdapter";
    private Context mContext;

    public VideosAdapter(Context mContext) {
        this.mContext = mContext;
        mVideoResults = new ArrayList<>();
    }

    @NonNull
    @Override
    public VideosAdapter.VideosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_video_item, viewGroup, false);

        return new VideosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VideosViewHolder videosViewHolder, int i) {
        videosViewHolder.bindVideo(mVideoResults.get(i));

        videosViewHolder.setOnItemClickedListenerListener(new OnItemClickedListener() {
            @Override
            public void onMovieClicked(View v, int position, boolean isLongClick) {
                Log.d(TAG, "onMovieClicked: short click");
                if (!isLongClick) {
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + mVideoResults.get(position).getKey()));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://www.youtube.com/watch?v=" + mVideoResults.get(position).getKey()));
                    try {
                        mContext.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        mContext.startActivity(webIntent);
                    }
                } else {
                    Toast.makeText(mContext, "LongClick", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onGenreClicked(View v, int position, boolean isLongClick) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return mVideoResults.size();
    }

    public void setmVideoResults(List<VideoResult> videoResults) {
        mVideoResults.clear();
        mVideoResults.addAll(videoResults);

        notifyDataSetChanged();
    }

    public class VideosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
            View.OnLongClickListener{

        private TextView mTextView;
        private ImageView mImageView;

        private OnItemClickedListener onItemClickedListenerListener;

        void setOnItemClickedListenerListener(OnItemClickedListener onItemClickedListenerListener) {
            this.onItemClickedListenerListener = onItemClickedListenerListener;
        }


        public VideosViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.video_title);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bindVideo(VideoResult videoResult) {
            mTextView.setText(videoResult.getName());
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
