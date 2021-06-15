package com.example.cars.data.model;

import java.util.ArrayList;

/**
 * Created by Esraa Rashad on 6/15/2021.
 * email : esraarashad56@yahoo.com
 */
public class CarsModel {
    private Integer status;
    private ArrayList<CarsResult> carsResult;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ArrayList<CarsResult> getCarsResult() {
        return carsResult;
    }

    public void setCarsResult(ArrayList<CarsResult> carsResult) {
        this.carsResult = carsResult;
    }
}
