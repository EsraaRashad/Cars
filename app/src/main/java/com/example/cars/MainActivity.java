package com.example.cars;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cars.data.model.CarsModel;
import com.example.cars.data.remote.ApiService;
import com.example.cars.data.remote.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView brandTxt;
    TextView usedTxt;
    ApiService apiService;
    @Override
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

                CarsModel userList = response.body();

            }

            @Override
            public void onFailure(Call<CarsModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_LONG).show();
                call.cancel();
            }
        });

    }
}