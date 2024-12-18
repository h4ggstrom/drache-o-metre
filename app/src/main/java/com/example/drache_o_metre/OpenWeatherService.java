package com.example.drache_o_metre;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit Interface for the OpenWeatherMap API.
 *
 * @see <a href="https://openweathermap.org/current">OpenWeatherMap API</a>
 * @see <a href="https://square.github.io/retrofit/">Retrofit documentation</a>
 * @author Robin de Angelis
 * @version 1.0.0
 */
public interface OpenWeatherService {

    /**
     * Retrieves the current weather forecast for a given location.
     *
     * @param latitude current latitude of the user
     * @param longitude current longitude of the user
     * @param apiKey API key for the OpenWeatherMap API
     * @return Call object for the API request
     */
    @GET("weather")
    Call<WeatherResponse> getCurrentWeather(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String apiKey
    );
}
