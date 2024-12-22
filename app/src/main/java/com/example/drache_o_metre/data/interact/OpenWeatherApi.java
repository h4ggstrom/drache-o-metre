package com.example.drache_o_metre.data.interact;

import com.example.drache_o_metre.data.interact.responses.CurrentWeatherResponse;
import com.example.drache_o_metre.data.interact.responses.DailyWeatherResponse;
import com.example.drache_o_metre.data.interact.responses.HourlyWeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

    // API pour les prévisions horaires
    @GET("data/2.5/forecast/hourly")
    Call<HourlyWeatherResponse> getHourlyForecast(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey
    );
/*
    // API pour les prévisions quotidiennes
    @GET("data/2.5/forecast/daily")
    Call<DailyWeatherResponse> getDailyForecast(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

    // API pour la météo actuelle
    @GET("data/2.5/weather")
    Call<CurrentWeatherResponse> getCurrentWeather(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
 */
}


