package com.applex.miskaa_assignment.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.app.AlertDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import com.applex.miskaa_assignment.Adapters.RecyclerAdapter;
import com.applex.miskaa_assignment.Models.CountryModel;
import com.applex.miskaa_assignment.Preferences.IntroPref;
import com.applex.miskaa_assignment.R;
import com.applex.miskaa_assignment.Utilities.RetrofitHelper;
import com.applex.miskaa_assignment.Utilities.RoomDatabase;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView no_data, error;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ExtendedFloatingActionButton delete;
    private ShimmerFrameLayout shimmerFrameLayout;
    private CoordinatorLayout main_layout;
    private List<CountryModel> countryModelArrayList = new ArrayList<>();
    private RoomDatabase database;
    private IntroPref introPref;
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        introPref = new IntroPref(this);
        database = RoomDatabase.getInstance(this);

        error = findViewById(R.id.error);
        delete = findViewById(R.id.delete);
        no_data = findViewById(R.id.no_data);
        main_layout = findViewById(R.id.main_layout);
        recyclerView = findViewById(R.id.recycler_list);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);

        if(introPref.isFirstTimeLaunch()) {
            if(((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
                recyclerView.setVisibility(View.GONE);
                no_data.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, "Please check your internet connection and refresh", Toast.LENGTH_SHORT).show();
            }
            else {
                no_data.setVisibility(View.GONE);
                shimmerFrameLayout.setVisibility(View.VISIBLE);
                shimmerFrameLayout.startShimmer();
                fetchData();
                introPref.setIsFirstTimeLaunch(false);
            }
        }
        else {
            buildRecyclerView();
        }

        swipeRefreshLayout
                .setColorSchemeColors(getResources().getColor(R.color.purple_500),
                 getResources().getColor(R.color.purple_700));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            if(introPref.isFirstTimeLaunch()) {
                if(((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
                    recyclerView.setVisibility(View.GONE);
                    no_data.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "Please check your internet connection and refresh", Toast.LENGTH_SHORT).show();
                }
                else {
                    no_data.setVisibility(View.GONE);
                    shimmerFrameLayout.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.startShimmer();
                    fetchData();
                    introPref.setIsFirstTimeLaunch(false);
                }
            }
            else {
                buildRecyclerView();
            }
        });

        delete.setOnClickListener(view -> {
            if(countryModelArrayList.size() > 0) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete all data")
                        .setMessage("Are you sure?")
                        .setPositiveButton("Delete", (dialog, which) -> {
                            introPref.setIsFirstTimeLaunch(true);
                            database.daoInterface().reset(countryModelArrayList);
                            countryModelArrayList.clear();
                            countryModelArrayList.addAll(database.daoInterface().getAll());
                            adapter.notifyDataSetChanged();
                            recyclerView.setVisibility(View.GONE);
                            no_data.setVisibility(View.VISIBLE);
                            main_layout.setBackgroundColor(getResources().getColor(R.color.white));
                        })
                        .setNegativeButton("Cancel",null)
                        .setCancelable(true);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
            else {
                Toast.makeText(MainActivity.this, "There is no data to delete", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if(dy > 0) {
                    new Handler().postDelayed(() -> delete.shrink(), 200);
                }
                else {
                    new Handler().postDelayed(() -> delete.extend(), 200);
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    private void fetchData() {
        Call<ArrayList<CountryModel>> call = RetrofitHelper.getInstance().getApiInterface().getCountryList();
        call.enqueue(new Callback<ArrayList<CountryModel>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<CountryModel>> call, @NonNull Response<ArrayList<CountryModel>> response) {
                main_layout.setBackgroundColor(getResources().getColor(R.color.grey));
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                error.setVisibility(View.GONE);

                if(response.body().size() > 0) {
                    no_data.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    database.daoInterface().insert(response.body());
                    buildRecyclerView();
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    no_data.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<CountryModel>> call, @NonNull Throwable t) {
                main_layout.setBackgroundColor(getResources().getColor(R.color.white));
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                error.setVisibility(View.VISIBLE);
            }
        });
    }

    private void buildRecyclerView() {
        countryModelArrayList = database.daoInterface().getAll();
        adapter = new RecyclerAdapter(MainActivity.this, countryModelArrayList);
        recyclerView.setAdapter(adapter);
        if(swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}