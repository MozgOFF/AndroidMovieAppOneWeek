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
import com.example.finalproject.model.ProductionCompany;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LogosAdapter extends RecyclerView.Adapter<LogosAdapter.LogosViewHolder> {

    private List<ProductionCompany> mProductionCompanies;

    public LogosAdapter() {
        mProductionCompanies = new ArrayList<>();
    }

    @NonNull
    @Override
    public LogosAdapter.LogosViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.production_company_layout, viewGroup, false);

        return new LogosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogosViewHolder logosViewHolder, int i) {
        logosViewHolder.bindLogo(mProductionCompanies.get(i));
    }

    @Override
    public int getItemCount() {
        return mProductionCompanies.size();
    }

    public void setLogos(List<ProductionCompany> productionCompanies) {
        mProductionCompanies.clear();
        mProductionCompanies.addAll(productionCompanies);

        notifyDataSetChanged();
    }

    public class LogosViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;
        private ImageView mImageView;

        public LogosViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.production_company_title);
            mImageView = itemView.findViewById(R.id.production_company_logo);
        }

        public void bindLogo(ProductionCompany productionCompany) {

            mTextView.setText(productionCompany.getName());
            Picasso.get()
                    .load(MovieService.IMAGE_ENDPOINT + productionCompany.getLogoPath())
                    .into(mImageView);
        }
    }
}
