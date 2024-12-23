package com.example.drache_o_metre.data.interact;

import static com.example.drache_o_metre.data.interact.WeatherIconUtils.getWeatherIconResource;

import android.util.Log;

import com.example.drache_o_metre.data.forecast_objects.DailyForecast;
import com.example.drache_o_metre.data.forecast_objects.HourlyForecast;
import com.example.drache_o_metre.data.interact.callback.CurrentWeatherCallback;
import com.example.drache_o_metre.data.interact.callback.DailyWeatherCallback;
import com.example.drache_o_metre.data.interact.callback.HourlyWeatherCallback;
import com.example.drache_o_metre.data.interact.responses.ApiKey;
import com.example.drache_o_metre.data.interact.responses.CurrentWeatherResponse;
import com.example.drache_o_metre.data.interact.responses.DailyWeatherResponse;
import com.example.drache_o_metre.data.interact.responses.HourlyWeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataManager {

    private static final String BASE_HOURLY_URL = "https://api.openweathermap.org/";

    private OpenWeatherApi openWeatherApi;
    private String apiKey;

    public WeatherDataManager() {
        //TODO: update method when possible
        ApiKey k = new ApiKey();
        apiKey = k.getApiKey();
        Log.d("WeatherDataManager", "apiKey: " + apiKey);
        Log.d("WeatherDataManager", "apiKey: " + apiKey);

        // Initialiser Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_HOURLY_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        openWeatherApi = retrofit.create(OpenWeatherApi.class);
    }

    public void getHourlyForecast(double latitude, double longitude, final HourlyWeatherCallback callback) {
        // Appel API pour obtenir les prévisions horaires
        Call<HourlyWeatherResponse> call = openWeatherApi.getHourlyForecast(latitude, longitude, apiKey);
        Log.d("API Request", "URL: " + call.request().url().toString());
        call.enqueue(new Callback<HourlyWeatherResponse>() {
            @Override
            public void onResponse(Call<HourlyWeatherResponse> call, Response<HourlyWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HourlyWeatherResponse.Hourly> hourlyList = response.body().list;
                    List<HourlyForecast> forecasts = new ArrayList<>();

                    // Obtenez les données pour les 10 prochaines heures
                    for (int i = 0; i < 10 && i < hourlyList.size(); i++) {
                        HourlyWeatherResponse.Hourly hourly = hourlyList.get(i);

                        // Convertir le timestamp en heure
                        String time = convertTimestampToTime(hourly.dt);
                        String temperature = Math.round(hourly.main.temp - 273.15) + "°C";
                        String icon = getWeatherIconResource(hourly.weather.get(0).icon);  // Assumons qu'il y a toujours au moins un élément


                        forecasts.add(new HourlyForecast(time, temperature, icon));
                    }

                    // Retourner les résultats via le callback
                    callback.onSuccess(forecasts);
                } else {
                    callback.onFailure("Erreur dans la réponse de l'API");
                }
            }

            @Override
            public void onFailure(Call<HourlyWeatherResponse> call, Throwable t) {
                callback.onFailure("Erreur de connexion : " + t.getMessage());
            }
        });
    }

    public void getCurrentWeather(double latitude, double longitude, final CurrentWeatherCallback callback) {
        Call<CurrentWeatherResponse> call = openWeatherApi.getCurrentWeather(latitude, longitude, apiKey);
        Log.d("API Request", "URL: " + call.request().url().toString());
        call.enqueue(new Callback<CurrentWeatherResponse>() {
            @Override
            public void onResponse(Call<CurrentWeatherResponse> call, Response<CurrentWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CurrentWeatherResponse currentWeather = response.body();

                    String cityName = currentWeather.getCityName();
                    String temperature = Math.round(currentWeather.getTemperature()) + "°C";
                    String icon = getWeatherIconResource(currentWeather.getIconCode());

                    callback.onSuccess(cityName, temperature, icon);
                } else {
                    callback.onFailure("Erreur dans la réponse de l'API");
                }
            }

            @Override
            public void onFailure(Call<CurrentWeatherResponse> call, Throwable t) {
                callback.onFailure("Erreur de connexion : " + t.getMessage());
            }
        });
    }

    public void getDailyWeather(double latitude, double longitude, int days, final DailyWeatherCallback callback) {
        Call<DailyWeatherResponse> call = openWeatherApi.getDailyForecast(latitude, longitude, days, apiKey);
        Log.d("API Request", "URL: " + call.request().url().toString());

        call.enqueue(new Callback<DailyWeatherResponse>() {
            @Override
            public void onResponse(Call<DailyWeatherResponse> call, Response<DailyWeatherResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DailyWeatherResponse.DailyForecastItem> dailyList = response.body().getForecastList();
                    List<DailyForecast> forecasts = new ArrayList<>();

                    for (DailyWeatherResponse.DailyForecastItem item : dailyList) {
                        String dayName = item.getDayName();
                        String weatherIcon = item.getIconCode();
                        double pop = item.getPrecipitationProbability();
                        float mornTemp = item.getTemperature().getMorn() - 273.15f;  // Température du matin
                        float maxTemp = item.getTemperature().getMax() - 273.15f;    // Température maximale

                        forecasts.add(new DailyForecast(dayName, weatherIcon, pop, mornTemp, maxTemp));
                    }

                    callback.onSuccess(forecasts);
                } else {
                    callback.onFailure("Erreur dans la réponse de l'API");
                }
            }

            @Override
            public void onFailure(Call<DailyWeatherResponse> call, Throwable t) {
                callback.onFailure("Erreur de connexion : " + t.getMessage());
            }
        });
    }








    // Méthode pour convertir le timestamp en heure
    private String convertTimestampToTime(long timestamp) {
        // Convertir le timestamp en heure (par exemple, 1638900000L -> 14:00)
        java.util.Date date = new java.util.Date(timestamp * 1000); // Convertir en millisecondes
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
}

