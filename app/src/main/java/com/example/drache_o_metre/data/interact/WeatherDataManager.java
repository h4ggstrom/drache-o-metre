package com.example.drache_o_metre.data.interact;

import static com.example.drache_o_metre.data.interact.WeatherIconUtils.getWeatherIconResource;

import android.util.Log;

import com.example.drache_o_metre.data.forecast_objects.HourlyForecast;
import com.example.drache_o_metre.data.interact.responses.ApiKey;
import com.example.drache_o_metre.data.interact.responses.HourlyWeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

    public void getHourlyForecast(double latitude, double longitude, final WeatherCallback callback) {
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
                        String temperature = String.valueOf(Math.round(hourly.main.temp - 273.15)) + "°C";
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

    // Méthode pour convertir le timestamp en heure
    private String convertTimestampToTime(long timestamp) {
        // Convertir le timestamp en heure (par exemple, 1638900000L -> 14:00)
        java.util.Date date = new java.util.Date(timestamp * 1000); // Convertir en millisecondes
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
        return sdf.format(date);
    }
}

