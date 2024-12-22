package com.example.drache_o_metre;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drache_o_metre.data.adapters.DetailedForecastAdapter;
import com.example.drache_o_metre.data.forecast_objects.DetailedForecast;


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

        // Initialisation du RecyclerView
        detailedForecastRecyclerView = findViewById(R.id.weeklyForecastRecyclerView);
        detailedForecastRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        // Initialisation de l'adaptateur
        detailedForecastAdapter = new DetailedForecastAdapter(forecastList);
        detailedForecastRecyclerView.setAdapter(detailedForecastAdapter);

        // Charger les données (fictives pour l'instant)
        loadSampleData();
    }

    private void loadSampleData() {
        forecastList.add(new DetailedForecast("Monday", R.drawable.broken_clouds, R.drawable.few_clouds, "10°C", "18°C", "30%"));
        forecastList.add(new DetailedForecast("Tuesday", R.drawable.mist, R.drawable.rain, "8°C", "16°C", "70%"));
        forecastList.add(new DetailedForecast("Wednesday", R.drawable.scattered_clouds, R.drawable.shower_rain, "12°C", "19°C", "50%"));
        forecastList.add(new DetailedForecast("Thursday", R.drawable.snow, R.drawable.sunny, "11°C", "20°C", "20%"));
        forecastList.add(new DetailedForecast("Friday", R.drawable.shower_rain, R.drawable.rain, "15°C", "22°C", "10%"));
        forecastList.add(new DetailedForecast("Sunday", R.drawable.mist, R.drawable.scattered_clouds, "10°C", "18°C", "30%"));
        forecastList.add(new DetailedForecast("Saturday", R.drawable.sunny, R.drawable.snow, "10°C", "18°C", "30%"));

        // Notifier l'adaptateur que les données ont changé
        detailedForecastAdapter.notifyDataSetChanged();
    }
}
