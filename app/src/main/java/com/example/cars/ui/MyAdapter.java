package com.example.cars.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cars.R;
import com.example.cars.data.model.CarsResult;
import java.util.ArrayList;

/**
 * Created by Esraa Rashad on 6/15/2021.
 * email : esraarashad56@yahoo.com
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final ArrayList<CarsResult> carsList;
    private final Context context;
    private CarsResult carsResult = null;

    public MyAdapter(Context context, ArrayList<CarsResult> carsList) {
        this.carsList = carsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder viewHolder, int i) {
        carsResult = carsList.get(i);
        ((MyViewHolder) viewHolder).carBrandTV.setText(carsResult.getBrand());
        if (carsResult.getUsed()) {
            ((MyViewHolder) viewHolder).carStatusTV.setText(context.getText(R.string.isUsed));
        } else {
            ((MyViewHolder) viewHolder).carStatusTV.setText(context.getText(R.string.isNew));
        }
    }

    @Override
    public int getItemCount() {
        return carsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView carBrandTV;
        public TextView carStatusTV;
        public ConstraintLayout constraintLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            this.carBrandTV = (TextView) itemView.findViewById(R.id.car_brand_txt);
            this.carStatusTV = (TextView) itemView.findViewById(R.id.car_status_txt);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.car_container);
        }
    }
}