package com.example.cars.ui;

/**
 * Created by Esraa Rashad on 6/15/2021.
 * email : esraarashad56@yahoo.com
 */

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cars.R;
import com.example.cars.data.model.CarsResult;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public ArrayList<CarsResult> carsList;
    private CarsResult carsResult = null;

    public RecyclerViewAdapter(ArrayList<CarsResult> carsItemList) {

        carsList = carsItemList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ItemViewHolder) {

            populateItemRows((ItemViewHolder) viewHolder, position);
        } else if (viewHolder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) viewHolder, position);
        }

    }

    @Override
    public int getItemCount() {
        return carsList == null ? 0 : carsList.size();
    }

    /**
     * The following method decides the type of ViewHolder to display in the RecyclerView
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return carsList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    private class ItemViewHolder extends RecyclerView.ViewHolder {

        public TextView carBrandTV;
        public TextView carStatusTV;
        public ConstraintLayout constraintLayout;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            this.carBrandTV = (TextView) itemView.findViewById(R.id.car_brand_txt);
            this.carStatusTV = (TextView) itemView.findViewById(R.id.car_status_txt);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.car_container);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    private void showLoadingView(LoadingViewHolder viewHolder, int position) {
        //ProgressBar would be displayed
        viewHolder.progressBar.setVisibility(View.VISIBLE);
    }

    private void populateItemRows(ItemViewHolder viewHolder, int position) {
        carsResult = carsList.get(position);
        viewHolder.carBrandTV.setText(carsResult.getBrand());
        if (carsResult.getUsed()) {
            viewHolder.carStatusTV.setText("is used");
        } else {
            viewHolder.carStatusTV.setText("is new");
        }
    }
}
