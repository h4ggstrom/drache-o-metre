package com.example.drache_o_metre.data.interact;

import com.example.drache_o_metre.data.forecast_objects.HourlyForecast;

import java.util.List;

public interface WeatherCallback {
    void onSuccess(List<HourlyForecast> forecasts);
    void onFailure(String errorMessage);
}

