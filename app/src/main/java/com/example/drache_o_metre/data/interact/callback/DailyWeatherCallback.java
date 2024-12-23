package com.example.drache_o_metre.data.interact.callback;

import com.example.drache_o_metre.data.forecast_objects.DailyForecast;
import com.example.drache_o_metre.data.interact.responses.DailyWeatherResponse;
import java.util.List;

public interface DailyWeatherCallback {

    void onSuccess(List<DailyForecast> dailyForecasts);
    void onFailure(String errorMessage);
}
