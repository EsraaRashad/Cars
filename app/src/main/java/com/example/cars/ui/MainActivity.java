package com.example.cars.ui;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.cars.R;
import com.example.cars.data.model.CarsModel;
import com.example.cars.data.model.CarsResult;
import com.example.cars.data.remote.ApiService;
import com.example.cars.data.remote.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView brandTxt;
    TextView usedTxt;
    ApiService apiService;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    SwipeRefreshLayout swipeRefresh;
    ArrayList<CarsResult> carsResult = new ArrayList<>();
    CarsResult result = null;
    boolean isLoading = false;
    Integer page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.carsRV);
        brandTxt = (TextView) findViewById(R.id.car_brand_txt);
        usedTxt = (TextView) findViewById(R.id.car_status_txt);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        populateData();
        initAdapter();
        initScrollListener();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(0);
                populateData();
                initAdapter();
                initScrollListener();
            }
        });
    }

    private void getData(Integer page) {
        Call<CarsModel> call2 = apiService.getCars(page + "");
        call2.enqueue(new Callback<CarsModel>() {
            @Override
            public void onResponse(Call<CarsModel> call, Response<CarsModel> response) {

                CarsModel carList = response.body();
                carsResult = carList.getCarsResult();
                result = carList.getCarsResult().get(page);
                swipeRefresh.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<CarsModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }


    private void populateData() {
        int i = 0;
        while (i < 10) {
            carsResult.add(result);
            i++;
        }
    }

    private void initAdapter() {

        recyclerViewAdapter = new RecyclerViewAdapter(carsResult);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == carsResult.size() - 1) {
                        //bottom of list!
                        page++;
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        carsResult.add(null);
        recyclerViewAdapter.notifyItemInserted(carsResult.size() - 1);
        getData(page);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                carsResult.remove(carsResult.size() - 1);
                int scrollPosition = carsResult.size();
                recyclerViewAdapter.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 10;

                while (currentSize - 1 < nextLimit) {
                    carsResult.add(result);
                    currentSize++;
                }

                recyclerViewAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 2000);
    }
}