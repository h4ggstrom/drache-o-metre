package com.example.drache_o_metre;

import static com.example.drache_o_metre.data.interact.WeatherIconUtils.getWeatherIconResource;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drache_o_metre.data.adapters.DetailedForecastAdapter;
import com.example.drache_o_metre.data.forecast_objects.DailyForecast;
import com.example.drache_o_metre.data.forecast_objects.DetailedForecast;
import com.example.drache_o_metre.data.interact.WeatherDataManager;
import com.example.drache_o_metre.data.interact.callback.DailyWeatherCallback;


import java.util.ArrayList;
import java.util.List;

public class Detailed_Forecast extends AppCompatActivity {

    private RecyclerView detailedForecastRecyclerView;
    private DetailedForecastAdapter detailedForecastAdapter;
    private List<DetailedForecast> forecastList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_forecast);

        // Récupérer les coordonnées depuis l'Intent
        double latitude = getIntent().getDoubleExtra("LATITUDE", 0.0);
        double longitude = getIntent().getDoubleExtra("LONGITUDE", 0.0);

        // Log les coordonnées pour vérifier qu'elles sont correctement récupérées
        Log.d("Detailed_Forecast", "Latitude: " + latitude + ", Longitude: " + longitude);

        // Initialisation du RecyclerView
        detailedForecastRecyclerView = findViewById(R.id.weeklyForecastRecyclerView);
        detailedForecastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Initialisation de l'adaptateur
        detailedForecastAdapter = new DetailedForecastAdapter(forecastList);
        detailedForecastRecyclerView.setAdapter(detailedForecastAdapter);

        // Charger les données (ici tu peux utiliser les coordonnées pour récupérer des prévisions réelles via l'API)
        loadForecastData(latitude, longitude);
    }

    private void loadForecastData(double latitude, double longitude) {
        // Utilisation de WeatherDataManager pour récupérer les prévisions détaillées
        WeatherDataManager weatherDataManager = new WeatherDataManager();
        weatherDataManager.getDailyWeather(latitude, longitude, 7, new DailyWeatherCallback() {
            @Override
            public void onSuccess(List<DailyForecast> dailyForecasts) {
                forecastList.clear(); // Vider la liste existante
                // Remplir la liste avec les données récupérées de l'API
                for (DailyForecast dailyForecast : dailyForecasts) {
                    String day = dailyForecast.getDayName(); // Exemple : "Monday"
                    String minTemp = String.format("%.0f°C", dailyForecast.getMornTemp()); // Température du matin (formatée)
                    String maxTemp = String.format("%.0f°C", dailyForecast.getMaxTemp()); // Température maximale (formatée)
                    String rainChance = String.format("%.0f%%", dailyForecast.getPrecipitationProbability() * 100); // Probabilité de pluie formatée

                    String iconResourceId = dailyForecast.getWeatherIcon(); // Obtenir l'ID de l'icône météo
                    Log.d("IconResourceID", "IconResourceID: " + iconResourceId);

                    // Ajouter la prévision détaillée à la liste
                    forecastList.add(new DetailedForecast(day, iconResourceId, iconResourceId, minTemp, maxTemp, rainChance));
                }

                // Notifier l'adaptateur que les données ont changé
                detailedForecastAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String errorMessage) {
                Toast.makeText(Detailed_Forecast.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

}

