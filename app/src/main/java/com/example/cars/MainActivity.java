package com.example.cars;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cars.data.model.CarsModel;
import com.example.cars.data.model.CarsResult;
import com.example.cars.data.remote.ApiService;
import com.example.cars.data.remote.RetrofitClient;
import com.example.cars.ui.RecyclerViewAdapter;

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
    ArrayList<CarsResult> carsResult = new ArrayList<>();
    CarsResult result = null;
    boolean isLoading = false;
   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        brandTxt = (TextView) findViewById(R.id.car_brand_txt);
        usedTxt = (TextView) findViewById(R.id.car_status_txt);
        apiService = RetrofitClient.getClient().create(ApiService.class);

        Call<CarsModel> call2 = apiService.getCars("2");
        call2.enqueue(new Callback<CarsModel>() {
            @Override
            public void onResponse(Call<CarsModel> call, Response<CarsModel> response) {

                CarsModel carList = response.body();

            }

            @Override
            public void onFailure(Call<CarsModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.carsRV);
        brandTxt = (TextView) findViewById(R.id.car_brand_txt);
        usedTxt = (TextView) findViewById(R.id.car_status_txt);
        apiService = RetrofitClient.getClient().create(ApiService.class);
        populateData();
        initAdapter();
        initScrollListener();
        Call<CarsModel> call2 = apiService.getCars("2");
        call2.enqueue(new Callback<CarsModel>() {
            @Override
            public void onResponse(Call<CarsModel> call, Response<CarsModel> response) {

                CarsModel carList = response.body();
                carsResult = carList.getCarsResult();
                result = carList.getCarsResult().get(0);
            }

            @Override
            public void onFailure(Call<CarsModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
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