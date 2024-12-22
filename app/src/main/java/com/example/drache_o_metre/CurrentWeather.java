package com.example.drache_o_metre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.drache_o_metre.data.adapters.DailyForecastAdapter;
import com.example.drache_o_metre.data.adapters.HourlyForecastAdapter;
import com.example.drache_o_metre.data.forecast_objects.DailyForecast;
import com.example.drache_o_metre.data.forecast_objects.HourlyForecast;

import java.util.ArrayList;
import java.util.List;

public class CurrentWeather extends AppCompatActivity {

    private RecyclerView hourlyForecastRecyclerView;
    private HourlyForecastAdapter hourlyForecastAdapter;
    private List<HourlyForecast> hourlyForecastList = new ArrayList<>();

    private RecyclerView dailyForecastRecyclerView;
    private DailyForecastAdapter dailyForecastAdapter;
    private List<DailyForecast> dailyForecastList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation du RecyclerView horizontal pour les prévisions horaires
        hourlyForecastRecyclerView = findViewById(R.id.hourlyForecastRecyclerView);
        hourlyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        hourlyForecastAdapter = new HourlyForecastAdapter(hourlyForecastList);
        hourlyForecastRecyclerView.setAdapter(hourlyForecastAdapter);

        // Initialisation du RecyclerView vertical pour les prévisions journalières
        dailyForecastRecyclerView = findViewById(R.id.weeklyForecastRecyclerView);
        dailyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        dailyForecastAdapter = new DailyForecastAdapter(dailyForecastList);
        dailyForecastRecyclerView.setAdapter(dailyForecastAdapter);

        // Récupérer le bouton "Details"
        Button detailsButton = findViewById(R.id.detailsButton);

        // Définir un OnClickListener pour lancer l'activité de prévisions hebdomadaires
        detailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créer un Intent pour lancer WeeklyForecastActivity
                Intent intent = new Intent(CurrentWeather.this, Detailed_Forecast.class);
                startActivity(intent);  // Lancer l'activité
            }
        });

        // Récupérer les données météo (ici, avec des exemples statiques)
        fetchHourlyForecastData();
        fetchDailyForecastData();
    }

    private void fetchHourlyForecastData() {
        // Exemple avec des données fictives
        hourlyForecastList.add(new HourlyForecast("10:00", "22°C", "broken_clouds"));
        hourlyForecastList.add(new HourlyForecast("11:00", "24°C", "few_clouds"));
        hourlyForecastList.add(new HourlyForecast("12:00", "25°C", "mist"));
        hourlyForecastList.add(new HourlyForecast("13:00", "22°C", "rain"));
        hourlyForecastList.add(new HourlyForecast("14:00", "24°C", "scattered_clouds"));
        hourlyForecastList.add(new HourlyForecast("15:00", "25°C", "shower_rain"));
        hourlyForecastList.add(new HourlyForecast("16:00", "22°C", "snow"));
        hourlyForecastList.add(new HourlyForecast("17:00", "24°C", "sunny"));
        hourlyForecastList.add(new HourlyForecast("18:00", "25°C", "thunderstorm"));
        hourlyForecastList.add(new HourlyForecast("19:00", "22°C", "sunny"));
        hourlyForecastList.add(new HourlyForecast("20:00", "24°C", "few_clouds"));

        hourlyForecastAdapter.notifyDataSetChanged();
    }

    private void fetchDailyForecastData() {
        // Exemple avec des données fictives
        dailyForecastList.add(new DailyForecast("Lundi", R.drawable.sunny, 20));
        dailyForecastList.add(new DailyForecast("Mardi", R.drawable.rain, 80));
        dailyForecastList.add(new DailyForecast("Mercredi", R.drawable.snow, 10));
        dailyForecastList.add(new DailyForecast("Jeudi", R.drawable.shower_rain, 50));
        dailyForecastList.add(new DailyForecast("Vendredi", R.drawable.few_clouds, 15));
        dailyForecastList.add(new DailyForecast("Samedi", R.drawable.mist, 30));
        dailyForecastList.add(new DailyForecast("Dimanche", R.drawable.thunderstorm, 70));

        dailyForecastAdapter.notifyDataSetChanged();
    }
}