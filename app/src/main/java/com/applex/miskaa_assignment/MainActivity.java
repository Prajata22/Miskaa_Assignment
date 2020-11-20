package com.applex.miskaa_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView no_data;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ExtendedFloatingActionButton delete;
    private ShimmerFrameLayout shimmerFrameLayout;
    private List<CountryModel> countryModelArrayList = new ArrayList<>();
    private RoomDB database;
    private IntroPref introPref;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        introPref = new IntroPref(this);
        database = RoomDB.getInstance(this);

        no_data = findViewById(R.id.no_data);
        recyclerView = findViewById(R.id.recycler_list);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        delete = findViewById(R.id.delete);

        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);

        if(introPref.isFirstTimeLaunch()) {
            fetchData();
            introPref.setIsFirstTimeLaunch(false);
        }
        else {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            countryModelArrayList = database.daoInterface().getAll();
            adapter = new RecyclerAdapter(MainActivity.this, countryModelArrayList);
            recyclerView.setAdapter(adapter);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                introPref.setIsFirstTimeLaunch(true);
                database.daoInterface().reset(countryModelArrayList);
                countryModelArrayList.clear();
                countryModelArrayList.addAll(database.daoInterface().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void fetchData() {
        Call<ArrayList<CountryModel>> call = RetrofitHelper.getInstance().getApiInterface().getCountryList();
        call.enqueue(new Callback<ArrayList<CountryModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<CountryModel>> call, @NonNull Response<ArrayList<CountryModel>> response) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                database.daoInterface().insert(response.body());
                countryModelArrayList = database.daoInterface().getAll();
                adapter = new RecyclerAdapter(MainActivity.this, countryModelArrayList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<CountryModel>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}