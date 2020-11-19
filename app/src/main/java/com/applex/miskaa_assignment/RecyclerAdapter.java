package com.applex.miskaa_assignment;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.ProgrammingViewHolder> {

    private final List<CountryModel> mList;

    public RecyclerAdapter(List<CountryModel> list) { this.mList = list; }

    @NonNull
    @Override
    public ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ProgrammingViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ProgrammingViewHolder holder, int position) {
        CountryModel currentItem = mList.get(position);

        if(currentItem.getName() != null && !currentItem.getName().isEmpty()) {
            holder.country_name.setText(currentItem.getName());
        }
        else {
            holder.country_name.setVisibility(View.GONE);
        }

        if(currentItem.getCapital() != null && !currentItem.getCapital().isEmpty()) {
            holder.capital.setText("Capital: " + currentItem.getCapital());
        }
        else {
            holder.capital.setVisibility(View.GONE);
        }

        if(currentItem.getRegion() != null && !currentItem.getRegion().isEmpty()) {
            holder.region.setText("Region: " + currentItem.getRegion());
        }
        else {
            holder.region.setVisibility(View.GONE);
        }

        if(currentItem.getSubregion() != null && !currentItem.getSubregion().isEmpty()) {
            holder.sub_region.setText("Sub Region: " + currentItem.getSubregion());
        }
        else {
            holder.sub_region.setVisibility(View.GONE);
        }

        holder.population.setText("Population: " + currentItem.getPopulation());

        if (currentItem.getFlag() != null && !currentItem.getFlag().isEmpty()) {
            Picasso.get().load(currentItem.getFlag())
                    .resize(R.dimen.image_preview_width, R.dimen.image_preview_height)
                    .error(R.drawable.ic_account_circle_black_24dp)
                    .into(holder.flag);
//            holder.flag.setImageURI(Uri.parse(currentItem.getFlag()));
//            Picasso.get().load(currentItem.getFlag()).resize(R.dimen.image_preview_width, R.dimen.image_preview_height).into(new Target() {
//                @Override
//                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                    holder.flag.setImageBitmap(bitmap);
//                }
//
//                @Override
//                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
//                    Log.e("BAMCHIKI", e.getMessage());
//                }
//
//                @Override
//                public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//                }
//            });
        }
        else {
            Log.e("BAMCHIKI", "1");
            holder.flag.setImageResource(R.drawable.ic_account_circle_black_24dp);
        }

        if(currentItem.getBorders() != null && currentItem.getBorders().size() != 0) {
            StringBuilder borders = new StringBuilder();
            for(int i = 0; i < currentItem.getBorders().size(); i++) {
                if(i == currentItem.getBorders().size()-1) {
                    borders.append(currentItem.getBorders().get(i));
                }
                else {
                    borders.append(currentItem.getBorders().get(i)).append(", ");
                }
            }
            holder.borders.setText(borders.toString());
        }
        else {
            holder.bordersHeader.setVisibility(View.GONE);
            holder.borders.setVisibility(View.GONE);
        }

        if(currentItem.getLanguages() != null && currentItem.getLanguages().size() != 0) {
            StringBuilder languages = new StringBuilder();
            for(int i = 0; i < currentItem.getLanguages().size(); i++) {
                if(i == currentItem.getLanguages().size()-1) {
                    languages.append(currentItem.getLanguages().get(i).getName());
                }
                else {
                    languages.append(currentItem.getLanguages().get(i).getName()).append(", ");
                }
            }
            holder.languages.setText(languages.toString());
        }
        else {
            holder.languagesHeader.setVisibility(View.GONE);
            holder.languages.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ProgrammingViewHolder extends RecyclerView.ViewHolder {

        TextView country_name, capital, region, sub_region, population, borders, bordersHeader, languages, languagesHeader;
        ImageView flag;

        private ProgrammingViewHolder(@NonNull View view) {
            super(view);

            country_name = view.findViewById(R.id.country_name);
            capital = view.findViewById(R.id.capital);
            region = view.findViewById(R.id.region);
            sub_region = view.findViewById(R.id.sub_region);
            population = view.findViewById(R.id.population);
            borders = view.findViewById(R.id.borders);
            bordersHeader = view.findViewById(R.id.bordersHeader);
            languages = view.findViewById(R.id.languages);
            languagesHeader = view.findViewById(R.id.languagesHeader);
            flag = view.findViewById(R.id.flag);
        }
    }
}