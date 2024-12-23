package com.example.drache_o_metre.data.interact.callback;

import com.example.drache_o_metre.data.forecast_objects.HourlyForecast;

import java.util.List;

public interface HourlyWeatherCallback {
    void onSuccess(List<HourlyForecast> forecasts);
    void onFailure(String errorMessage);
}

