package com.applex.miskaa_assignment.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.applex.miskaa_assignment.Models.CountryModel;
import com.applex.miskaa_assignment.R;
import com.pixplicity.sharp.Sharp;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RecyclerAdapter extends RecyclerView.Adapter <RecyclerAdapter.ProgrammingViewHolder> {

    private final Context context;
    private final List<CountryModel> mList;
    private OkHttpClient httpClient;

    public RecyclerAdapter(Context context, List<CountryModel> list) {
        this.mList = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ProgrammingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_items, parent, false);
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
            fetchSvg(context, currentItem.getFlag(), holder.flag);
        }
        else {
            holder.flag.setImageResource(R.drawable.flag);
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

    private void fetchSvg(Context context, String url, final ImageView target) {
        if (httpClient == null) {
            httpClient = new OkHttpClient.Builder()
                    .cache(new Cache(context.getCacheDir(), 20 * 1024 * 1024))
                    .build();
        }

        Request request = new Request.Builder().url(url).build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                target.setImageResource(R.drawable.flag);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                InputStream stream = response.body().byteStream();
                Sharp.loadInputStream(stream).into(target);
                stream.close();
            }
        });
    }
}